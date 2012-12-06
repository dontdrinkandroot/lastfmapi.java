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
import java.util.Date;

import net.dontdrinkandroot.lastfm.api.xml.DomUtils;

import org.w3c.dom.Element;


public class ChartRange implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date from;

	private Date to;


	/**
	 * Create ChartRange from a dom element.
	 * 
	 * @param element
	 *            The element to convert.
	 */
	public ChartRange(final Element element) {

		this.setFrom(DomUtils.toDateFromSecondsTimestamp(element, "from"));
		this.setTo(DomUtils.toDateFromSecondsTimestamp(element, "to"));
	}


	public final Date getFrom() {

		return this.from;
	}


	public final void setFrom(final Date from) {

		this.from = from;
	}


	public final Date getTo() {

		return this.to;
	}


	public final void setTo(final Date to) {

		this.to = to;
	}

}
