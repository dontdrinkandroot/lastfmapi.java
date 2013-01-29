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


public class Streamable implements Serializable {

	private static final long serialVersionUID = 1L;

	private Boolean streamable;

	private Boolean fullTrack;


	protected Streamable() {

		/* Default constructor for reflection instantiation */
	}


	/**
	 * Create streamable information from dom4j element.
	 * 
	 * @param element
	 *            The dom4j element to convert.
	 */
	public Streamable(final Element element) {

		this.setStreamable(DomUtils.toBoolean(element));
		this.setFullTrack(DomUtils.toBoolean(element, "fulltrack"));
	}


	public final boolean isStreamable() {

		if (this.streamable != null && this.streamable.equals(Boolean.TRUE)) {
			return true;
		}
		return false;
	}


	public final boolean isFullTrackStreamable() {

		if (this.fullTrack != null && this.fullTrack.equals(Boolean.TRUE)) {
			return true;
		}
		return false;
	}


	public final Boolean getStreamable() {

		return this.streamable;
	}


	public final void setStreamable(final Boolean streamable) {

		this.streamable = streamable;
	}


	public final Boolean getFullTrack() {

		return this.fullTrack;
	}


	public final void setFullTrack(final Boolean fullTrack) {

		this.fullTrack = fullTrack;
	}

}
