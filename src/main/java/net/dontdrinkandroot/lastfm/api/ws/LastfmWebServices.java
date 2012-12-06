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

import net.dontdrinkandroot.lastfm.api.LastfmWebServicesException;
import net.dontdrinkandroot.lastfm.api.queries.AuthenticatedGetQuery;
import net.dontdrinkandroot.lastfm.api.queries.PostQuery;
import net.dontdrinkandroot.lastfm.api.queries.UnauthenticatedGetQuery;
import net.dontdrinkandroot.lastfm.api.ws.fetcher.Fetcher;


public interface LastfmWebServices {

	/**
	 * Fetch an unauthenticated GET Query.
	 * 
	 * @param <T>
	 *            The return type of the query.
	 * @param query
	 *            The query.
	 * @return The desired return object.
	 * @throws LastfmWebServicesException
	 *             If fetching the Query fails.
	 */
	<T extends Serializable> T fetch(UnauthenticatedGetQuery<T> query) throws LastfmWebServicesException;


	/**
	 * Fetch an authenticated GET Query.
	 * 
	 * @param <T>
	 *            The return type of the Query.
	 * @param query
	 *            The query.
	 * @return The desired return object.
	 * @throws LastfmWebServicesException
	 *             If fetching the Query fails.
	 */
	<T extends Serializable> T fetch(AuthenticatedGetQuery<T> query) throws LastfmWebServicesException;


	/**
	 * Execute an authenticated POST Query.
	 * 
	 * @param <T>
	 *            The return type of the query.
	 * @param query
	 *            The query.
	 * @return The desired return object.
	 * @throws LastfmWebServicesException
	 *             If executing the Query fails.
	 */
	<T extends Serializable> T execute(PostQuery<T> query) throws LastfmWebServicesException;


	/**
	 * Get the current fetcher.
	 * 
	 * @return The {@link Fetcher} responsible for fetching the data.
	 */
	Fetcher getFetcher();


	/**
	 * Sets the current fetcher.
	 * 
	 * @param diskBufferedFetcherThe
	 *            {@link Fetcher} responsible for fetching the data.
	 */
	void setFetcher(Fetcher diskBufferedFetcher);


	/**
	 * Get the current api key.
	 * 
	 * @return The current api key.
	 */
	String getApiKey();


	/**
	 * Get the current api secret.
	 * 
	 * @return The current api secret.
	 */
	String getApiSecret();

}
