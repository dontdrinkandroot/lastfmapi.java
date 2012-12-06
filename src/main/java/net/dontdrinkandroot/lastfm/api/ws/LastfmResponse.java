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

import org.w3c.dom.Document;

/**
 * Encapsulates the last.fm response, this means the deserialized Xml Document and the Expiration as
 * returned by the HTTP header.
 *
 * @author Philip W. Sorst
 */
public class LastfmResponse implements Serializable {

	private static final long serialVersionUID = 5080757603374924536L;

	private Document doc;
	private Long expiration;


	public LastfmResponse(
			final Document doc,
			final Long expiration
	) {

		this.doc = doc;
		this.expiration = expiration;
	}


	public final Document getDocument() {

		return this.doc;
	}


	public final void setDocument(final Document doc) {

		this.doc = doc;
	}


	public final Long getExpiration() {

		return this.expiration;
	}


	public final void setExpiration(final Long expiration) {

		this.expiration = expiration;
	}

}
