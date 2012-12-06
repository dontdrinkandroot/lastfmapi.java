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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.ParserConfigurationException;

import net.dontdrinkandroot.lastfm.api.LastfmWebServicesException;
import net.dontdrinkandroot.lastfm.api.ws.LastfmResponse;
import net.dontdrinkandroot.utils.lang.time.DateUtils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;


public class DiskBufferedFetcher extends DefaultFetcher {

	public static String XML_PREFIX = "xml/";

	private File xmlWriteBase = null;


	public DiskBufferedFetcher() throws ParserConfigurationException {

		super();

		final URL xmlClassesBase = this.getClass().getClassLoader().getResource("xml/");
		if (xmlClassesBase == null) {
			throw new RuntimeException("XML Directory not found");
		}

		final File file = new File(xmlClassesBase.getFile());
		if (file.exists()) {
			this.xmlWriteBase =
					new File(file.getParentFile().getParentFile().getParentFile(), "src/test/resources/xml/");
			this.logger.info("XML Base found at " + this.xmlWriteBase);
		} else {
			this.logger.info("NO XML Base found at " + this.xmlWriteBase);
		}
	}


	@Override
	public LastfmResponse get(final Map<String, String> parameters) throws LastfmWebServicesException {

		final String xmlFileName = this.buildXmlFileName(parameters, "get");
		final InputStream is =
				this.getClass().getClassLoader().getResourceAsStream(DiskBufferedFetcher.XML_PREFIX + xmlFileName);
		if (is != null) {

			final Document doc = this.parseDoc(is);
			IOUtils.closeQuietly(is);

			return new LastfmResponse(doc, System.currentTimeMillis() + DateUtils.MINUTES_PER_HOUR);
		}

		final URL url = this.buildUrl(parameters);

		this.logger.trace("Query url is " + url);

		/* Open http connection */
		final HttpURLConnection conn = this.openConnection(url);

		File targetFile = null;
		if (this.xmlWriteBase != null) {
			targetFile = new File(this.xmlWriteBase, xmlFileName);
		}

		/* Do the actual fetch */
		final LastfmResponse response = this.fetchResponse(conn, targetFile);

		return response;
	}


	// @Override
	// public LastfmResponse post(final Map<String, String> parameters) throws
	// LastfmWebServicesException {
	//
	// final String xmlFileName = this.buildXmlFileName(parameters, "post");
	// final InputStream is =
	// this.getClass().getClassLoader().getResourceAsStream(DiskBufferedFetcher.XML_PREFIX +
	// xmlFileName);
	// if (is != null) {
	//
	// final Document doc = this.parseDoc(is);
	// IOUtils.closeQuietly(is);
	//
	// return new LastfmResponse(doc, System.currentTimeMillis() + DateUtils.MINUTES_PER_HOUR);
	// }
	//
	// final String parameterString = this.buildParameterString(parameters);
	//
	// this.logger.trace("ParameterString: " + parameterString);
	//
	// /* Open http connection */
	// final HttpURLConnection conn = this.openConnection(this.baseUrl);
	//
	// /* Mark as POST */
	// try {
	// conn.setRequestMethod("POST");
	// } catch (final ProtocolException e) {
	// throw new LastfmWebServicesException(LastfmWebServicesException.UNSUPPORTED_PROTOCOL,
	// e.getMessage());
	// }
	// conn.setDoOutput(true);
	//
	// /* Write POST data */
	// try {
	// final OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
	// osw.write(parameterString);
	// osw.flush();
	// } catch (final IOException e) {
	// throw new LastfmWebServicesException(LastfmWebServicesException.WRITING_POST_DATA_FAILED,
	// e.getMessage());
	// }
	//
	// File targetFile = null;
	// if (this.xmlWriteBase != null) {
	// targetFile = new File(this.xmlWriteBase, xmlFileName);
	// }
	//
	// final LastfmResponse response = this.fetchResponse(conn, targetFile);
	//
	// return response;
	// }

	private String buildXmlFileName(final Map<String, String> parameters, final String httpMethod) {

		final String method = parameters.get("method");

		final StringBuffer rest = new StringBuffer(method + "_" + httpMethod);
		for (final Entry<String, String> entry : parameters.entrySet()) {
			final String key = entry.getKey();
			final String value = entry.getValue();
			if (!"method".equals(key) && !"api_key".equals(key) && !"sk".equals(key)) {
				rest.append("_");
				rest.append(key);
				rest.append("_");
				rest.append(value);
			}
		}
		rest.append(".xml");

		return rest.toString();
	}


	protected LastfmResponse fetchResponse(final HttpURLConnection conn, final File targetFile)
			throws LastfmWebServicesException {

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

		if (targetFile != null) {
			this.logger.info("Writing response to {}", targetFile);
			try {
				FileUtils.copyInputStreamToFile(is, targetFile);
			} catch (final IOException e) {
				throw new RuntimeException(e);
			}
		}

		try {
			is = new FileInputStream(targetFile);
		} catch (final FileNotFoundException e) {
			throw new RuntimeException(e);
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
