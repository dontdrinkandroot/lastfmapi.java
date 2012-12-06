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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.dontdrinkandroot.lastfm.api.LastfmWebServicesException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;


/**
 * This class is responsible for actually fetching the data from last.fm and converting it to a dom
 * Document.
 * 
 * @author Philip W. Sorst
 * 
 */
public class Fetcher {

	/** The base URL, needed if you want to proxy requests somewhere. */
	private URL baseUrl;

	/** Default connection timeout */
	private final int defaultConnectionTimeout = 5000;

	/** Connection timeout */
	private final int connectionTimeout = this.defaultConnectionTimeout;

	/** We need to enforce the last.fm fetch limit */
	private final FetchLimitEnforcer limitEnforcer = new FetchLimitEnforcer();

	private final Logger logger = LoggerFactory.getLogger(Fetcher.class);

	private final DocumentBuilderFactory factory;


	public Fetcher() throws ParserConfigurationException {

		/* Set base URL */
		try {
			this.baseUrl = new URL("http://ws.audioscrobbler.com/2.0/");
		} catch (final MalformedURLException e) {
			/* This really shouldn't happen, if it does, fail hard */
			throw new RuntimeException(e);
		}

		/* Create JAXB unmarshaller */
		/*
		 * We explicity choose the SAXParser Factory here as there may be many implementations on
		 * the classpath
		 */
		System.setProperty(
				"javax.xml.parsers.SAXParserFactory",
				"com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl");

		this.factory = DocumentBuilderFactory.newInstance();
	}


	public final LastfmResponse get(final Map<String, String> parameters) throws LastfmWebServicesException {

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


	public final LastfmResponse post(final Map<String, String> parameters) throws LastfmWebServicesException {

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


	private String buildParameterString(final Map<String, String> parameters) {

		final StringBuffer parameterString = new StringBuffer();
		int count = 0;
		for (final Entry<String, String> parameter : parameters.entrySet()) {
			if (count != 0) {
				parameterString.append("&");
			}
			try {
				parameterString.append(URLEncoder.encode(parameter.getKey(), "UTF-8")
						+ "="
						+ URLEncoder.encode(parameter.getValue(), "UTF-8"));
			} catch (final UnsupportedEncodingException e) {
				/* UTF-8 should be always available */
				throw new RuntimeException("Encoding UTF-8 not found!", e);
			}
			count++;
		}

		return parameterString.toString();
	}


	private LastfmResponse fetchResponse(final HttpURLConnection conn) throws LastfmWebServicesException {

		/* Open input stream */
		InputStream is = null;
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


	/**
	 * 
	 * @param is
	 *            The InputStream to unmarshall.
	 * @return The Document that was parsed.
	 * @throws LastfmWebServicesException
	 *             Thrown if reading the document fails.
	 */
	private Document parseDoc(final InputStream is) throws LastfmWebServicesException {

		/* For debugging */
		// String xml = null;
		// try {
		// xml = new String(IOUtils.toByteArray(is));
		// System.out.println(xml);
		// } catch (final IOException e) {
		// e.printStackTrace();
		// }
		// final InputStream is2 = IOUtils.toInputStream(xml);

		try {

			final Document doc = this.factory.newDocumentBuilder().parse(is);
			return doc;

		} catch (final SAXException e) {
			throw new LastfmWebServicesException(LastfmWebServicesException.READING_DOC_FAILED, e.getMessage());
		} catch (final IOException e) {
			throw new LastfmWebServicesException(LastfmWebServicesException.READING_DOC_FAILED, e.getMessage());
		} catch (final ParserConfigurationException e) {
			throw new LastfmWebServicesException(LastfmWebServicesException.READING_DOC_FAILED, e.getMessage());
		}
	}


	private HttpURLConnection openConnection(final URL url) throws LastfmWebServicesException {

		try {

			final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(this.connectionTimeout);
			conn.setConnectTimeout(this.connectionTimeout);

			return conn;

		} catch (final IOException e) {
			throw new LastfmWebServicesException(LastfmWebServicesException.OPENING_CONNECTION_FAILED, e.getMessage());
		}
	}


	private URL buildUrl(final Map<String, String> parameters) {

		/* Build url */
		final String urlString = this.baseUrl.toString() + "?" + this.buildParameterString(parameters);

		try {

			return new URL(urlString);

		} catch (final MalformedURLException e) {
			/* Shouldn't happen */
			this.logger.error("Constructed URL is invalid", e);
			return null;
		}
	}


	public final double getWebRequestsPerSecond() {

		return this.limitEnforcer.getWebRequestsPerSecond();
	}

}
