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
package net.dontdrinkandroot.lastfm.api.ws.fetcher;

import java.util.Map;

import net.dontdrinkandroot.lastfm.api.LastfmWebServicesException;
import net.dontdrinkandroot.lastfm.api.ws.LastfmResponse;


public interface Fetcher {

	/**
	 * Get the current number of requests per second. The returned value depends on the
	 * implementation and can mean the requests/sec over a specific interval or overall.
	 * 
	 * @return The number of requests that this fetcher performed per second.
	 */
	double getWebRequestsPerSecond();


	/**
	 * Perform a post request to the Last.fm API.
	 * 
	 * @param parameters
	 *            A Key/Value map of HTTP parameters.
	 * @return The response returned from Last.fm.
	 * @throws LastfmWebServicesException
	 *             Thrown on any fetching errors encountered.
	 */
	LastfmResponse post(Map<String, String> parameters) throws LastfmWebServicesException;


	/**
	 * Perform a get request to the Last.fm API.
	 * 
	 * @param parameters
	 *            A Key/Value map of HTTP parameters.
	 * @return The response returned from Last.fm.
	 * @throws LastfmWebServicesException
	 *             Thrown on any fetching errors encountered.
	 */
	LastfmResponse get(Map<String, String> parameters) throws LastfmWebServicesException;

}
