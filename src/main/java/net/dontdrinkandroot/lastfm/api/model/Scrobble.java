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

import net.dontdrinkandroot.lastfm.api.xml.DomUtils;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.w3c.dom.Element;


public class Scrobble extends LfmEntity {

	private String track;

	private boolean trackCorrected;

	private String artist;

	private boolean artistCorrected;

	private String album;

	private boolean albumCorrected;

	private String albumArtist;

	private boolean albumArtistCorrected;

	private Long timestamp;

	private String ignoredMessage;

	private int ignoredMessageCode;


	public Scrobble(Element element) {

		super(element);

		// TODO Parsing is currently done by hand as the entites are specific to srcobble and we
		// don't want to include all those extra values in the real entites
		Element trackElement = DomUtils.getChildByTagName(element, "track");
		this.setTrack(DomUtils.toNonEmptyString(trackElement));
		this.setTrackCorrected(DomUtils.toBoolean(trackElement, "corrected"));

		Element artistElement = DomUtils.getChildByTagName(element, "artist");
		this.setArtist(DomUtils.toNonEmptyString(artistElement));
		this.setArtistCorrected(DomUtils.toBoolean(artistElement, "corrected"));

		Element albumElement = DomUtils.getChildByTagName(element, "album");
		this.setAlbum(DomUtils.toNonEmptyString(albumElement));
		this.setAlbumCorrected(DomUtils.toBoolean(albumElement, "corrected"));

		Element albumArtistElement = DomUtils.getChildByTagName(element, "albumArtist");
		this.setAlbumArtist(DomUtils.toNonEmptyString(albumArtistElement));
		this.setAlbumCorrected(DomUtils.toBoolean(albumArtistElement, "corrected"));

		Element timestampElement = DomUtils.getChildByTagName(element, "timestamp");
		this.setTimestamp(DomUtils.toLong(timestampElement) * 1000L);

		Element ignoredMessageElement = DomUtils.getChildByTagName(element, "ignoredMessage");
		this.setIgnoredMessage(DomUtils.toNonEmptyString(ignoredMessageElement));
		this.setIgnoredMessageCode(DomUtils.toInteger(ignoredMessageElement, "code"));
	}


	public String getTrack() {

		return this.track;
	}


	public void setTrack(String track) {

		this.track = track;
	}


	public boolean isTrackCorrected() {

		return this.trackCorrected;
	}


	public void setTrackCorrected(boolean trackCorrected) {

		this.trackCorrected = trackCorrected;
	}


	public String getArtist() {

		return this.artist;
	}


	public void setArtist(String artist) {

		this.artist = artist;
	}


	public boolean isArtistCorrected() {

		return this.artistCorrected;
	}


	public void setArtistCorrected(boolean artistCorrected) {

		this.artistCorrected = artistCorrected;
	}


	public String getAlbum() {

		return this.album;
	}


	public void setAlbum(String album) {

		this.album = album;
	}


	public boolean isAlbumCorrected() {

		return this.albumCorrected;
	}


	public void setAlbumCorrected(boolean albumCorrected) {

		this.albumCorrected = albumCorrected;
	}


	public String getAlbumArtist() {

		return this.albumArtist;
	}


	public void setAlbumArtist(String albumArtist) {

		this.albumArtist = albumArtist;
	}


	public boolean isAlbumArtistCorrected() {

		return this.albumArtistCorrected;
	}


	public void setAlbumArtistCorrected(boolean albumArtistCorrected) {

		this.albumArtistCorrected = albumArtistCorrected;
	}


	public Long getTimestamp() {

		return this.timestamp;
	}


	public void setTimestamp(Long timestamp) {

		this.timestamp = timestamp;
	}


	public String getIgnoredMessage() {

		return this.ignoredMessage;
	}


	public void setIgnoredMessage(String ignoredMessage) {

		this.ignoredMessage = ignoredMessage;
	}


	public int getIgnoredMessageCode() {

		return this.ignoredMessageCode;
	}


	public void setIgnoredMessageCode(int ignoredMessageCode) {

		this.ignoredMessageCode = ignoredMessageCode;
	}


	@Override
	public String toString() {

		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
