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

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.dontdrinkandroot.lastfm.api.model.entitytypes.AlbumEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.ArtistEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.DateEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.DurationEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.IdEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.MusicBrainzEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.NameEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.NowPlayingEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.PlayCountEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.RankEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.StreamableEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.UrlEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.UserPlayCountEntity;
import net.dontdrinkandroot.lastfm.api.queries.AbstractPostQuery;
import net.dontdrinkandroot.lastfm.api.queries.AbstractUnauthenticatedGetQuery;
import net.dontdrinkandroot.lastfm.api.queries.PostQuery;
import net.dontdrinkandroot.lastfm.api.queries.UnauthenticatedGetQuery;
import net.dontdrinkandroot.lastfm.api.xml.DomUtils;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public class Track extends LfmEntity
		implements MusicBrainzEntity, NameEntity, UrlEntity, PlayCountEntity, UserPlayCountEntity, IdEntity,
		DurationEntity, StreamableEntity, DateEntity, RankEntity, AlbumEntity, NowPlayingEntity, ArtistEntity {

	private static final long serialVersionUID = 2L;

	private Integer rank;

	private Long id;

	private String name;

	private String mbid;

	private URL url;

	private Long duration;

	private Streamable streamable;

	private Integer listeners;

	private Integer playCount;

	private Integer userPlayCount;

	private Artist artist;

	private Float match;

	private Album album;

	private List<Album> albums;

	private List<Tag> tags;

	private Wiki wiki;

	private Date date;

	private Boolean nowPlaying;


	public Track(String name) {

		this.name = name;
	}


	/**
	 * Create a track from a dom4j element.
	 * 
	 * @param element
	 *            The dom4j element to convert.
	 */
	public Track(final Element element) {

		/*
		 * NOTE: Tracks can also have images, but as those are the images of the corresponding
		 * albums they are not included.
		 */

		super(element);
	}


	@Override
	public final String getName() {

		return this.name;
	}


	@Override
	public final URL getUrl() {

		return this.url;
	}


	@Override
	public final Date getDate() {

		return this.date;
	}


	@Override
	public final void setDate(final Date date) {

		this.date = date;
	}


	@Override
	public final void setName(final String name) {

		this.name = name;
	}


	@Override
	public final void setUrl(final URL url) {

		this.url = url;
	}


	@Override
	public final Integer getListeners() {

		return this.listeners;
	}


	@Override
	public final void setStreamable(final Streamable streamable) {

		this.streamable = streamable;
	}


	@Override
	public final Streamable getStreamable() {

		return this.streamable;
	}


	@Override
	public final String getMbid() {

		return this.mbid;
	}


	@Override
	public final Integer getPlayCount() {

		return this.playCount;
	}


	@Override
	public final Integer getRank() {

		return this.rank;
	}


	@Override
	public final void setListeners(final Integer listeners) {

		this.listeners = listeners;
	}


	@Override
	public final void setMbid(final String mbid) {

		this.mbid = mbid;
	}


	@Override
	public final void setPlayCount(final Integer playCount) {

		this.playCount = playCount;
	}


	@Override
	public final void setRank(final Integer rank) {

		this.rank = rank;
	}


	public final List<Album> getAlbums() {

		return this.albums;
	}


	@Override
	public Album getAlbum() {

		return this.album;
	}


	@Override
	public void setAlbum(final Album album) {

		this.album = album;
	}


	public final void setAlbums(final List<Album> albums) {

		this.albums = albums;
	}


	@Override
	public final Artist getArtist() {

		return this.artist;
	}


	@Override
	public final void setArtist(final Artist artist) {

		this.artist = artist;
	}


	public final Float getMatch() {

		return this.match;
	}


	public final void setMatch(final Float match) {

		this.match = match;
	}


	@Override
	public Boolean getNowPlaying() {

		return this.nowPlaying;
	}


	@Override
	public void setNowPlaying(Boolean nowPlaying) {

		this.nowPlaying = nowPlaying;
	}


	@Override
	public final Long getDuration() {

		return this.duration;
	}


	@Override
	public final void setDuration(final Long duration) {

		this.duration = duration;
	}


	public final List<Tag> getTags() {

		return this.tags;
	}


	public final void setTags(final List<Tag> tags) {

		this.tags = tags;
	}


	public final Wiki getWiki() {

		return this.wiki;
	}


	public final void setWiki(final Wiki wiki) {

		this.wiki = wiki;
	}


	@Override
	public final Long getId() {

		return this.id;
	}


	@Override
	public final void setId(final Long id) {

		this.id = id;
	}


	@Override
	public final Integer getUserPlayCount() {

		return this.userPlayCount;
	}


	@Override
	public final void setUserPlayCount(final Integer userPlayCount) {

		this.userPlayCount = userPlayCount;

	}


	// /**
	// * Tag an album using a list of user supplied tags.
	// * @param artist The artist name in question (Required).
	// * @param track The track name in question (Required).
	// * @param tags A comma delimited list of user supplied tags to apply to this track. Accepts a
	// maximum of 10 tags. (Required).
	// * @return
	// */
	// public static AuthenticatedGetQuery<Serializable> addTags(
	// final Object artist,
	// final Object track,
	// final Object tags
	// ) {
	// //TODO: implement
	// if (1 == 1) throw new RuntimeException("Not implemented");
	// AuthenticatedGetQuery<Serializable> query
	// = new AbstractAuthenticatedGetQuery<Serializable>("track.addTags") {
	// @Override
	// public Serializable parse(final Element root) {
	// return null;
	// }
	// };
	// query.addParameter("artist", artist);
	// query.addParameter("track", track);
	// query.addParameter("tags", tags);
	// return query;
	// }
	//
	//
	// /**
	// * Ban a track for a given user profile. This needs to be supplemented with ascrobbling
	// submission containing the 'ban' rating (see theaudioscrobbler API).
	// * @param track A track name (utf8 encoded) (Required).
	// * @param artist An artist name (utf8 encoded) (Required).
	// * @return
	// */
	// public static AuthenticatedGetQuery<Serializable> ban(
	// final Object track,
	// final Object artist
	// ) {
	// //TODO: implement
	// if (1 == 1) throw new RuntimeException("Not implemented");
	// AuthenticatedGetQuery<Serializable> query
	// = new AbstractAuthenticatedGetQuery<Serializable>("track.ban") {
	// @Override
	// public Serializable parse(final Element root) {
	// return null;
	// }
	// };
	// query.addParameter("track", track);
	// query.addParameter("artist", artist);
	// return query;
	// }
	//
	//
	// /**
	// * Get a list of Buy Links for a particular Track. It is required that yousupply either the
	// artist and track params or the mbid param.
	// * @param artist The artist name in question. (Optional).
	// * @param track The track name in question. (Optional).
	// * @param mbid A MusicBrainz id for the album in question. (Optional).
	// * @param country A country name, as defined by the ISO 3166-1 country names standard.
	// (Optional).
	// * @return
	// */
	// public static UnauthenticatedGetQuery<Serializable> getBuylinks(
	// final Object artist,
	// final Object track,
	// final Object mbid,
	// final Object country
	// ) {
	// //TODO: implement
	// if (1 == 1) throw new RuntimeException("Not implemented");
	// UnauthenticatedGetQuery<Serializable> query
	// = new AbstractUnauthenticatedGetQuery<Serializable>("track.getBuylinks") {
	// @Override
	// public Serializable parse(final Element root) {
	// return null;
	// }
	// };
	// query.addParameter("artist", artist);
	// query.addParameter("track", track);
	// query.addParameter("mbid", mbid);
	// query.addParameter("country", country);
	// return query;
	// }
	//
	//
	// /**
	// * Retrieve track metadata associated with a fingerprint id generated by the<a
	// href="http://github.com/lastfm/Fingerprinter">Last.fmFingerprinter</a>. Returns track
	// elements, along with a'rank' value between 0 and 1 reflecting the confidence for eachmatch.
	// See <ahref="http://blog.last.fm/2010/07/09/fingerprint-api-and-app-updated">thisblog post</a>
	// for more info.
	// * @param fingerprintid The fingerprint id to look up (Required).
	// * @return
	// */
	// public static UnauthenticatedGetQuery<Serializable> getFingerprintMetadata(
	// final Object fingerprintid
	// ) {
	// //TODO: implement
	// if (1 == 1) throw new RuntimeException("Not implemented");
	// UnauthenticatedGetQuery<Serializable> query
	// = new AbstractUnauthenticatedGetQuery<Serializable>("track.getFingerprintMetadata") {
	// @Override
	// public Serializable parse(final Element root) {
	// return null;
	// }
	// };
	// query.addParameter("fingerprintid", fingerprintid);
	// return query;
	// }
	//
	//

	/**
	 * Get the metadata for a track on Last.fm using the artist/track name or a musicbrainz id.
	 * TODO: autocorrect is not supported, implement userloved
	 * 
	 * @param artist
	 *            The artist name (Required (unless mbid).
	 * @param track
	 *            The track name (Required (unless mbid).
	 * @param mbid
	 *            The musicbrainz id for the track (Optional).
	 * @param username
	 *            The username for the context of the request (Optional). If supplied, the user's
	 *            playcount for this track and whether they have loved the track is included in the
	 *            response.
	 * @return
	 */
	public static UnauthenticatedGetQuery<Track> getInfo(
			final String artist,
			final String track,
			final String mbid,
			final String username) {

		final UnauthenticatedGetQuery<Track> query = new AbstractUnauthenticatedGetQuery<Track>("track.getInfo") {

			@Override
			public Track parse(final Element root) {

				return new Track(root);
			}
		};

		query.addParameter("artist", artist);
		query.addParameter("track", track);
		query.addParameter("mbid", mbid);
		query.addParameter("username", username);

		return query;
	}


	/**
	 * Get the similar tracks for this track on Last.fm, based on listening data.
	 * 
	 * @param track
	 *            The track name (Optional).
	 * @param artist
	 *            The artist name (Optional).
	 * @param mbid
	 *            The musicbrainz id for the track (Optional).
	 * @param autocorrect
	 *            Transform misspelled artist and track names into correct artist and track names,
	 *            returning the correct version instead (Optional). The corrected artist and track
	 *            name will be returned in the response. TODO: currently the corrected originally
	 *            requested artist/trackname will NOT be returned.
	 * @param limit
	 *            Maximum number of similar tracks to return (Optional).
	 * @return TODO: Using ArrayList to avoid complex generics.
	 */
	public static UnauthenticatedGetQuery<ArrayList<Track>> getSimilar(
			final String track,
			final String artist,
			final String mbid,
			final Boolean autocorrect,
			final Integer limit) {

		final UnauthenticatedGetQuery<ArrayList<Track>> query =
				new AbstractUnauthenticatedGetQuery<ArrayList<Track>>("track.getSimilar") {

					@Override
					public ArrayList<Track> parse(final Element root) {

						final ArrayList<Track> ret = new ArrayList<Track>();
						final NodeList trackEls = root.getElementsByTagName("track");
						for (int i = 0; i < trackEls.getLength(); i++) {
							final Element trackEl = (Element) trackEls.item(i);
							final Track track = new Track(trackEl);
							track.setMatch(DomUtils.toFloat(DomUtils.getChildByTagName(trackEl, "match")));
							track.setDuration(DomUtils.toLong(DomUtils.getChildByTagName(trackEl, "duration")));
							ret.add(track);
						}
						return ret;
					}
				};
		query.addParameter("track", track);
		query.addParameter("artist", artist);
		query.addParameter("mbid", mbid);
		query.addParameter("autocorrect", autocorrect);
		query.addParameter("limit", limit);
		return query;
	}


	// /**
	// * Get the tags applied by an individual user to a track on Last.fm.
	// * @param artist The artist name in question (Required).
	// * @param track The track name in question (Required).
	// * @return
	// */
	// public static AuthenticatedGetQuery<Serializable> getTags(
	// final Object artist,
	// final Object track
	// ) {
	// //TODO: implement
	// if (1 == 1) throw new RuntimeException("Not implemented");
	// AuthenticatedGetQuery<Serializable> query
	// = new AbstractAuthenticatedGetQuery<Serializable>("track.getTags") {
	// @Override
	// public Serializable parse(final Element root) {
	// return null;
	// }
	// };
	// query.addParameter("artist", artist);
	// query.addParameter("track", track);
	// return query;
	// }
	//
	//
	// /**
	// * Get the top fans for this track on Last.fm, based on listening data. Supplyeither track &
	// artist name or musicbrainz id.
	// * @param track The track name in question (Optional).
	// * @param artist The artist name in question (Optional).
	// * @param mbid The musicbrainz id for the track (Optional).
	// * @return
	// */
	// public static UnauthenticatedGetQuery<Serializable> getTopFans(
	// final Object track,
	// final Object artist,
	// final Object mbid
	// ) {
	// //TODO: implement
	// if (1 == 1) throw new RuntimeException("Not implemented");
	// UnauthenticatedGetQuery<Serializable> query
	// = new AbstractUnauthenticatedGetQuery<Serializable>("track.getTopFans") {
	// @Override
	// public Serializable parse(final Element root) {
	// return null;
	// }
	// };
	// query.addParameter("track", track);
	// query.addParameter("artist", artist);
	// query.addParameter("mbid", mbid);
	// return query;
	// }
	//
	//
	// /**
	// * Get the top tags for this track on Last.fm, ordered by tag count. Supply either track &
	// * artist name or mbid.
	// * TODO: using ArrayList to prevent complex generics
	// * @param track The track name in question (Optional).
	// * @param artist The artist name in question (Optional).
	// * @param mbid The musicbrainz id for the track (Optional).
	// * @return
	// */
	// public static UnauthenticatedGetQuery<ArrayList<Tag>> getTopTags(
	// final String track,
	// final String artist,
	// final String mbid
	// ) {
	// UnauthenticatedGetQuery<ArrayList<Tag>> query
	// = new AbstractUnauthenticatedGetQuery<ArrayList<Tag>>("track.getTopTags") {
	// @SuppressWarnings("unchecked")
	// @Override
	// public ArrayList<Tag> parse(final Element root) {
	// ArrayList<Tag> ret = new ArrayList<Tag>();
	// for (Element tagElement : (List<Element>) root.elements("tag")) {
	// ret.add(new Tag(tagElement));
	// }
	// return ret;
	// }
	// };
	// query.addParameter("track", track);
	// query.addParameter("artist", artist);
	// query.addParameter("mbid", mbid);
	// return query;
	// }
	//
	//
	// /**
	// * Love a track for a user profile. This needs to be supplemented with ascrobbling submission
	// containing the 'love' rating (see theaudioscrobbler API).
	// * @param track A track name (utf8 encoded) (Required).
	// * @param artist An artist name (utf8 encoded) (Required).
	// * @return
	// */
	// public static AuthenticatedGetQuery<Serializable> love(
	// final Object track,
	// final Object artist
	// ) {
	// //TODO: implement
	// if (1 == 1) throw new RuntimeException("Not implemented");
	// AuthenticatedGetQuery<Serializable> query
	// = new AbstractAuthenticatedGetQuery<Serializable>("track.love") {
	// @Override
	// public Serializable parse(final Element root) {
	// return null;
	// }
	// };
	// query.addParameter("track", track);
	// query.addParameter("artist", artist);
	// return query;
	// }
	//
	//
	// /**
	// * Remove a user's tag from a track.
	// * @param artist The artist name in question (Required).
	// * @param track The track name in question (Required).
	// * @param tag A single user tag to remove from this track. (Required).
	// * @return
	// */
	// public static AuthenticatedGetQuery<Serializable> removeTag(
	// final Object artist,
	// final Object track,
	// final Object tag
	// ) {
	// //TODO: implement
	// if (1 == 1) throw new RuntimeException("Not implemented");
	// AuthenticatedGetQuery<Serializable> query
	// = new AbstractAuthenticatedGetQuery<Serializable>("track.removeTag") {
	// @Override
	// public Serializable parse(final Element root) {
	// return null;
	// }
	// };
	// query.addParameter("artist", artist);
	// query.addParameter("track", track);
	// query.addParameter("tag", tag);
	// return query;
	// }
	//
	//
	// /**
	// * Search for a track by track name. Returns track matches sorted byrelevance.
	// * @param limit Limit the number of tracks returned at one time. Default (maximum) is 30.
	// (Optional).
	// * @param page Scan into the results by specifying a page number. Defaults to first page.
	// (Optional).
	// * @param track The track name in question. (Required).
	// * @param artist Narrow your search by specifying an artist. (Optional).
	// * @return
	// */
	// public static UnauthenticatedGetQuery<Serializable> search(
	// final Object limit,
	// final Object page,
	// final Object track,
	// final Object artist
	// ) {
	// //TODO: implement
	// if (1 == 1) throw new RuntimeException("Not implemented");
	// UnauthenticatedGetQuery<Serializable> query
	// = new AbstractUnauthenticatedGetQuery<Serializable>("track.search") {
	// @Override
	// public Serializable parse(final Element root) {
	// return null;
	// }
	// };
	// query.addParameter("limit", limit);
	// query.addParameter("page", page);
	// query.addParameter("track", track);
	// query.addParameter("artist", artist);
	// return query;
	// }
	//
	//
	// /**
	// * Share a track twith one or more Last.fm users or other friends.
	// * @param artist An artist name. (Required).
	// * @param track A track name. (Required).
	// * @param publicViewable A Last.fm API key. (Optional): Optionally show in the sharing users
	// activity feed. Defaults to 0 (false).<br /> <span class="param">message</span> (Optional): An
	// optional message to send with the recommendation. If not supplied a default message will be
	// used.<br /> <span class="param">recipient</span> (Required): Email Address | Last.fm Username
	// - A comma delimited list of email addresses or Last.fm usernames. Maximum is 10.<br /> <span
	// class="param">api_key</span> (Required).
	// * @return
	// */
	// public static AuthenticatedGetQuery<Serializable> share(
	// final Object artist,
	// final Object track,
	// final Object publicViewable
	// ) {
	// //TODO: implement
	// if (1 == 1) throw new RuntimeException("Not implemented");
	// AuthenticatedGetQuery<Serializable> query
	// = new AbstractAuthenticatedGetQuery<Serializable>("track.share") {
	// @Override
	// public Serializable parse(final Element root) {
	// return null;
	// }
	// };
	// query.addParameter("artist", artist);
	// query.addParameter("track", track);
	// query.addParameter("public", publicViewable);
	// return query;
	// }

	/**
	 * Used to add a track-play to a user's profile.
	 * 
	 * @param artist
	 *            The artist name (Required).
	 * @param track
	 *            The track name (Required).
	 * @param startTime
	 *            The time the track started playing in UNIX timestamp format in milliseconds
	 *            (Required).
	 * @param album
	 *            The album name (Optional).
	 * @param trackNumber
	 *            The track number of the track on the album (Optional).
	 * @param mbid
	 *            The MusicBrainz Track ID (Optional).
	 * @param albumArtist
	 *            The album artist - if this differs from the track artist (Optional).
	 * @param duration
	 *            The length of the track in milliseconds (Optional).
	 * @param sessionKey
	 *            A session key generated by authenticating a user via the authentication protocol.
	 */
	public static PostQuery<Scrobble> scrobble(
			String artist,
			String track,
			Long startTime,
			String album,
			Integer trackNumber,
			String mbid,
			String albumArtist,
			Long duration,
			String sessionKey) {

		// TODO This currently does not support batch scrobbling and some of the optional
		// parameters.
		AbstractPostQuery<Scrobble> query = new AbstractPostQuery<Scrobble>("track.scrobble") {

			@Override
			public Scrobble parse(Element root) {

				List<Element> scrobbles = DomUtils.getChildrenByTagName(root, "scrobble");
				if (scrobbles.size() != 1) {
					throw new RuntimeException("Unexpected number of results: " + scrobbles.size());
				}

				Element scrobbleElement = scrobbles.iterator().next();
				return new Scrobble(scrobbleElement);
			}
		};

		query.addParameter("artist[0]", artist);
		query.addParameter("track[0]", track);
		query.addParameter("timestamp[0]", startTime / 1000L);
		query.addParameter("album[0]", album);
		query.addParameter("trackNumber[0]", trackNumber);
		query.addParameter("mbid[0]", mbid);
		query.addParameter("albumArtist[0]", albumArtist);
		if (duration != null) {
			query.addParameter("duration[0]", duration / 1000L);
		}
		query.addParameter("sk", sessionKey);

		return query;
	}
}
