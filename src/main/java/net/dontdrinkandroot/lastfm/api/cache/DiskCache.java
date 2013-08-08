/**
 * Copyright (C) 2012 Philip W. Sorst <philip@sorst.net>
 * and individual contributors as indicated
 * by the @authors tag.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.dontdrinkandroot.lastfm.api.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;

import net.dontdrinkandroot.utils.lang.SerializationUtils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DiskCache implements Cache {

	private final File baseDir;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());


	public DiskCache() {

		this(new File(FileUtils.getUserDirectory(), "lastfmcache"));
	}


	public DiskCache(File baseDir) {

		this.baseDir = baseDir;

		/* Create Cache BaseDir if it does not exist */
		if (!baseDir.exists()) {
			if (!baseDir.mkdirs()) {
				throw new RuntimeException("Creating " + baseDir + " failed");
			}
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public synchronized <T extends Serializable> T get(String id) {

		File entryFile = this.resolveFile(id);
		if (!entryFile.exists()) {
			return null;
		}

		try {

			DiskCacheEntry entry = (DiskCacheEntry) SerializationUtils.deserialize(new FileInputStream(entryFile));
			if (entry.isExpired()) {
				entryFile.delete();
				return null;
			}

			return (T) entry.getData();

		} catch (Exception e) {
			this.logger.error("Getting from cache failed", e);
			return null;
		}
	}


	@Override
	public synchronized <T extends Serializable> T put(String id, T data, long timeToLive) {

		if (timeToLive > 0) {
			File entryFile = this.resolveFile(id);
			long expiry = System.currentTimeMillis() + timeToLive;
			DiskCacheEntry entry = new DiskCacheEntry(data, expiry);

			try {
				SerializationUtils.serialize(entry, new FileOutputStream(entryFile));
			} catch (FileNotFoundException e) {
				this.logger.error("Putting to cache failed", e);
			}
		}

		return data;
	}


	private File resolveFile(String id) {

		return new File(this.baseDir, this.computeMd5(id));
	}


	private String computeMd5(String id) {

		return DigestUtils.md5Hex(id);
	}

}
