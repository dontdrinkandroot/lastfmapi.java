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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.dontdrinkandroot.lastfm.api.model.entitytypes.ImageEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.MatchEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.MusicBrainzEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.NameEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.PlayCountEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.StreamableEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.TagsEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.UrlEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.UserPlayCountEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.WikiEntity;
import net.dontdrinkandroot.lastfm.api.model.paginatedresult.GenericPaginatedResult;
import net.dontdrinkandroot.lastfm.api.model.paginatedresult.PaginatedResult;
import net.dontdrinkandroot.lastfm.api.queries.AbstractPostQuery;
import net.dontdrinkandroot.lastfm.api.queries.AbstractUnauthenticatedGetQuery;
import net.dontdrinkandroot.lastfm.api.queries.PostQuery;
import net.dontdrinkandroot.lastfm.api.queries.UnauthenticatedGetQuery;
import net.dontdrinkandroot.lastfm.api.xml.DomUtils;
import net.dontdrinkandroot.utils.ISO_3166_1_alpha2;
import net.dontdrinkandroot.utils.lang.StringUtils;

import org.w3c.dom.Element;


public class Artist extends LfmEntity
		implements MusicBrainzEntity, ImageEntity, NameEntity, UrlEntity, PlayCountEntity, StreamableEntity,
		UserPlayCountEntity, WikiEntity, TagsEntity, MatchEntity {

	private static final long serialVersionUID = 1L;

	public static final transient Map<ImageSize, URL> DEFAULT_IMAGES = new HashMap<ImageSize, URL>();

	/* Initialize default images */
	static {
		try {
			Artist.DEFAULT_IMAGES.put(ImageSize.MEGA, new URL(
					"http://cdn.last.fm/flatness/catalogue/noimage/2/default_artist_mega.png"));
			Artist.DEFAULT_IMAGES.put(ImageSize.LARGE, new URL(
					"http://cdn.last.fm/flatness/catalogue/noimage/2/default_artist_large.png"));
			Artist.DEFAULT_IMAGES.put(ImageSize.MEDIUM, new URL(
					"http://cdn.last.fm/flatness/catalogue/noimage/2/default_artist_medium.png"));
			Artist.DEFAULT_IMAGES.put(ImageSize.SMALL, new URL(
					"http://cdn.last.fm/flatness/catalogue/noimage/2/default_artist_small.png"));
		} catch (final MalformedURLException e) {
			/* Shouldn't happen */
			e.printStackTrace();
		}
	}

	private Integer rank;

	private String name;

	private String mbid;

	private URL url;

	private Map<ImageSize, URL> images;

	private Streamable streamable;

	private Integer listeners;

	private Integer playCount;

	private Integer tagCount;

	private List<Artist> similar;

	private List<Tag> tags;

	private Wiki wiki;

	private Float match;

	private Integer userPlayCount;


	/**
	 * Create an Artist from an xml element.
	 * 
	 * @param element
	 *            The xml element to convert.
	 */
	public Artist(final Element element) {

		super(element);
	}


	public Artist(final String name) {

		this.name = name;
	}


	@Override
	public final Float getMatch() {

		return this.match;
	}


	@Override
	public final void setMatch(final Float match) {

		this.match = match;
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
	public final String getMbid() {

		return this.mbid;
	}


	@Override
	public final void setMbid(final String mbid) {

		this.mbid = mbid;
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
	public final URL getUrl() {

		return this.url;
	}


	@Override
	public final void setUrl(final URL url) {

		this.url = url;
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
	public final Streamable getStreamable() {

		return this.streamable;
	}


	@Override
	public final void setStreamable(final Streamable streamable) {

		this.streamable = streamable;
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


	public final void setTagCount(final Integer tagCount) {

		this.tagCount = tagCount;
	}


	public final Integer getTagCount() {

		return this.tagCount;
	}


	public final List<Artist> getSimilar() {

		return this.similar;
	}


	public final void setSimilar(final List<Artist> similar) {

		this.similar = similar;
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
	public Integer getUserPlayCount() {

		return this.userPlayCount;
	}


	@Override
	public void setUserPlayCount(final Integer userPlayCount) {

		this.userPlayCount = userPlayCount;
	}


	/*
	 * HashCode and Equals based on the assumption that the name is the unique identifier.
	 */
	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + (this.name == null ? 0 : this.name.hashCode());

		return result;
	}


	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (this.getClass() != obj.getClass()) {
			return false;
		}

		Artist other = (Artist) obj;
		if (this.name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!this.name.equals(other.name)) {
			return false;
		}

		return true;
	}


	@Override
	public final String toString() {

		return this.name;
	}


	/**
	 * Tag an artist with one or more user supplied tags.
	 * 
	 * @param artist
	 *            The artist name in question (Required).
	 * @param tags
	 *            A comma delimited list of user supplied tags to apply to this artist (Required).
	 *            Accepts a maximum of 10 tags.
	 */
	public static PostQuery<Boolean> addTags(final String artist, final List<String> tags, final String sk) {

		final PostQuery<Boolean> query = new AbstractPostQuery<Boolean>("artist.addTags") {

			@Override
			public Boolean parse(final Element root) {

				/* This always returns true as errors are denoted by a LastfmWebservicesException */
				return true;
			}
		};

		query.addParameter("artist", artist);
		if (tags != null) {
			query.addParameter("tags", StringUtils.join(tags, ","));
		}
		query.addParameter("sk", sk);

		return query;

	}


	/**
	 * Use the last.fm corrections data to check whether the supplied artist has a correction to a
	 * canonical artist
	 * 
	 * @param artistName
	 *            The artist name to correct (Required).
	 */
	public static UnauthenticatedGetQuery<ArrayList<Artist>> getCorrection(final String artistName) {

		final UnauthenticatedGetQuery<ArrayList<Artist>> query =
				new AbstractUnauthenticatedGetQuery<ArrayList<Artist>>("artist.getCorrection") {

					@Override
					public ArrayList<Artist> parse(final Element root) {

						final ArrayList<Artist> artists = new ArrayList<Artist>();
						final List<Element> correctionElements = DomUtils.getChildrenByTagName(root, "correction");
						for (final Element correctionElement : correctionElements) {
							final Element artistElement = DomUtils.getChildByTagName(correctionElement, "artist");
							artists.add(new Artist(artistElement));
						}

						return artists;
					}
				};

		query.addParameter("artist", artistName);

		return query;
	}


	/**
	 * Get a list of upcoming events for this artist.
	 * 
	 * @param artist
	 *            The artist name (Required unless mbid).
	 * @param mbid
	 *            The musicbrainz id for the artist (Optional).
	 * @param festivalsOnly
	 *            Whether only festivals should be returned, or all events (Optional).
	 * @param limit
	 *            The number of results to fetch per page (Optional). Defaults to 50.
	 * @param page
	 *            The page number to fetch (Optional). Defaults to first page.
	 * @return
	 */
	public static UnauthenticatedGetQuery<PaginatedResult<List<Event>>> getEvents(
			final String artist,
			final String mbid,
			final Boolean festivalsOnly,
			final Integer limit,
			final Integer page) {

		final UnauthenticatedGetQuery<PaginatedResult<List<Event>>> query =
				new AbstractUnauthenticatedGetQuery<PaginatedResult<List<Event>>>("artist.getEvents") {

					@Override
					public PaginatedResult<List<Event>> parse(final Element root) {

						final GenericPaginatedResult<List<Event>> result =
								new GenericPaginatedResult<List<Event>>(root);
						final List<Element> eventElements =
								net.dontdrinkandroot.utils.xml.DomUtils.getChildrenByTagName(root, "event");
						final List<Event> events = new ArrayList<Event>();
						for (final Element eventElement : eventElements) {
							events.add(new Event(eventElement));
						}
						result.setEntries(events);

						return result;
					}
				};

		query.addParameter("artist", artist);
		query.addParameter("mbid", mbid);
		query.addParameter("festivalsonly", festivalsOnly);
		query.addParameter("limit", limit);
		query.addParameter("page", page);

		return query;
	}


	/**
	 * Get Images for this artist in a variety of sizes.
	 * 
	 * @param artist
	 *            The artist name in question (Required unless mbid).
	 * @param mbid
	 *            The musicbrainz id for the artist (Optional).
	 * @param order
	 *            Sort ordering can be either 'popularity' (default) or 'dateadded' (Optional).
	 *            While ordering by popularity officially selected images by labels and artists will
	 *            be ordered first. This is ignored and set to 'dateadded' when requested as rss.
	 * @param limit
	 *            The number of results to fetch per page (Optional). Defaults to 50.
	 * @param page
	 *            The page number to fetch (Optional). Defaults to first page.
	 * 
	 * @return
	 */
	public static UnauthenticatedGetQuery<PaginatedResult<List<Image>>> getImages(
			final String artist,
			final String mbid,
			final String order,
			final Integer limit,
			final Integer page) {

		final UnauthenticatedGetQuery<PaginatedResult<List<Image>>> query =
				new AbstractUnauthenticatedGetQuery<PaginatedResult<List<Image>>>("artist.getImages") {

					@Override
					public PaginatedResult<List<Image>> parse(final Element root) {

						final GenericPaginatedResult<List<Image>> result =
								new GenericPaginatedResult<List<Image>>(root);
						final List<Element> imageElements =
								net.dontdrinkandroot.utils.xml.DomUtils.getChildrenByTagName(root, "image");
						final List<Image> images = new ArrayList<Image>();
						for (final Element imageElement : imageElements) {
							images.add(new Image(imageElement));
						}
						result.setEntries(images);
						return result;
					}
				};

		query.addParameter("artist", artist);
		query.addParameter("page", page);
		query.addParameter("limit", limit);
		query.addParameter("order", order);

		return query;
	}


	/**
	 * Get the metadata for an artist on Last.fm. Includes biography.
	 * 
	 * @param artist
	 *            The artist name in question (Optional).
	 * @param mbid
	 *            The musicbrainz id for the artist (Optional).
	 * @param username
	 *            The username for the context of the request (Optional). If supplied, the user's
	 *            playcount for this artist is included in the response.
	 * @param lang
	 *            The language to return the biography in, expressed as an ISO 639 alpha-2 code
	 *            (Optional).
	 * @return
	 */
	public static UnauthenticatedGetQuery<Artist> getInfo(
			final String artist,
			final String mbid,
			final String username,
			final ISO_3166_1_alpha2 lang) {

		final UnauthenticatedGetQuery<Artist> query = new AbstractUnauthenticatedGetQuery<Artist>("artist.getInfo") {

			@Override
			public Artist parse(final Element root) {

				/* Parse artist */
				final Artist artist = new Artist(root);

				/* Parse similar artists */
				final List<Artist> similarArtists = new ArrayList<Artist>();
				final Element similarEl = DomUtils.getChildByTagName(root, "similar");
				if (similarEl != null) {
					final List<Element> similarArtistElements = DomUtils.getChildrenByTagName(similarEl, "artist");
					for (final Element similarArtistElement : similarArtistElements) {
						similarArtists.add(new Artist(similarArtistElement));
					}
				}
				artist.setSimilar(similarArtists);

				return artist;
			}
		};
		query.addParameter("artist", artist);
		query.addParameter("mbid", mbid);
		query.addParameter("username", username);
		if (lang != null) {
			query.addParameter("lang", lang.toString().toLowerCase());
		}
		return query;
	}


	// /**
	// * Get a paginated list of all the events this artist has played at in the past.
	// *
	// * @param artistName
	// * The name of the artist you would like to fetch event listings for (Required unless
	// * mbid).
	// * @param mbid
	// * The musicbrainz id for the artist (Required unless artistName).
	// * @param page
	// * The page of results to return (Optional).
	// * @param limit
	// * The number of results to fetch per page (Optional). Defaults to 50.
	// */
	// public static UnauthenticatedGetQuery<PaginatedResult<List<Event>>> getPastEvents(
	// final String artistName,
	// final String mbid,
	// final Integer page,
	// final Integer limit) {
	//
	// // TODO: autocorrect not supported
	//
	// final UnauthenticatedGetQuery<PaginatedResult<List<Event>>> query =
	// new AbstractUnauthenticatedGetQuery<PaginatedResult<List<Event>>>("artist.getPastEvents") {
	//
	// @Override
	// public PaginatedResult<List<Event>> parse(final Element root) {
	//
	// return new EventsPaginatedResult(root);
	// }
	// };
	//
	// query.addParameter("artist", artistName);
	// query.addParameter("mbid", mbid);
	// query.addParameter("page", page);
	// query.addParameter("limit", limit);
	//
	// return query;
	// }

	/**
	 * Get a podcast of free mp3s based on an artist
	 * 
	 * @param artist
	 *            The artist name in question (Required).
	 * @return
	 */
	// public static UnauthenticatedGetQuery<Serializable> getPodcast(
	// final Object artist
	// ) {
	// //TODO: implement
	// if (1 == 1) {
	// throw new RuntimeException("Not implemented");
	// }
	// final UnauthenticatedGetQuery<Serializable> query
	// = new AbstractUnauthenticatedGetQuery<Serializable>("artist.getPodcast") {
	// @Override
	// public Serializable parse(final Element root) {
	// return null;
	// }
	// };
	// query.addParameter("artist", artist);
	// return query;
	// }

	/**
	 * Get shouts for this artist.
	 * 
	 * @param artist
	 *            The artist name (Required unless mbid).
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
			final String mbid,
			final Integer limit,
			final Integer page) {

		final UnauthenticatedGetQuery<PaginatedResult<List<Shout>>> query =
				new AbstractUnauthenticatedGetQuery<PaginatedResult<List<Shout>>>("artist.getShouts") {

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
		query.addParameter("mbid", mbid);
		query.addParameter("limit", limit);
		query.addParameter("page", page);

		return query;
	}


	// TODO Using ArrayList to avoid complex generics.
	/**
	 * Get all the artists similar to this artist.
	 * 
	 * @param artist
	 *            The artist name in question (Required unless mbid).
	 * @param mbid
	 *            The musicbrainz id for the artist (Optional).
	 * @param limit
	 *            Limit the number of similar artists returned (Optional).
	 * @return
	 */
	public static UnauthenticatedGetQuery<ArrayList<Artist>> getSimilar(
			final String artist,
			final String mbid,
			final Integer limit) {

		final UnauthenticatedGetQuery<ArrayList<Artist>> query =
				new AbstractUnauthenticatedGetQuery<ArrayList<Artist>>("artist.getSimilar") {

					@Override
					public ArrayList<Artist> parse(final Element root) {

						final ArrayList<Artist> artists = new ArrayList<Artist>();
						final List<Element> artistElements =
								net.dontdrinkandroot.utils.xml.DomUtils.getChildrenByTagName(root, "artist");
						for (final Element artistElement : artistElements) {
							artists.add(new Artist(artistElement));
						}
						return artists;
					}
				};

		query.addParameter("artist", artist);
		query.addParameter("limit", limit);
		query.addParameter("mbid", mbid);

		return query;
	}


	/**
	 * Get the tags applied by an individual user to an artist on Last.fm.
	 * 
	 * @param artist
	 *            The artist name in question (Required).
	 * @param mbid
	 *            The musicbrainz id for the artist (Optional).
	 * @param user
	 *            The User to look up. (Required).
	 * @return
	 */
	public static UnauthenticatedGetQuery<ArrayList<Tag>> getTags(
			final String artist,
			final String mbid,
			final String user) {

		final UnauthenticatedGetQuery<ArrayList<Tag>> query =
				new AbstractUnauthenticatedGetQuery<ArrayList<Tag>>("artist.getTags") {

					@Override
					public ArrayList<Tag> parse(final Element root) {

						final ArrayList<Tag> ret = new ArrayList<Tag>();
						final List<Element> tagElements =
								net.dontdrinkandroot.utils.xml.DomUtils.getChildrenByTagName(root, "tag");
						for (final Element tagElement : tagElements) {
							ret.add(new Tag(tagElement));
						}

						return ret;
					}
				};

		query.addParameter("artist", artist);
		query.addParameter("user", user);

		return query;
	}


	/**
	 * Get the top albums for an artist on Last.fm, ordered by popularity.
	 * 
	 * @param artist
	 *            The artist name in question (Required).
	 * @return
	 */
	// public static UnauthenticatedGetQuery<Serializable> getTopAlbums(
	// final Object artist
	// ) {
	// //TODO: implement
	// if (1 == 1) {
	// throw new RuntimeException("Not implemented");
	// }
	// final UnauthenticatedGetQuery<Serializable> query
	// = new AbstractUnauthenticatedGetQuery<Serializable>("artist.getTopAlbums") {
	// @Override
	// public Serializable parse(final Element root) {
	// return null;
	// }
	// };
	// query.addParameter("artist", artist);
	// return query;
	// }

	/**
	 * Get the top fans for an artist on Last.fm, based on listening data.
	 * 
	 * @param artist
	 *            The artist name in question (Required).
	 * @return
	 */
	// public static UnauthenticatedGetQuery<Serializable> getTopFans(
	// final Object artist
	// ) {
	// //TODO: implement
	// if (1 == 1) {
	// throw new RuntimeException("Not implemented");
	// }
	// final UnauthenticatedGetQuery<Serializable> query
	// = new AbstractUnauthenticatedGetQuery<Serializable>("artist.getTopFans") {
	// @Override
	// public Serializable parse(final Element root) {
	// return null;
	// }
	// };
	// query.addParameter("artist", artist);
	// return query;
	// }

	// TODO: Using ArrayList to prevent complicated generics.
	/**
	 * Get the top tags for an artist on Last.fm, ordered by popularity.
	 * 
	 * @param artist
	 *            The artist name in question (Required unless mbid).
	 * @param mbid
	 *            The musicbrainz id for the artist (Optional).
	 * @return
	 */
	public static UnauthenticatedGetQuery<ArrayList<Tag>> getTopTags(final String artist, final String mbid) {

		final UnauthenticatedGetQuery<ArrayList<Tag>> query =
				new AbstractUnauthenticatedGetQuery<ArrayList<Tag>>("artist.getTopTags") {

					@Override
					public ArrayList<Tag> parse(final Element root) {

						final List<Element> tagElements =
								net.dontdrinkandroot.utils.xml.DomUtils.getChildrenByTagName(root, "tag");
						final ArrayList<Tag> tags = new ArrayList<Tag>();
						for (final Element tagElement : tagElements) {
							tags.add(new Tag(tagElement));
						}

						return tags;
					}
				};

		query.addParameter("artist", artist);
		query.addParameter("mbid", mbid);

		return query;
	}


	/**
	 * Get the top tracks by an artist on Last.fm, ordered by popularity
	 * 
	 * @param artist
	 *            The artist name in question (Required unless mbid).
	 * @param mbid
	 *            The musicbrainz id for the artist (Optional).
	 * @param limit
	 *            The number of results to fetch per page (Optional). Defaults to 50.
	 * @param page
	 *            The page number to fetch (Optional). Default to 50.
	 * 
	 * @return
	 */
	public static UnauthenticatedGetQuery<PaginatedResult<List<Track>>> getTopTracks(
			final String artist,
			final String mbid,
			final Integer limit,
			final Integer page) {

		final UnauthenticatedGetQuery<PaginatedResult<List<Track>>> query =
				new AbstractUnauthenticatedGetQuery<PaginatedResult<List<Track>>>("artist.getTopTracks") {

					@Override
					public PaginatedResult<List<Track>> parse(final Element root) {

						final GenericPaginatedResult<List<Track>> result =
								new GenericPaginatedResult<List<Track>>(root);
						final List<Element> trackElements =
								net.dontdrinkandroot.utils.xml.DomUtils.getChildrenByTagName(root, "track");
						final List<Track> tracks = new ArrayList<Track>();
						for (final Element trackElement : trackElements) {
							tracks.add(new Track(trackElement));
						}
						result.setEntries(tracks);

						return result;
					}
				};

		query.addParameter("artist", artist);
		query.addParameter("mbid", mbid);
		query.addParameter("page", page);
		query.addParameter("limit", limit);

		return query;
	}


	/**
	 * Remove a user's tag from an artist.
	 * 
	 * @param artist
	 *            The artist name in question (Required).
	 * @param tag
	 *            A single user tag to remove from this artist (Required).
	 * @param sk
	 *            A session key generated by authenticating a user via the authentication protocol
	 *            (Required).
	 * @return
	 */
	public static PostQuery<Boolean> removeTag(final String artist, final String tag, final String sk) {

		final PostQuery<Boolean> query = new AbstractPostQuery<Boolean>("artist.removeTag") {

			@Override
			public Boolean parse(final Element root) {

				return true;
			}
		};

		query.addParameter("artist", artist);
		query.addParameter("tag", tag);
		query.addParameter("sk", sk);

		return query;
	}

	/**
	 * Search for an artist by name. Returns artist matches sorted by relevance.
	 * 
	 * @param limit
	 *            Limit the number of artists returned at one time. Default (maximum) is 30.
	 *            (Optional).
	 * @param page
	 *            Scan into the results by specifying a page number. Defaults to first page.
	 *            (Optional).
	 * @param artist
	 *            The artist name in question. (Required).
	 * @return
	 */
	// public static UnauthenticatedGetQuery<Serializable> search(
	// final Object limit,
	// final Object page,
	// final Object artist
	// ) {
	// //TODO: implement
	// if (1 == 1) {
	// throw new RuntimeException("Not implemented");
	// }
	// final UnauthenticatedGetQuery<Serializable> query
	// = new AbstractUnauthenticatedGetQuery<Serializable>("artist.search") {
	// @Override
	// public Serializable parse(final Element root) {
	// return null;
	// }
	// };
	// query.addParameter("limit", limit);
	// query.addParameter("page", page);
	// query.addParameter("artist", artist);
	// return query;
	// }

	/**
	 * Share an artist with Last.fm users or other friends.
	 * 
	 * @param artist
	 *            The artist to share. (Required).
	 * @param recipient
	 *            A Last.fm API key. (Required): Email Address | Last.fm Username - A comma
	 *            delimited list of email addresses or Last.fm usernames. Maximum is 10.<br />
	 *            <span class="param">message</span> (Optional): An optional message to send with
	 *            the recommendation. If not supplied a default message will be used.<br />
	 *            <span class="param">public</span> (Optional): Optionally show in the sharing users
	 *            activity feed. Defaults to 0 (false).<br />
	 *            <span class="param">api_key</span> (Required).
	 * @return
	 */
	// public static AuthenticatedGetQuery<Serializable> share(
	// final Object artist,
	// final Object recipient
	// ) {
	// //TODO: implement
	// if (1 == 1) {
	// throw new RuntimeException("Not implemented");
	// }
	// final AuthenticatedGetQuery<Serializable> query
	// = new AbstractAuthenticatedGetQuery<Serializable>("artist.share") {
	// @Override
	// public Serializable parse(final Element root) {
	// return null;
	// }
	// };
	// query.addParameter("artist", artist);
	// query.addParameter("recipient", recipient);
	// return query;
	// }

	/**
	 * Shout in this artist's shoutbox
	 * 
	 * @param artist
	 *            The name of the artist to shout on. (Required).
	 * @param message
	 *            The message to post to the shoutbox. (Required).
	 * @return
	 */
	// public static AuthenticatedGetQuery<Serializable> shout(
	// final Object artist,
	// final Object message
	// ) {
	// //TODO: implement
	// if (1 == 1) {
	// throw new RuntimeException("Not implemented");
	// }
	// final AuthenticatedGetQuery<Serializable> query
	// = new AbstractAuthenticatedGetQuery<Serializable>("artist.shout") {
	// @Override
	// public Serializable parse(final Element root) {
	// return null;
	// }
	// };
	// query.addParameter("artist", artist);
	// query.addParameter("message", message);
	// return query;
	// }

}
