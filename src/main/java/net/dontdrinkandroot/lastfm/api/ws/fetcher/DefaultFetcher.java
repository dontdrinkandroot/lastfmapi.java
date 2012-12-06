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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import net.dontdrinkandroot.lastfm.api.LastfmWebServicesException;
import net.dontdrinkandroot.lastfm.api.ws.LastfmResponse;

import org.w3c.dom.Document;


/**
 * This class is responsible for actually fetching the data from last.fm and converting it to a dom
 * Document.
 * 
 * @author Philip W. Sorst
 * 
 */
public class DefaultFetcher extends AbstractFetcher implements Fetcher {

	/** Number of Web requests that have been made */
	private long numWebRequests;

	/** We need to enforce the last.fm fetch limit */
	private final FetchLimitEnforcer limitEnforcer = new FetchLimitEnforcer();


	public DefaultFetcher() throws ParserConfigurationException {

		super();
	}


	@Override
	public LastfmResponse get(final Map<String, String> parameters) throws LastfmWebServicesException {

		final URL url = this.buildUrl(parameters);

		this.logger.trace("Query url is " + url);

		/* Respect last.fm fetch limit */
		this.limitEnforcer.waitForTimeslot();

		/* Open http connection */
		final HttpURLConnection conn = this.openConnection(url);

		/* Do the actual fetch */
		final LastfmResponse response = this.fetchResponse(conn);

		return response;
	}


	@Override
	public LastfmResponse post(final Map<String, String> parameters) throws LastfmWebServicesException {

		/* Respect last.fm fetch limit */
		this.limitEnforcer.waitForTimeslot();

		final String parameterString = this.buildParameterString(parameters);

		this.logger.trace("ParameterString: " + parameterString);

		/* Open http connection */
		final HttpURLConnection conn = this.openConnection(this.baseUrl);

		/* Mark as POST */
		try {
			conn.setRequestMethod("POST");
		} catch (final ProtocolException e) {
			throw new LastfmWebServicesException(LastfmWebServicesException.UNSUPPORTED_PROTOCOL, e.getMessage());
		}
		conn.setDoOutput(true);

		/* Write POST data */
		try {
			final OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
			osw.write(parameterString);
			osw.flush();
		} catch (final IOException e) {
			throw new LastfmWebServicesException(LastfmWebServicesException.WRITING_POST_DATA_FAILED, e.getMessage());
		}

		final LastfmResponse response = this.fetchResponse(conn);

		return response;
	}


	@Override
	public final double getWebRequestsPerSecond() {

		return this.limitEnforcer.getWebRequestsPerSecond();
	}


	protected LastfmResponse fetchResponse(final HttpURLConnection conn) throws LastfmWebServicesException {

		/* Open input stream */
		InputStream is = null;
		this.numWebRequests++;
		try {
			is = conn.getInputStream();
		} catch (final IOException e) {
			is = conn.getErrorStream();
		}

		if (is == null) {
			conn.disconnect();
			throw new LastfmWebServicesException(LastfmWebServicesException.STREAM_WAS_NULL, "Stream was null");
		}

		/* Read document from inputstream */
		final Document doc = this.parseDoc(is);

		/* Close connection */
		try {
			is.close();
			conn.disconnect();
		} catch (final IOException e) {
			this.logger.error("Closing Http connection failed", e);
		}

		final long expiration = conn.getExpiration();
		final LastfmResponse response = new LastfmResponse(doc, expiration);

		return response;
	}

}
