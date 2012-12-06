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
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Set;

import net.dontdrinkandroot.lastfm.api.xml.DomUtils;

import org.w3c.dom.Element;


public class Station implements Serializable {

	private static final long serialVersionUID = 1L;

	private String type;

	private String name;

	private URL url;

	private Boolean supportsDiscovery;


	/**
	 * Create a Station from a dom4j Element.
	 * 
	 * @param root
	 *            The element to create the Station from.
	 */
	public Station(final Element root) {

		this.setType(DomUtils.toNonEmptyString(DomUtils.getChildByTagName(root, "type")));
		this.setName(DomUtils.toNonEmptyString(DomUtils.getChildByTagName(root, "name")));
		this.setUrl(DomUtils.toHttpURL(DomUtils.getChildByTagName(root, "url")));
		this.setSupportsDiscovery(DomUtils.toBoolean(DomUtils.getChildByTagName(root, "supportsdiscovery")));

	}


	public final String getType() {

		return this.type;
	}


	public final void setType(final String type) {

		this.type = type;
	}


	public final String getName() {

		return this.name;
	}


	public final void setName(final String name) {

		this.name = name;
	}


	public final URL getUrl() {

		return this.url;
	}


	public final void setUrl(final URL url) {

		this.url = url;
	}


	public final Boolean getSupportsDiscovery() {

		return this.supportsDiscovery;
	}


	public final void setSupportsDiscovery(final Boolean supportsDiscovery) {

		this.supportsDiscovery = supportsDiscovery;
	}


	/* Create Station URLs */

	/**
	 * Create the Station URI needed to play an Artists similar artists radio.
	 */
	public static URI getSimilarArtistsStationUri(final String artistName) {

		try {
			return new URI("lastfm://artist/" + URLEncoder.encode(artistName, "UTF-8") + "/similarartists");
		} catch (final UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		} catch (final URISyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}


	/**
	 * Create the Station URI needed to play a users mix radio.
	 */
	public static URI getMixRadioStationUri(final String username) {

		try {
			return new URI("lastfm://user/" + URLEncoder.encode(username, "UTF-8") + "/mix");
		} catch (final UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		} catch (final URISyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}


	/**
	 * Create the URI needed to play an Artists top fans radio.
	 * 
	 * @param artistName
	 * @return
	 */
	public static URI getTopFansStationUri(final String artistName) {

		try {
			return new URI("lastfm://artist/" + URLEncoder.encode(artistName, "UTF-8") + "/fans");
		} catch (final URISyntaxException e) {
			e.printStackTrace();
			return null;
		} catch (final UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}


	/**
	 * Create the URI needed to play a Users library radio.
	 * 
	 * @param userName
	 * @return
	 */
	public static URI getLibraryStationUri(final String userName) {

		try {
			return new URI("lastfm://user/" + URLEncoder.encode(userName, "UTF-8") + "/library");
		} catch (final URISyntaxException e) {
			e.printStackTrace();
			return null;
		} catch (final UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}


	/**
	 * Create the URI needed to play a Users neighbour radio.
	 * 
	 * @param userName
	 * @return
	 */
	public static URI getNeighboursStationUri(final String userName) {

		try {
			return new URI("lastfm://user/" + URLEncoder.encode(userName, "UTF-8") + "/neighbours");
		} catch (final URISyntaxException e) {
			e.printStackTrace();
			return null;
		} catch (final UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}


	/**
	 * Create the URI needed to play a Users loved tracks radio.
	 * 
	 * @param userName
	 * @return
	 */
	public static URI getLovedTracksStationUri(final String userName) {

		try {
			return new URI("lastfm://user/" + URLEncoder.encode(userName, "UTF-8") + "/loved");
		} catch (final URISyntaxException e) {
			e.printStackTrace();
			return null;
		} catch (final UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}


	/**
	 * Create a URI needed to play a Users recommended radio.
	 * 
	 * @param userName
	 * @return
	 */
	public static URI getRecommendationStationUri(final String userName) {

		try {
			return new URI("lastfm://user/" + URLEncoder.encode(userName, "UTF-8") + "/recommended");
		} catch (final URISyntaxException e) {
			e.printStackTrace();
			return null;
		} catch (final UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}


	/**
	 * Create the URI needed to play a Groups members radio.
	 * 
	 * @param groupName
	 * @return
	 */
	public static URI getGroupStationUri(final String groupName) {

		try {
			return new URI("lastfm://group/" + URLEncoder.encode(groupName, "UTF-8"));
		} catch (final URISyntaxException e) {
			e.printStackTrace();
			return null;
		} catch (final UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}


	/**
	 * Create the URI needed to play a Tags global radio.
	 * 
	 * @param tagName
	 * @return
	 */
	public static URI getGlobalTagStationUri(final String tagName) {

		try {
			return new URI("lastfm://globaltags/" + URLEncoder.encode(tagName, "UTF-8"));
		} catch (final URISyntaxException e) {
			e.printStackTrace();
			return null;
		} catch (final UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}


	/**
	 * Create the URI needed to play a Users tag radio.
	 * 
	 * @param userName
	 * @param tagName
	 * @return
	 */
	public static URI getUserTagStation(final String userName, final String tagName) {

		try {
			return new URI("lastfm://usertags/"
					+ URLEncoder.encode(userName, "UTF-8")
					+ "/"
					+ URLEncoder.encode(tagName, "UTF-8"));
		} catch (final URISyntaxException e) {
			e.printStackTrace();
			return null;
		} catch (final UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}


	public static URI getMultiTagStationUri(Set<String> tagNames) {

		tagNames.remove(null);
		try {
			String uriString = "lastfm://tag/";
			int count = 0;
			for (String tagName : tagNames) {
				if (count != 0) {
					uriString += "*";
				}
				uriString += URLEncoder.encode(tagName, "UTF-8");
				count++;
			}

			return new URI(uriString);

		} catch (URISyntaxException e) {
			e.printStackTrace();
			return null;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}


	public static URI getMultiArtistStationUri(Set<Long> artistIds) {

		artistIds.remove(null);
		try {
			String uriString = "lastfm://artists/";
			int count = 0;
			for (Long artistId : artistIds) {
				if (count != 0) {
					uriString += ",";
				}
				uriString += artistId.toString();
				count++;
			}

			return new URI(uriString);

		} catch (URISyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}

}
