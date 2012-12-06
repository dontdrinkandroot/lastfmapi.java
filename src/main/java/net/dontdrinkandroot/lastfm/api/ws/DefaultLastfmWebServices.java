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
package net.dontdrinkandroot.lastfm.api.ws;

import java.io.Serializable;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import net.dontdrinkandroot.lastfm.api.cache.Cache;
import net.dontdrinkandroot.lastfm.api.cache.DiskCache;
import net.dontdrinkandroot.lastfm.api.ws.fetcher.DefaultFetcher;
import net.dontdrinkandroot.lastfm.api.ws.fetcher.Fetcher;


public class DefaultLastfmWebServices extends AbstractLastfmWebServices {

	/** The cache implementation. */
	private final Cache cache;


	/**
	 * Set up the WebServices with the required components.
	 * 
	 * @param key
	 *            The last.fm API key, see http://www.last.fm/api/account
	 * @param secret
	 *            The last.fm API secret, see http://www.last.fm/api/account
	 * @throws ParserConfigurationException
	 */
	public DefaultLastfmWebServices(final String key, final String secret) throws ParserConfigurationException {

		this(key, secret, new DiskCache(), new DefaultFetcher());
	}


	/**
	 * Set up the WebServices with the required components.
	 * 
	 * @param key
	 *            The last.fm API key, see http://www.last.fm/api/account
	 * @param secret
	 *            The last.fm API secret, see http://www.last.fm/api/account
	 * @param cache
	 *            The cache implementation
	 * @throws ParserConfigurationException
	 */
	public DefaultLastfmWebServices(final String key, final String secret, final Cache cache)
			throws ParserConfigurationException {

		this(key, secret, cache, new DefaultFetcher());
	}


	public DefaultLastfmWebServices(final String key, final String secret, final Cache cache, final Fetcher fetcher) {

		super(key, secret, fetcher);
		this.cache = cache;
	}


	/**
	 * Try to fetch the result from cache.
	 * 
	 * @param id
	 *            Id to lookup.
	 * @return The entry if found or null.
	 */
	@Override
	protected Serializable getFromCache(Map<String, String> parameters) {

		return this.cache.get(parameters.toString());
	}


	/**
	 * Store result in cache.
	 * 
	 * @param id
	 *            The id to store the entry for.
	 * @param result
	 *            The query result.
	 * @param timeToLive
	 *            The time to live for the cache entry.
	 * @return The cached result or the original result on caching errors.
	 */
	@Override
	protected Serializable putToCache(Map<String, String> parameters, final Serializable result, final long timeToLive) {

		return this.cache.put(parameters.toString(), result, timeToLive);
	}

}
