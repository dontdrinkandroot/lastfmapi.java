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
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;

import net.dontdrinkandroot.lastfm.api.LastfmWebServicesException;
import net.dontdrinkandroot.lastfm.api.queries.AuthenticatedGetQuery;
import net.dontdrinkandroot.lastfm.api.queries.PostQuery;
import net.dontdrinkandroot.lastfm.api.queries.Query;
import net.dontdrinkandroot.lastfm.api.queries.UnauthenticatedGetQuery;
import net.dontdrinkandroot.lastfm.api.ws.fetcher.DefaultFetcher;
import net.dontdrinkandroot.lastfm.api.ws.fetcher.Fetcher;
import net.dontdrinkandroot.utils.lang.StringUtils;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public abstract class AbstractLastfmWebServices implements LastfmWebServices {

	/** <a href="http://www.last.fm/api/account">The last.fm API key</a>. */
	private final String key;

	/** <a href="http://www.last.fm/api/account">The last.fm API secret</a>. */
	private final String secret;

	/** A map of time to lives for specific functions. */
	private final Map<String, Long> ttls = new HashMap<String, Long>();

	/** Performs the actual last.fm fetching. */
	private Fetcher fetcher;

	/** Our logger. */
	protected final Logger logger = LoggerFactory.getLogger(AbstractLastfmWebServices.class);


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
	public AbstractLastfmWebServices(final String key, final String secret) throws ParserConfigurationException {

		this(key, secret, new DefaultFetcher());
	}


	public AbstractLastfmWebServices(final String key, final String secret, final Fetcher fetcher) {

		this.fetcher = fetcher;
		this.key = key;
		this.secret = secret;
	}


	@Override
	public final <T extends Serializable> T fetch(final AuthenticatedGetQuery<T> query)
			throws LastfmWebServicesException {

		/* Add authentication info to parameters */
		final Map<String, String> parameters = query.getParameters();
		parameters.put("api_key", this.key);
		parameters.put("api_sig", this.generateSignature(parameters));

		/* Fetch from last.fm, this is uncached so go straight ahead */
		final LastfmResponse response = this.fetcher.get(parameters);

		/* Check if this was an error */
		this.checkForError(response.getDocument());

		/* Get first real element */
		final Element firstElement = this.getFirstElement(response.getDocument());

		return query.parse(firstElement);
	}


	@Override
	public final <T extends Serializable> T execute(final PostQuery<T> query) throws LastfmWebServicesException {

		/* Add authentication info to parameters */
		final Map<String, String> parameters = query.getParameters();
		parameters.put("api_key", this.key);
		parameters.put("api_sig", this.generateSignature(parameters));

		/* Fetch from last.fm */
		final LastfmResponse response = this.fetcher.post(parameters);

		/* Check if Last.fm signalled an error */
		this.checkForError(response.getDocument());

		/* Get first real element */
		final Element firstElement = this.getFirstElement(response.getDocument());

		/* Extract result */
		final T result = query.parse(firstElement);

		return result;
	}


	@SuppressWarnings("unchecked")
	@Override
	public final <T extends Serializable> T fetch(final UnauthenticatedGetQuery<T> query)
			throws LastfmWebServicesException {

		/* Get the parameters of the query and add the api key before fetching */
		final Map<String, String> fetchParameters = query.getParameters();
		fetchParameters.put("api_key", this.key);

		/*
		 * Try to fetch result from cache (by the original parameters) and throw exception if it was
		 * a cached exception
		 */
		final Serializable cacheObject = this.getFromCache(query.getParameters());
		if (cacheObject instanceof LastfmWebServicesException) {
			throw (LastfmWebServicesException) cacheObject;
		}
		T result = (T) cacheObject;

		/* Object not in cache, fetch it */
		if (result == null) {

			/* Fetch the document and have it parsed by the query */
			final LastfmResponse response = this.fetcher.get(fetchParameters);

			/* Check if Last.fm signaled an error and cache exception */
			try {
				this.checkForError(response.getDocument());
			} catch (final LastfmWebServicesException e) {
				Long exceptionTtl = this.getMethodTimeToLive("exception");
				if (exceptionTtl == null) {
					exceptionTtl = 0L;
				}
				this.putToCache(query.getParameters(), e, exceptionTtl);
				throw e;
			}

			/* Get first real element */
			final Element firstElement = this.getFirstElement(response.getDocument());

			/* Extract result */
			result = query.parse(firstElement);

			/* Determine time to live for this entry */
			final Long timeToLive = this.resolveTimeToLive(query, response, result);

			/* Store the new document in cache (witch the original parameters) */
			result = (T) this.putToCache(query.getParameters(), result, timeToLive);

		}

		return result;
	}


	@Override
	public final String getApiSecret() {

		return this.secret;
	}


	@Override
	public final String getApiKey() {

		return this.key;
	}


	@Override
	public Fetcher getFetcher() {

		return this.fetcher;
	}


	@Override
	public void setFetcher(final Fetcher fetcher) {

		this.fetcher = fetcher;
	}


	/**
	 * You can specify how long the data of each method should be cached. Usually this defaults to
	 * the expiration header sent by last.fm. The properties must be organized as follows: The key
	 * is the package and method separated by a dot, the value is the time to live in milliseconds.
	 * E.g.:
	 * 
	 * <pre>
	 * artist.getinfo = 2419200000
	 * </pre>
	 * 
	 * A special entry is exception, this is used to cache exceptions.
	 * 
	 * @param ttls
	 *            Properties containing method time to lives.
	 */
	public final void setTimeToLive(final Properties ttls) {

		for (final Object propKey : ttls.keySet()) {
			final String method = (String) propKey;
			final Long timeToLive = Long.parseLong(ttls.getProperty(method));
			this.logger.info(
					"Overwriting ttl for method {} with {}ms",
					new Object[] { method.toLowerCase(), timeToLive });
			this.ttls.put(method.toLowerCase(), timeToLive);
		}
	}


	/**
	 * Generate Last.fm Signature String from parameters. "Construct your api method signatures by
	 * first ordering all the parameters sent in your call alphabetically by parameter name and
	 * concatenating them into one string using a [name][value] scheme."
	 */
	private String generateSignature(final Map<String, String> parameters) {

		String sig = "";
		for (final Entry<String, String> parameter : parameters.entrySet()) {
			sig += parameter.getKey() + parameter.getValue();
		}
		sig += this.secret;

		return DigestUtils.md5Hex(sig);
	}


	/**
	 * Resolve the cache expiration for the given method.
	 * 
	 * @param method
	 *            The method to resolve the expiration for (e.g. "User.getFriends")
	 * @return The expiration or null if not defined
	 */
	private Long getMethodTimeToLive(final String method) {

		return this.ttls.get(method.toLowerCase());
	}


	/**
	 * Retrieve the first real element below &lt;lfm&gt;.
	 * 
	 * @param document
	 *            The deserialized Last.fm Document.
	 * @return The first real child element or null if not found.
	 */
	private Element getFirstElement(final Document document) {

		final Element lfm = document.getDocumentElement();
		final NodeList children = lfm.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			final Node childNode = children.item(i);
			if (childNode.getNodeType() == Node.ELEMENT_NODE) {
				return (Element) childNode;
			}
		}

		return null;
	}


	/**
	 * Resolve the time to live for this entry by first checking if it was null then trying to look
	 * it up from the overwritten method entries or finally assigning the value from the response.
	 * 
	 * @param <T>
	 *            Type of the query result.
	 * @param query
	 *            The original query.
	 * @param response
	 *            The last.fm response.
	 * @param result
	 *            The parsed result of the query.
	 * @return The time to live for this entry.
	 */
	private <T extends Serializable> Long resolveTimeToLive(
			final Query<T> query,
			final LastfmResponse response,
			final T result) {

		Long timeToLive;

		if (result == null) {
			timeToLive = this.getMethodTimeToLive("null");
		} else {
			timeToLive = this.getMethodTimeToLive(query.getMethod());
		}

		if (timeToLive == null) {
			timeToLive = response.getExpiration() - System.currentTimeMillis();
		}

		return timeToLive;
	}


	/**
	 * Check if the Response contains an error element and throw an Exception with the corresponding
	 * error code and message.
	 * 
	 * @param document
	 *            The Response Document to check.
	 */
	private void checkForError(final Document document) throws LastfmWebServicesException {

		final NodeList errors = document.getDocumentElement().getElementsByTagName("error");
		if (errors != null && errors.getLength() > 0) {
			final Element error = (Element) errors.item(0);
			throw new LastfmWebServicesException(
					Integer.parseInt(error.getAttribute("code")),
					StringUtils.trim(error.getTextContent()));
		}
	}


	/**
	 * Try to fetch the result from cache.
	 * 
	 * @param <T>
	 *            Return type of the query.
	 * @param parameters
	 *            The parameters of the request.
	 * @return The entry if found or null.
	 */
	protected abstract Serializable getFromCache(final Map<String, String> parameters);


	/**
	 * Store result in cache.
	 * 
	 * @param <T>
	 *            Return type of the query.
	 * @param parameters
	 *            The parameters of the request.
	 * @param result
	 *            The query result.
	 * @param timeToLive
	 *            The time to live for the cache entry in milliseconds.
	 * @return The cached result or the original result on caching errors.
	 */
	protected abstract Serializable putToCache(
			final Map<String, String> parameters,
			final Serializable result,
			final long timeToLive);

}
