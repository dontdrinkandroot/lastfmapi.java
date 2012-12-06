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

import net.dontdrinkandroot.lastfm.api.xml.DomUtils;

import org.w3c.dom.Element;


public class Coordinate extends LfmEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private Float latitude;

	private Float longitude;


	/**
	 * Create a Coordinate from an XML Element.
	 * 
	 * @param element
	 *            The XML Element to create the Coordinate from.
	 */
	public Coordinate(final Element element) {

		this.setLatitude(DomUtils.toFloat(DomUtils.getChildByTagName(element, "geo:lat")));
		this.setLongitude(DomUtils.toFloat(DomUtils.getChildByTagName(element, "geo:long")));
	}


	public final Float getLatitude() {

		return this.latitude;
	}


	public final Float getLongitude() {

		return this.longitude;
	}


	public final void setLatitude(final Float latitude) {

		this.latitude = latitude;
	}


	public final void setLongitude(final Float longitude) {

		this.longitude = longitude;
	}


	public final Float lat() {

		return this.getLatitude();
	}


	public final Float lon() {

		return this.getLongitude();
	}

}
