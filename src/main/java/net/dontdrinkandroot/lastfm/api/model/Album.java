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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.dontdrinkandroot.lastfm.api.model.entitytypes.ArtistEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.IdEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.ImageEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.MusicBrainzEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.NameEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.PlayCountEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.ReleaseDateEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.StreamableEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.TagsEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.TracksEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.UrlEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.UserPlayCountEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.WikiEntity;
import net.dontdrinkandroot.lastfm.api.model.paginatedresult.GenericPaginatedResult;
import net.dontdrinkandroot.lastfm.api.model.paginatedresult.PaginatedResult;
import net.dontdrinkandroot.lastfm.api.queries.AbstractPostQuery;
import net.dontdrinkandroot.lastfm.api.queries.AbstractUnauthenticatedGetQuery;
import net.dontdrinkandroot.lastfm.api.queries.NoResponsePostQuery;
import net.dontdrinkandroot.lastfm.api.queries.PostQuery;
import net.dontdrinkandroot.lastfm.api.queries.UnauthenticatedGetQuery;
import net.dontdrinkandroot.lastfm.api.xml.DomUtils;
import net.dontdrinkandroot.utils.ISO_3166_1_alpha2;
import net.dontdrinkandroot.utils.lang.StringUtils;

import org.w3c.dom.Element;


public class Album extends LfmEntity
		implements ImageEntity, MusicBrainzEntity, NameEntity, UrlEntity, PlayCountEntity, IdEntity,
		UserPlayCountEntity, ArtistEntity, ReleaseDateEntity, WikiEntity, TagsEntity, TracksEntity, StreamableEntity {

	private static final long serialVersionUID = 1L;

	public static final transient Map<ImageSize, URL> DEFAULT_IMAGES = new HashMap<ImageSize, URL>();

	/* Initialize default images */
	static {
		try {
			Album.DEFAULT_IMAGES.put(ImageSize.MEGA, new URL(
					"http://cdn.last.fm/flatness/catalogue/noimage/2/default_album_mega.png"));
			Album.DEFAULT_IMAGES.put(ImageSize.LARGE, new URL(
					"http://cdn.last.fm/flatness/catalogue/noimage/2/default_album_large.png"));
			Album.DEFAULT_IMAGES.put(ImageSize.MEDIUM, new URL(
					"http://cdn.last.fm/flatness/catalogue/noimage/2/default_album_medium.png"));
			Album.DEFAULT_IMAGES.put(ImageSize.SMALL, new URL(
					"http://cdn.last.fm/flatness/catalogue/noimage/2/default_album_small.png"));
		} catch (final MalformedURLException e) {
			/* Shouldn't happen */
			e.printStackTrace();
		}
	}

	private String name;

	private Artist artist;

	private Long id;

	private String mbid;

	private URL url;

	private Date releaseDate;

	private Map<ImageSize, URL> images;

	private Integer listeners;

	private Integer playCount;

	private List<Track> tracks;

	private List<Tag> tags;

	private Wiki wiki;

	private Integer rank;

	private Integer userPlayCount;

	private Streamable streamable;


	/**
	 * Create an Album from a dom4j Element.
	 * 
	 * @param albumElement
	 *            The Element to convert.
	 */
	public Album(final Element albumElement) {

		super(albumElement);
	}


	@Override
	public final String getName() {

		return this.name;
	}


	@Override
	public final void setName(final String name) {

		this.name = name;
	}


	@Override
	public final Artist getArtist() {

		return this.artist;
	}


	@Override
	public final void setArtist(final Artist artist) {

		this.artist = artist;
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
	public final String getMbid() {

		return this.mbid;
	}


	@Override
	public final void setMbid(final String mbid) {

		this.mbid = mbid;
	}


	@Override
	public final URL getUrl() {

		return this.url;
	}


	@Override
	public final void setUrl(final URL url) {

		this.url = url;
	}


	@Override
	public final Date getReleaseDate() {

		return this.releaseDate;
	}


	@Override
	public final void setReleaseDate(final Date releaseDate) {

		this.releaseDate = releaseDate;
	}


	@Override
	public final Map<ImageSize, URL> getImages() {

		return this.images;
	}


	@Override
	public final void setImages(final Map<ImageSize, URL> images) {

		this.images = images;
	}


	@Override
	public final Integer getUserPlayCount() {

		return this.userPlayCount;
	}


	@Override
	public final void setUserPlayCount(final Integer userPlayCount) {

		this.userPlayCount = userPlayCount;
	}


	@Override
	public final Integer getListeners() {

		return this.listeners;
	}


	@Override
	public final void setListeners(final Integer listeners) {

		this.listeners = listeners;
	}


	@Override
	public final Integer getPlayCount() {

		return this.playCount;
	}


	@Override
	public final void setPlayCount(final Integer playCount) {

		this.playCount = playCount;
	}


	@Override
	public final List<Track> getTracks() {

		return this.tracks;
	}


	@Override
	public final void setTracks(final List<Track> tracks) {

		this.tracks = tracks;
	}


	@Override
	public final List<Tag> getTags() {

		return this.tags;
	}


	@Override
	public final void setTags(final List<Tag> tags) {

		this.tags = tags;
	}


	@Override
	public final Wiki getWiki() {

		return this.wiki;
	}


	@Override
	public final void setWiki(final Wiki wiki) {

		this.wiki = wiki;
	}


	@Override
	public final Integer getRank() {

		return this.rank;
	}


	@Override
	public final void setRank(final Integer rank) {

		this.rank = rank;
	}


	@Override
	public void setStreamable(final Streamable streamable) {

		this.streamable = streamable;
	}


	@Override
	public Streamable getStreamable() {

		return this.streamable;
	}


	/*
	 * HashCode and Equals based on the assumption that the name is the unique identifier
	 */

	@Override
	public final int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result;
		if (this.name != null) {
			result += this.name.hashCode();
		}
		return result;
	}


	@Override
	public final boolean equals(final Object obj) {

		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final Album other = (Album) obj;
		if (this.name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!this.name.equals(other.name)) {
			return false;
		}
		return true;
	}


	/**
	 * Tag an album using a list of user supplied tags.
	 * 
	 * @param artist
	 *            The artist name in question (Required).
	 * @param album
	 *            The album name in question (Required).
	 * @param tags
	 *            A list of user supplied tags to apply to this album. Accepts a maximum of 10 tags
	 *            (Required).
	 * @param sk
	 *            A session key generated by authenticating a user via the authentication protocol
	 *            (Required).
	 * @return
	 */
	public static PostQuery<Boolean> addTags(
			final String artist,
			final String album,
			final List<String> tags,
			final String sk) {

		final PostQuery<Boolean> query = new AbstractPostQuery<Boolean>("album.addTags") {

			@Override
			public Boolean parse(final Element root) {

				return true;
			}
		};

		query.addParameter("artist", artist);
		query.addParameter("album", album);
		if (tags != null) {
			query.addParameter("tags", StringUtils.join(tags, ","));
		}
		query.addParameter("sk", sk);

		return query;

	}


	/**
	 * Get a list of Buy Links for a particular Album. It is required that you supply either the
	 * artist and track params or the mbid param.
	 * 
	 * @param artist
	 *            The artist name in question (Required unless mbid).
	 * @param album
	 *            The album in question (Required unless mbid).
	 * @param mbid
	 *            A MusicBrainz id for the album in question (Optional).
	 * @param country
	 *            A country name, as defined by the ISO 3166-1 country names standard (Optional).
	 * @return
	 */
	public static UnauthenticatedGetQuery<Affiliations> getBuylinks(
			final String artist,
			final String album,
			final String mbid,
			final ISO_3166_1_alpha2 country) {

		final UnauthenticatedGetQuery<Affiliations> query =
				new AbstractUnauthenticatedGetQuery<Affiliations>("album.getBuylinks") {

					@Override
					public Affiliations parse(final Element root) {

						return new Affiliations(root);
					}
				};

		query.addParameter("artist", artist);
		query.addParameter("album", album);
		query.addParameter("mbid", mbid);

		/* Last.fm expects the country name instead of the two letter code */
		if (country != null) {
			query.addParameter("country", country.getName());
		}

		return query;
	}


	/**
	 * Get the metadata for an album on Last.fm using the album name or a musicbrainz id. See
	 * {@link Playlist#fetch} on how to get the album playlist.
	 * 
	 * @param artist
	 *            The artist name in question (Optional).
	 * @param album
	 *            The album name in question (Optional).
	 * @param mbid
	 *            The musicbrainz id for the album (Optional).
	 * @param username
	 *            The username for the context of the request. If supplied, the user's playcount for
	 *            this album is included in the response. (Optional).
	 * @param lang
	 *            The language to return the biography in, expressed as an ISO 639 alpha-2 code.
	 *            (Optional).
	 * @return
	 */
	public static UnauthenticatedGetQuery<Album> getInfo(
			final String artist,
			final String album,
			final String mbid,
			final String username,
			final ISO_3166_1_alpha2 lang) {

		final UnauthenticatedGetQuery<Album> query = new AbstractUnauthenticatedGetQuery<Album>("album.getInfo") {

			@Override
			public Album parse(final Element root) {

				final Album album = new Album(root);

				return album;
			}

		};

		query.addParameter("artist", artist);
		query.addParameter("album", album);
		query.addParameter("mbid", mbid);
		query.addParameter("username", username);
		if (lang != null) {
			query.addParameter("lang", lang.toString().toLowerCase());
		}

		return query;

	}


	/**
	 * Get shouts for this album.
	 * 
	 * @param artist
	 *            The artist name (Required unless mbid).
	 * @param album
	 *            The album title (Required unless mbid).
	 * @param mbid
	 *            The musicbrainz id for the artist (Optional).
	 * @param limit
	 *            The number of results to fetch per page (Optional). Defaults to 50.
	 * @param page
	 *            The page number to fetch (Optional). Defaults to first page.
	 * @return
	 */
	public static UnauthenticatedGetQuery<PaginatedResult<List<Shout>>> getShouts(
			final String artist,
			final String album,
			final String mbid,
			final Integer limit,
			final Integer page) {

		final UnauthenticatedGetQuery<PaginatedResult<List<Shout>>> query =
				new AbstractUnauthenticatedGetQuery<PaginatedResult<List<Shout>>>("album.getShouts") {

					@Override
					public PaginatedResult<List<Shout>> parse(final Element root) {

						final GenericPaginatedResult<List<Shout>> result =
								new GenericPaginatedResult<List<Shout>>(root);
						final List<Element> shoutElements =
								net.dontdrinkandroot.utils.xml.DomUtils.getChildrenByTagName(root, "shout");
						final List<Shout> shouts = new ArrayList<Shout>();
						for (final Element shoutElement : shoutElements) {
							shouts.add(new Shout(shoutElement));
						}
						result.setEntries(shouts);

						return result;
					}
				};

		query.addParameter("artist", artist);
		query.addParameter("album", album);
		query.addParameter("mbid", mbid);
		query.addParameter("limit", limit);
		query.addParameter("page", page);

		return query;
	}


	// TODO Param "autocorrect" is not supported.
	// TODO Using ArrayList to avoid complex generics.
	/**
	 * Get the tags applied by an individual user to an album on Last.fm.
	 * 
	 * @param artist
	 *            The artist name in question (Required).
	 * @param album
	 *            The album name in question (Required).
	 * @param mbid
	 *            The musicbrainz id for the album (Optional).
	 * @param user
	 *            The user to look up (Required).
	 * @return
	 */
	public static UnauthenticatedGetQuery<ArrayList<Tag>> getTags(
			final String artist,
			final String album,
			final String mbid,
			final String user) {

		final UnauthenticatedGetQuery<ArrayList<Tag>> query =
				new AbstractUnauthenticatedGetQuery<ArrayList<Tag>>("album.getTags") {

					@Override
					public ArrayList<Tag> parse(final Element root) {

						final ArrayList<Tag> result = new ArrayList<Tag>();
						final List<Element> tagElements = DomUtils.getChildrenByTagName(root, "tag");
						for (final Element tagElement : tagElements) {
							result.add(new Tag(tagElement));
						}

						return result;
					}
				};

		query.addParameter("artist", artist);
		query.addParameter("album", album);
		query.addParameter("mbid", mbid);
		query.addParameter("user", user);

		return query;
	}


	/**
	 * Get the top tags for an album on Last.fm, ordered by popularity.
	 * 
	 * @param artist
	 *            The artist name in question (Required).
	 * @param album
	 *            The album name in question (Required).
	 * @param mbid
	 *            The musicbrainz id for the album (Optional).
	 * @return
	 */
	public static UnauthenticatedGetQuery<ArrayList<Tag>> getTopTags(
			final String artist,
			final String album,
			final String mbid) {

		final UnauthenticatedGetQuery<ArrayList<Tag>> query =
				new AbstractUnauthenticatedGetQuery<ArrayList<Tag>>("album.getTopTags") {

					@Override
					public ArrayList<Tag> parse(final Element root) {

						final ArrayList<Tag> tags = new ArrayList<Tag>();
						final List<Element> tagElements = DomUtils.getChildrenByTagName(root, "tag");
						for (final Element tagElement : tagElements) {
							tags.add(new Tag(tagElement));
						}

						return tags;
					}
				};

		query.addParameter("artist", artist);
		query.addParameter("album", album);
		query.addParameter("mbid", mbid);

		return query;
	}


	/**
	 * Remove a user's tag from an album.
	 * 
	 * @param artist
	 *            The artist name in question (Required).
	 * @param album
	 *            The album name in question (Required).
	 * @param tag
	 *            A single user tag to remove from this album. (Required).
	 * @param sk
	 *            A session key generated by authenticating a user via the authentication protocol.
	 *            (Required).
	 * @return
	 */
	public static PostQuery<Boolean> removeTag(
			final String artist,
			final String album,
			final String tag,
			final String sk) {

		final PostQuery<Boolean> query = new AbstractPostQuery<Boolean>("album.removeTag") {

			@Override
			public Boolean parse(final Element root) {

				return true;
			}
		};

		query.addParameter("artist", artist);
		query.addParameter("album", album);
		query.addParameter("tag", tag);
		query.addParameter("sk", sk);

		return query;
	}


	/**
	 * Search for an album by name. Returns album matches sorted by relevance.
	 * 
	 * @param album
	 *            The album name (Required).
	 * @param limit
	 *            The number of results to fetch per page (Optional). Defaults to 30.
	 * @param page
	 *            The page number to fetch (Optional). Defaults to first page.
	 * @return
	 */
	public static UnauthenticatedGetQuery<PaginatedResult<List<Album>>> search(
			final String album,
			final Integer limit,
			final Integer page) {

		final UnauthenticatedGetQuery<PaginatedResult<List<Album>>> query =
				new AbstractUnauthenticatedGetQuery<PaginatedResult<List<Album>>>("album.search") {

					@Override
					public PaginatedResult<List<Album>> parse(final Element root) {

						final GenericPaginatedResult<List<Album>> result =
								new GenericPaginatedResult<List<Album>>(root);
						final Element albumMatchesElement =
								net.dontdrinkandroot.utils.xml.DomUtils.getChildByTagName(root, "albummatches");
						if (albumMatchesElement != null) {
							final List<Element> albumElements =
									net.dontdrinkandroot.utils.xml.DomUtils.getChildrenByTagName(
											albumMatchesElement,
											"album");
							final List<Album> albums = new ArrayList<Album>();
							for (final Element albumElement : albumElements) {
								albums.add(new Album(albumElement));
							}
							result.setEntries(albums);
						}

						return result;
					}
				};

		query.addParameter("album", album);
		query.addParameter("limit", limit);
		query.addParameter("page", page);

		return query;
	}


	/**
	 * Share an album with one or more Last.fm users or other friends.
	 * 
	 * @param artist
	 *            An artist name (Required).
	 * @param album
	 *            An album name (Required).
	 * @param recipient
	 *            Email Address | Last.fm Username - A comma delimited list of email addresses or
	 *            Last.fm usernames (Required). Maximum is 10.
	 * @param publicViewable
	 *            Optionally show in the sharing users activity feed. Defaults to 0 (false)
	 *            (Optional).
	 * @param message
	 *            An optional message to send with the recommendation (Optional). If not supplied a
	 *            default message will be used.
	 * 
	 * @return
	 */
	public static PostQuery<Boolean> share(
			final String artist,
			final String album,
			final List<String> recipient,
			final Boolean publicViewable,
			final String message,
			final String sk) {

		final PostQuery<Boolean> query = new NoResponsePostQuery("album.share");

		query.addParameter("artist", artist);
		query.addParameter("album", album);
		if (recipient != null) {
			query.addParameter("recipient", StringUtils.join(recipient, ","));
		}
		query.addParameter("message", message);
		if (publicViewable != null && publicViewable.booleanValue()) {
			query.addParameter("public", "1");
		}

		return query;
	}
}
