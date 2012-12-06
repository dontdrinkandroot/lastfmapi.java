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

import org.junit.Assert;
import org.junit.Test;


public class MemoryCacheTest {

	@Test
	public void putGetTest() throws InterruptedException {

		MemoryCache cache = new MemoryCache();

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
