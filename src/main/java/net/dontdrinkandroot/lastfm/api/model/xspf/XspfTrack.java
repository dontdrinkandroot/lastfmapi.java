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
package net.dontdrinkandroot.lastfm.api.model.xspf;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import net.dontdrinkandroot.lastfm.api.xml.DomUtils;

import org.w3c.dom.DOMException;
import org.w3c.dom.Element;

public class XspfTrack {

	private List<URI> locations;

	private URI identifier;

	private String creator;

	private String title;

	private String album;

	private Integer duration;

	private URI image;


	public XspfTrack(final Element trackElement) {

		this.identifier = DomUtils.toURI(DomUtils.getChildByTagName(trackElement, "identifier"));
		this.image = DomUtils.toURI(DomUtils.getChildByTagName(trackElement, "image"));
		this.creator = DomUtils.getElementText(trackElement, "creator");
		this.title = DomUtils.getElementText(trackElement, "title");
		this.album = DomUtils.getElementText(trackElement, "album");
		this.duration = DomUtils.toInteger(DomUtils.getChildByTagName(trackElement, "duration"));
		this.locations = new ArrayList<URI>();
		final List<Element> locationElements = DomUtils.getChildrenByTagName(trackElement, "location");
		for (final Element locationElement : locationElements) {
			try {
				this.locations.add(new URI(locationElement.getTextContent()));
			} catch (final DOMException e) {
				e.printStackTrace();
			} catch (final URISyntaxException e) {
				e.printStackTrace();
			}
		}
	}


	public final List<URI> getLocations() {

		return this.locations;
	}


	public void setLocations(final List<URI> locations) {

		this.locations = locations;
	}


	public String getTitle() {

		return this.title;
	}


	public void setTitle(final String title) {

		this.title = title;
	}


	public String getAlbum() {

		return this.album;
	}


	public String getCreator() {

		return this.creator;
	}


	public Integer getDuration() {

		return this.duration;
	}


	public URI getIdentifier() {

		return this.identifier;
	}


	public URI getImage() {

		return this.image;
	}


	public void setAlbum(final String album) {

		this.album = album;
	}


	public void setCreator(final String creator) {

		this.creator = creator;
	}


	public void setDuration(final Integer duration) {

		this.duration = duration;
	}


	public void setIdentifier(final URI identifier) {

		this.identifier = identifier;
	}


	public void setImage(final URI image) {

		this.image = image;
	}
}
