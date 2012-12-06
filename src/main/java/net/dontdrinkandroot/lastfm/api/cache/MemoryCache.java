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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import net.dontdrinkandroot.utils.lang.SerializationUtils;


public class MemoryCache implements Cache {

	private final Map<String, Serializable> dataMap;

	private final Map<String, Long> expiryMap;


	public MemoryCache() {

		this.dataMap = new HashMap<String, Serializable>();
		this.expiryMap = new HashMap<String, Long>();
	}


	@Override
	public synchronized <T extends Serializable> T get(String id) {

		@SuppressWarnings("unchecked")
		T data = (T) this.dataMap.get(id);
		if (data == null) {
			return null;
		}

		Long expiry = this.expiryMap.get(data);
		if (expiry == null || expiry.longValue() < System.currentTimeMillis()) {
			this.dataMap.remove(id);
			this.expiryMap.remove(id);

			return null;
		}

		return SerializationUtils.fastClone(data);
	}


	@Override
	public synchronized <T extends Serializable> T put(String id, T data, long timeToLive) {

		if (timeToLive > 0) {
			long expiry = System.currentTimeMillis() + timeToLive;
			this.dataMap.put(id, data);
			this.expiryMap.put(id, expiry);
		}

		return SerializationUtils.fastClone(data);
	}

}
