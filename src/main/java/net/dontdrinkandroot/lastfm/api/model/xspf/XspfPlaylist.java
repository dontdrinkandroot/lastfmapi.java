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

import java.io.Serializable;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.dontdrinkandroot.lastfm.api.xml.DomUtils;

import org.w3c.dom.Element;


public class XspfPlaylist implements Serializable {

	private static final long serialVersionUID = 1L;

	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

	private Integer version = 1;

	private String title;

	private String creator;

	private String annotation;

	private URI info;

	private URI location;

	private URI identifier;

	private URI image;

	private Date date;

	private URI license;

	private List<XspfAttribution> attributions;

	private List<XspfLink> links;

	private List<XspfMeta> metas;

	private List<XspfExtension> extensions;

	private List<XspfTrack> tracks;


	public XspfPlaylist(final Element root) {

		this.version = DomUtils.toInteger(root, "version");
		this.title = DomUtils.getElementText(root, "title");
		this.creator = DomUtils.getElementText(root, "creator");
		final String dateString = DomUtils.getElementText(root, "date");
		if (dateString != null) {
			try {
				this.date = this.dateFormat.parse(dateString);
			} catch (final ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		final List<Element> linkElements = DomUtils.getChildrenByTagName(root, "link");
		this.links = new ArrayList<XspfLink>();
		for (final Element linkElement : linkElements) {
			this.links.add(new XspfLink(linkElement));
		}
		this.tracks = new ArrayList<XspfTrack>();
		final Element trackListElement = DomUtils.getChildByTagName(root, "trackList");
		final List<Element> trackElements = DomUtils.getChildrenByTagName(trackListElement, "track");
		for (final Element trackElement : trackElements) {
			this.tracks.add(new XspfTrack(trackElement));
		}
	}


	public Integer getVersion() {

		return this.version;
	}


	public void setVersion(final Integer version) {

		this.version = version;
	}


	public String getTitle() {

		return this.title;
	}


	public void setTitle(final String title) {

		this.title = title;
	}


	public String getCreator() {

		return this.creator;
	}


	public void setCreator(final String creator) {

		this.creator = creator;
	}


	public String getAnnotation() {

		return this.annotation;
	}


	public void setAnnotation(final String annotation) {

		this.annotation = annotation;
	}


	public URI getInfo() {

		return this.info;
	}


	public void setInfo(final URI info) {

		this.info = info;
	}


	public URI getLocation() {

		return this.location;
	}


	public void setLocation(final URI location) {

		this.location = location;
	}


	public URI getIdentifier() {

		return this.identifier;
	}


	public void setIdentifier(final URI identifier) {

		this.identifier = identifier;
	}


	public URI getImage() {

		return this.image;
	}


	public void setImage(final URI image) {

		this.image = image;
	}


	public Date getDate() {

		return this.date;
	}


	public void setDate(final Date date) {

		this.date = date;
	}


	public URI getLicense() {

		return this.license;
	}


	public void setLicense(final URI license) {

		this.license = license;
	}


	public List<XspfAttribution> getAttributions() {

		return this.attributions;
	}


	public void setAttributions(final List<XspfAttribution> attributions) {

		this.attributions = attributions;
	}


	public List<XspfLink> getLinks() {

		return this.links;
	}


	public void setLinks(final List<XspfLink> links) {

		this.links = links;
	}


	public List<XspfMeta> getMetas() {

		return this.metas;
	}


	public void setMetas(final List<XspfMeta> metas) {

		this.metas = metas;
	}


	public List<XspfExtension> getExtensions() {

		return this.extensions;
	}


	public void setExtensions(final List<XspfExtension> extensions) {

		this.extensions = extensions;
	}


	public List<XspfTrack> getTracks() {

		return this.tracks;
	}


	public void setTracks(final List<XspfTrack> tracks) {

		this.tracks = tracks;
	}

}
