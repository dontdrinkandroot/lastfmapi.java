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
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class DiskCacheTest {

	private File baseDir;


	@Before
	public void before() {

		this.baseDir = new File(FileUtils.getTempDirectory(), "lfmapicachetest");
		this.baseDir.mkdirs();
	}


	@After
	public void after() throws IOException {

		FileUtils.deleteDirectory(this.baseDir);
	}


	@Test
	public void putGetTest() throws InterruptedException {

		DiskCache cache = new DiskCache(this.baseDir);

		Assert.assertNull(cache.get("one"));
		cache.put("one", "one", 100000L);
		Assert.assertEquals("one", cache.get("one"));

		Assert.assertNull(cache.get("two"));
		cache.put("two", "two", 1L);
		Thread.sleep(2L);

		Assert.assertNull(cache.get("two"));
		cache.put("two", "two", -1L);
		Assert.assertNull(cache.get("two"));
	}

}
