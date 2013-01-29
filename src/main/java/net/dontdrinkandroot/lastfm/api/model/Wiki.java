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
package net.dontdrinkandroot.lastfm.api.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import net.dontdrinkandroot.lastfm.api.xml.DomUtils;

import org.w3c.dom.Element;


public class Wiki implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final transient SimpleDateFormat PUBLISHED_FORMAT = new SimpleDateFormat(
			"EEE, d MMM yyyy HH:mm:ss Z",
			Locale.UK);

	private Date published;

	private String content;


	protected Wiki() {

		/* Default constructor for reflection instantiation */
	}


	/**
	 * Generate Wiki from an XML Element.
	 * 
	 * @param element
	 *            The XML Element to convert.
	 */
	public Wiki(final Element element) {

		try {
			final String publishedText = DomUtils.getElementText(element, "published");
			if (publishedText != null && !publishedText.equals("")) {
				this.published = Wiki.PUBLISHED_FORMAT.parse(DomUtils.getElementText(element, "published"));
			}
		} catch (final ParseException e) {
			e.printStackTrace();
		}
		this.content = DomUtils.getElementText(element, "content");
	}


	public final Date getPublished() {

		return this.published;
	}


	public final void setPublished(final Date published) {

		this.published = published;
	}


	public final String getContent() {

		return this.content;
	}


	public final void setContent(final String content) {

		this.content = content;
	}

}
