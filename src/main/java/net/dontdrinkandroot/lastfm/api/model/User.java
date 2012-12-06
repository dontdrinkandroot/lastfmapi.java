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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.dontdrinkandroot.lastfm.api.model.entitytypes.CountryEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.IdEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.ImageEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.MatchEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.NameEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.PlayCountEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.RecentTrackEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.ScrobbleSourceEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.UrlEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.UserEntity;
import net.dontdrinkandroot.lastfm.api.model.paginatedresult.AlbumsPaginatedResult;
import net.dontdrinkandroot.lastfm.api.model.paginatedresult.ArtistsPaginatedResult;
import net.dontdrinkandroot.lastfm.api.model.paginatedresult.GenericPaginatedResult;
import net.dontdrinkandroot.lastfm.api.model.paginatedresult.PaginatedResult;
import net.dontdrinkandroot.lastfm.api.model.paginatedresult.TracksPaginatedResult;
import net.dontdrinkandroot.lastfm.api.model.paginatedresult.UsersPaginatedResult;
import net.dontdrinkandroot.lastfm.api.queries.AbstractAuthenticatedGetQuery;
import net.dontdrinkandroot.lastfm.api.queries.AbstractUnauthenticatedGetQuery;
import net.dontdrinkandroot.lastfm.api.queries.AuthenticatedGetQuery;
import net.dontdrinkandroot.lastfm.api.queries.UnauthenticatedGetQuery;
import net.dontdrinkandroot.lastfm.api.xml.DomUtils;
import net.dontdrinkandroot.utils.ISO_3166_1_alpha2;
import net.dontdrinkandroot.utils.lang.time.DateUtils;

import org.w3c.dom.Element;


public class User extends LfmEntity
		implements Serializable, ImageEntity, NameEntity, UrlEntity, IdEntity, PlayCountEntity, MatchEntity,
		CountryEntity, UserEntity, RecentTrackEntity, ScrobbleSourceEntity {

	private static final long serialVersionUID = 1L;

	public static final transient Map<ImageSize, URL> DEFAULT_IMAGES = new HashMap<ImageSize, URL>();

	/* Initialize default images */
	static {
		try {
			// TODO: EXTRALARGE is missing
			User.DEFAULT_IMAGES.put(ImageSize.MEGA, new URL(
					"http://cdn.last.fm/flatness/catalogue/noimage/2/default_user_mega.png"));
			User.DEFAULT_IMAGES.put(ImageSize.LARGE, new URL(
					"http://cdn.last.fm/flatness/catalogue/noimage/2/default_user_large.png"));
			User.DEFAULT_IMAGES.put(ImageSize.MEDIUM, new URL(
					"http://cdn.last.fm/flatness/catalogue/noimage/2/default_user_medium.png"));
			User.DEFAULT_IMAGES.put(ImageSize.SMALL, new URL(
					"http://cdn.last.fm/flatness/catalogue/noimage/2/default_user_small.png"));
		} catch (final MalformedURLException e) {
			/* Shouldn't happen */
			e.printStackTrace();
		}
	}

	private Long id;

	private String name;

	private String realname;

	private URL url;

	private Map<ImageSize, URL> images;

	private ISO_3166_1_alpha2 country;

	private Integer age;

	private Gender gender;

	private Boolean subscriber;

	private Integer playCount;

	private Boolean bootstrap;

	private Date registered;

	private Integer numPlaylists;

	private Track recentTrack;

	private Float match;

	private ScrobbleSource scrobbleSource;


	public User(final String name) {

		this.name = name;
	}


	/**
	 * Create a user from a dom element.
	 * 
	 * @param element
	 *            The dom element to convert.
	 */
	public User(final Element element) {

		super(element);
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
	public final Float getMatch() {

		return this.match;
	}


	@Override
	public final void setMatch(final Float match) {

		this.match = match;
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
	public final void setImages(final Map<ImageSize, URL> images) {

		this.images = images;
	}


	@Override
	public final Map<ImageSize, URL> getImages() {

		return this.images;
	}


	@Override
	public final String getRealname() {

		return this.realname;
	}


	@Override
	public final void setRealname(final String realname) {

		this.realname = realname;
	}


	@Override
	public final void setRecentTrack(final Track recentTrack) {

		this.recentTrack = recentTrack;
	}


	@Override
	public final Track getRecentTrack() {

		return this.recentTrack;
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
	public final ISO_3166_1_alpha2 getCountry() {

		return this.country;
	}


	@Override
	public final void setCountry(final ISO_3166_1_alpha2 country) {

		this.country = country;
	}


	@Override
	public final Integer getAge() {

		return this.age;
	}


	@Override
	public final void setAge(final Integer age) {

		this.age = age;
	}


	@Override
	public final Gender getGender() {

		return this.gender;
	}


	@Override
	public final void setGender(final Gender gender) {

		this.gender = gender;
	}


	@Override
	public final void setSubscriber(final Boolean subscriber) {

		this.subscriber = subscriber;
	}


	@Override
	public final Boolean isSubscriber() {

		return this.subscriber;
	}


	public final void setNumPlaylists(final Integer numPlaylists) {

		this.numPlaylists = numPlaylists;
	}


	public final Integer getNumPlaylists() {

		return this.numPlaylists;
	}


	@Override
	public final Integer getPlayCount() {

		return this.playCount;
	}


	@Override
	public final void setPlayCount(final Integer playcount) {

		this.playCount = playcount;
	}


	@Override
	public final Boolean isBootstrap() {

		return this.bootstrap;
	}


	@Override
	public final void setBootstrap(final Boolean bootstrap) {

		this.bootstrap = bootstrap;
	}


	public final Date getRegistered() {

		return this.registered;
	}


	public final void setRegistered(final Date registered) {

		this.registered = registered;
	}


	@Override
	public ScrobbleSource getScrobbleSource() {

		return this.scrobbleSource;
	}


	@Override
	public void setScrobbleSource(final ScrobbleSource scrobbleSource) {

		this.scrobbleSource = scrobbleSource;
	}


	/**
	 * Convenience method that returns the small image or the default if not available.
	 * 
	 * @return The small image or the small default.
	 */
	public URL getSmallImage() {

		if (this.images != null && this.images.containsKey(ImageSize.SMALL)) {

			return this.images.get(ImageSize.SMALL);

		} else {

			return User.DEFAULT_IMAGES.get(ImageSize.SMALL);
		}
	}


	/**
	 * Convenience method that returns the medium image or the default if not available.
	 * 
	 * @return The medium image or the medium default.
	 */
	public URL getMediumImage() {

		if (this.images != null && this.images.containsKey(ImageSize.MEDIUM)) {

			return this.images.get(ImageSize.MEDIUM);

		} else {

			return User.DEFAULT_IMAGES.get(ImageSize.MEDIUM);
		}

	}


	/**
	 * Convenience method that returns the large image or the default if not available.
	 * 
	 * @return The large image or the large default.
	 */
	public URL getLargeImage() {

		if (this.images != null && this.images.containsKey(ImageSize.LARGE)) {

			return this.images.get(ImageSize.LARGE);

		} else {

			return User.DEFAULT_IMAGES.get(ImageSize.LARGE);
		}
	}


	/**
	 * Convenience method that returns the extralarge image or the default if not available.
	 * 
	 * @return The extralarge image or the extralarge default.
	 */
	public URL getExtraLargeImage() {

		if (this.images != null && this.images.containsKey(ImageSize.EXTRALARGE)) {

			return this.images.get(ImageSize.EXTRALARGE);

		} else {

			// TODO: Fix as soon as EXTRALARGE placeholder image is defined
			return User.DEFAULT_IMAGES.get(ImageSize.MEGA);
		}
	}


	@Override
	public final String toString() {

		return this.name;
	};


	/**
	 * Based on the assumption that username is unique.
	 */
	@Override
	public final int hashCode() {

		final int prime = 31;
		int result = 1;
		final int intermediate;

		if (this.name != null) {
			intermediate = this.name.hashCode();
		} else {
			intermediate = 0;
		}

		result = prime * result + intermediate;

		return result;
	}


	/**
	 * Based on the assumption that username is unique.
	 */
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

		final User other = (User) obj;
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
	 * Get a list of tracks by a given artist scrobbled by this user, including scrobble time. Can
	 * be limited to specific timeranges, defaults to all time.
	 * 
	 * @param user
	 *            The last.fm username to fetch the recent tracks of (Required).
	 * @param artist
	 *            The artist name you are interested in (Required).
	 * @param startTimestamp
	 *            An unix timestamp to start at (Optional).
	 * @param endTimestamp
	 *            An unix timestamp to end at (Optional).
	 * @param page
	 *            An integer used to fetch a specific page of tracks (Optional).
	 * @return
	 */
	public static UnauthenticatedGetQuery<GenericPaginatedResult<List<Track>>> getArtistTracks(
			final String user,
			final String artist,
			final Long startTimestamp,
			final Long endTimestamp,
			final Integer page) {

		final UnauthenticatedGetQuery<GenericPaginatedResult<List<Track>>> query =
				new AbstractUnauthenticatedGetQuery<GenericPaginatedResult<List<Track>>>("user.getArtistTracks") {

					@Override
					public GenericPaginatedResult<List<Track>> parse(final Element root) {

						final GenericPaginatedResult<List<Track>> result =
								new GenericPaginatedResult<List<Track>>(root);
						final List<Track> tracks = new ArrayList<Track>();
						final List<Element> trackElements =
								net.dontdrinkandroot.utils.xml.DomUtils.getChildrenByTagName(root, "track");
						for (final Element trackElement : trackElements) {
							tracks.add(new Track(trackElement));
						}
						result.setEntries(tracks);

						return result;
					}
				};

		query.addParameter("user", user);
		query.addParameter("artist", artist);
		if (startTimestamp != null) {
			query.addParameter("startTimestamp", startTimestamp / DateUtils.MILLIS_PER_SECOND);
		}
		query.addParameter("page", page);
		if (endTimestamp != null) {
			query.addParameter("endTimestamp", endTimestamp / DateUtils.MILLIS_PER_SECOND);
		}

		return query;
	}


	/**
	 * Returns the tracks banned by the user.
	 * 
	 * @param user
	 *            The user name (Required).
	 * @param limit
	 *            The number of results to fetch per page (Optional). Defaults to 50.
	 * @param page
	 *            The page number to fetch. Defaults to first page (Optional).
	 * @return
	 */
	public static UnauthenticatedGetQuery<GenericPaginatedResult<List<Track>>> getBannedTracks(
			final String user,
			final Integer limit,
			final Integer page) {

		final UnauthenticatedGetQuery<GenericPaginatedResult<List<Track>>> query =
				new AbstractUnauthenticatedGetQuery<GenericPaginatedResult<List<Track>>>("user.getBannedTracks") {

					@Override
					public GenericPaginatedResult<List<Track>> parse(final Element root) {

						return new TracksPaginatedResult(root);
					}

				};

		query.addParameter("user", user);
		query.addParameter("limit", limit);
		query.addParameter("page", page);

		return query;
	}


	/**
	 * Get a list of upcoming events that this user is attending. TODO: COMPLETE
	 * 
	 * @param user
	 *            The user to fetch the events for (Required).
	 * @return
	 */
	public static UnauthenticatedGetQuery<ArrayList<Event>> getEvents(final String user) {

		final UnauthenticatedGetQuery<ArrayList<Event>> query =
				new AbstractUnauthenticatedGetQuery<ArrayList<Event>>("user.getEvents") {

					@Override
					public ArrayList<Event> parse(final Element root) {

						final ArrayList<Event> events = new ArrayList<Event>();
						final List<Element> eventElements =
								net.dontdrinkandroot.utils.xml.DomUtils.getChildrenByTagName(root, "event");
						for (final Element eventElement : eventElements) {
							events.add(new Event(eventElement));
						}

						return events;
					}
				};

		query.addParameter("user", user);

		return query;
	}


	/**
	 * Get a list of the user's friends on Last.fm.
	 * 
	 * @param user
	 *            The last.fm username to fetch the friends of. (Required).
	 * @param recenttracks
	 *            Whether or not to include information about friends' recent listening in the
	 *            response. (Optional).
	 * @param limit
	 *            An integer used to limit the number of friends returned per page. The default is
	 *            50. (Optional).
	 * @param page
	 *            The page number to fetch. (Optional).
	 * @return
	 */
	public static UnauthenticatedGetQuery<GenericPaginatedResult<List<User>>> getFriends(
			final String user,
			final Boolean recenttracks,
			final Integer limit,
			final Integer page) {

		final UnauthenticatedGetQuery<GenericPaginatedResult<List<User>>> query =
				new AbstractUnauthenticatedGetQuery<GenericPaginatedResult<List<User>>>("user.getFriends") {

					@Override
					public GenericPaginatedResult<List<User>> parse(final Element root) {

						return new UsersPaginatedResult(root);
					}
				};

		query.addParameter("user", user);
		query.addParameter("recenttracks", recenttracks);
		query.addParameter("limit", limit);
		query.addParameter("page", page);

		return query;
	}


	/**
	 * Get information about a user profile.
	 * 
	 * @param user
	 *            The user to fetch info for (Required).
	 */
	public static UnauthenticatedGetQuery<User> getInfo(final String user) {

		final UnauthenticatedGetQuery<User> query = new AbstractUnauthenticatedGetQuery<User>("user.getInfo") {

			@Override
			public User parse(final Element root) {

				final User user = new User(root);
				user.setNumPlaylists(net.dontdrinkandroot.utils.xml.DomUtils.toInteger(net.dontdrinkandroot.utils.xml.DomUtils.getChildByTagName(
						root,
						"playlists")));
				user.setRegistered(net.dontdrinkandroot.utils.xml.DomUtils.toDateFromSecondsTimestamp(
						net.dontdrinkandroot.utils.xml.DomUtils.getChildByTagName(root, "registered"),
						"unixtime"));

				return user;
			}
		};

		query.addParameter("user", user);

		return query;
	}


	public static AuthenticatedGetQuery<User> getInfo(Session session) {

		final AuthenticatedGetQuery<User> query = new AbstractAuthenticatedGetQuery<User>("user.getInfo") {

			@Override
			public User parse(final Element root) {

				final User user = new User(root);
				user.setNumPlaylists(DomUtils.toInteger(DomUtils.getChildByTagName(root, "playlists")));
				user.setRegistered(DomUtils.toDateFromSecondsTimestamp(
						DomUtils.getChildByTagName(root, "registered"),
						"unixtime"));

				return user;
			}
		};

		query.addParameter("sk", session.getKey());

		return query;
	}


	// /**
	// * Get the last 50 tracks loved by a user.
	// * @param user The user name to fetch the loved tracks for. (Required).
	// * @param limit An integer used to limit the number of tracks returned per page. The default
	// is 50. (Optional).
	// * @param page The page number to fetch. (Optional).
	// * @return
	// */
	// public static UnauthenticatedGetQuery<Serializable> getLovedTracks(
	// final Object user,
	// final Object limit,
	// final Object page
	// ) {
	// //TODO: implement
	// if (1 == 1) throw new RuntimeException("Not implemented");
	// UnauthenticatedGetQuery<Serializable> query
	// = new AbstractUnauthenticatedGetQuery<Serializable>("user.getLovedTracks") {
	// @Override
	// public Serializable parse(final Element root) {
	// return null;
	// }
	// };
	// query.addParameter("user", user);
	// query.addParameter("limit", limit);
	// query.addParameter("page", page);
	// return query;
	// }

	/**
	 * Get a list of a user's neighbours on Last.fm. TODO: Using ArrayList here to avoid complex
	 * generics.
	 * 
	 * @param user
	 *            The last.fm username to fetch the neighbours of. (Required).
	 * @param limit
	 *            An integer used to limit the number of neighbours returned. (Optional).
	 * @return
	 */
	public static UnauthenticatedGetQuery<ArrayList<User>> getNeighbours(final String user, final Integer limit) {

		final UnauthenticatedGetQuery<ArrayList<User>> query =
				new AbstractUnauthenticatedGetQuery<ArrayList<User>>("user.getNeighbours") {

					@Override
					public ArrayList<User> parse(final Element root) {

						final ArrayList<User> ret = new ArrayList<User>();
						final List<Element> neighbourElements = DomUtils.getChildrenByTagName(root, "user");
						for (final Element neighbourElement : neighbourElements) {
							ret.add(new User(neighbourElement));
						}

						return ret;
					}
				};

		query.addParameter("user", user);
		query.addParameter("limit", limit);

		return query;
	}


	// /**
	// * Get a paginated list of all events a user has attended in the past.
	// * @param user The username to fetch the events for. (Required).
	// * @param page The page number to scan to. (Optional).
	// * @param limit The maximum number of events to return per page. (Optional).
	// * @return
	// */
	// public static UnauthenticatedGetQuery<Serializable> getPastEvents(
	// final Object user,
	// final Object page,
	// final Object limit
	// ) {
	// //TODO: implement
	// if (1 == 1) throw new RuntimeException("Not implemented");
	// UnauthenticatedGetQuery<Serializable> query
	// = new AbstractUnauthenticatedGetQuery<Serializable>("user.getPastEvents") {
	// @Override
	// public Serializable parse(final Element root) {
	// return null;
	// }
	// };
	// query.addParameter("user", user);
	// query.addParameter("page", page);
	// query.addParameter("limit", limit);
	// return query;
	// }
	//
	//
	// /**
	// * Get a list of a user's playlists on Last.fm.
	// * @param user The last.fm username to fetch the playlists of. (Required).
	// * @return
	// */
	// public static UnauthenticatedGetQuery<Serializable> getPlaylists(
	// final Object user
	// ) {
	// //TODO: implement
	// if (1 == 1) throw new RuntimeException("Not implemented");
	// UnauthenticatedGetQuery<Serializable> query
	// = new AbstractUnauthenticatedGetQuery<Serializable>("user.getPlaylists") {
	// @Override
	// public Serializable parse(final Element root) {
	// return null;
	// }
	// };
	// query.addParameter("user", user);
	// return query;
	// }
	//
	//
	// /**
	// * Get a list of the recent Stations listened to by this user.
	// * @param user The last.fm username to fetch the recent Stations of. (Required).
	// * @param limit An integer used to limit the number of stations returned per page. The default
	// is 10, the maximum is 25 (Optional).
	// * @param page The page number to fetch. (Optional).
	// * @return
	// */
	// public static AuthenticatedGetQuery<Serializable> getRecentStations(
	// final Object user,
	// final Object limit,
	// final Object page
	// ) {
	// //TODO: implement
	// if (1 == 1) throw new RuntimeException("Not implemented");
	// AuthenticatedGetQuery<Serializable> query
	// = new AbstractAuthenticatedGetQuery<Serializable>("user.getRecentStations") {
	// @Override
	// public Serializable parse(final Element root) {
	// return null;
	// }
	// };
	// query.addParameter("user", user);
	// query.addParameter("limit", limit);
	// query.addParameter("page", page);
	// return query;
	// }
	//
	//
	/**
	 * Get a list of the recent tracks listened to by this user. Also includes the currently playing
	 * track with the nowplaying="true" attribute if the user is currently listening.
	 * 
	 * @param user
	 *            The last.fm username to fetch the recent tracks of (Required).
	 * @param limit
	 *            The number of results to fetch per page (Optional). Defaults to 50. Maximum is
	 *            200.
	 * @param page
	 *            The page number to fetch (Optional). Defaults to first page.
	 * @param from
	 *            Beginning timestamp of a range - only display scrobbles after this time, in UNIX
	 *            timestamp format (integer number of seconds since 00:00:00, January 1st 1970 UTC)
	 *            (Optional). This must be in the UTC time zone.
	 * @param to
	 *            End timestamp of a range - only display scrobbles before this time, in UNIX
	 *            timestamp format (integer number of seconds since 00:00:00, January 1st 1970 UTC)
	 *            (Optional). This must be in the UTC time zone.
	 * 
	 */
	public static UnauthenticatedGetQuery<PaginatedResult<List<Track>>> getRecentTracks(
			final String user,
			final Integer limit,
			final Integer page,
			Integer from,
			Integer to) {

		final UnauthenticatedGetQuery<PaginatedResult<List<Track>>> query =
				new AbstractUnauthenticatedGetQuery<PaginatedResult<List<Track>>>("user.getRecentTracks") {

					@Override
					public PaginatedResult<List<Track>> parse(final Element root) {

						return new TracksPaginatedResult(root);
					}
				};

		query.addParameter("user", user);
		query.addParameter("limit", limit);
		query.addParameter("page", page);
		query.addParameter("to", to);
		query.addParameter("from", from);

		return query;
	}


	/**
	 * Get a list of the recent tracks listened to by this user. Also includes the currently playing
	 * track with the nowplaying="true" attribute if the user is currently listening.
	 * 
	 * @param user
	 *            The last.fm username to fetch the recent tracks of (Required).
	 * @param limit
	 *            The number of results to fetch per page (Optional). Defaults to 50. Maximum is
	 *            200.
	 * @param page
	 *            The page number to fetch (Optional). Defaults to first page.
	 * @param from
	 *            Beginning timestamp of a range - only display scrobbles after this time, in UNIX
	 *            timestamp format (integer number of seconds since 00:00:00, January 1st 1970 UTC)
	 *            (Optional). This must be in the UTC time zone.
	 * @param to
	 *            End timestamp of a range - only display scrobbles before this time, in UNIX
	 *            timestamp format (integer number of seconds since 00:00:00, January 1st 1970 UTC)
	 *            (Optional). This must be in the UTC time zone.
	 * 
	 */
	public static AuthenticatedGetQuery<PaginatedResult<List<Track>>> getAuthenticatedRecentTracks(
			final String user,
			final Integer limit,
			final Integer page,
			Integer from,
			Integer to,
			String sk) {

		final AuthenticatedGetQuery<PaginatedResult<List<Track>>> query =
				new AbstractAuthenticatedGetQuery<PaginatedResult<List<Track>>>("user.getRecentTracks") {

					@Override
					public PaginatedResult<List<Track>> parse(final Element root) {

						return new TracksPaginatedResult(root);
					}
				};

		query.addParameter("user", user);
		query.addParameter("limit", limit);
		query.addParameter("page", page);
		query.addParameter("to", to);
		query.addParameter("from", from);
		query.addParameter("sk", sk);

		return query;
	}


	//
	//
	// /**
	// * Get Last.fm artist recommendations for a user
	// * @return
	// */
	// public static AuthenticatedGetQuery<Serializable> getRecommendedArtists(
	// ) {
	// //TODO: implement
	// if (1 == 1) throw new RuntimeException("Not implemented");
	// AuthenticatedGetQuery<Serializable> query
	// = new AbstractAuthenticatedGetQuery<Serializable>("user.getRecommendedArtists") {
	// @Override
	// public Serializable parse(final Element root) {
	// return null;
	// }
	// };
	// return query;
	// }
	//
	//
	// /**
	// * Get a paginated list of all events recommended to a user by Last.fm, basedon their
	// listening profile.
	// * @param page The page number to scan to. (Optional).
	// * @param limit The number of events to return per page. (Optional).
	// * @return
	// */
	// public static AuthenticatedGetQuery<Serializable> getRecommendedEvents(
	// final Object page,
	// final Object limit
	// ) {
	// //TODO: implement
	// if (1 == 1) throw new RuntimeException("Not implemented");
	// AuthenticatedGetQuery<Serializable> query
	// = new AbstractAuthenticatedGetQuery<Serializable>("user.getRecommendedEvents") {
	// @Override
	// public Serializable parse(final Element root) {
	// return null;
	// }
	// };
	// query.addParameter("page", page);
	// query.addParameter("limit", limit);
	// return query;
	// }
	//
	//
	// /**
	// * Get shouts for this user. Also available as an rss feed.
	// * @param user The username to fetch shouts for (Required).
	// * @return
	// */
	// public static UnauthenticatedGetQuery<Serializable> getShouts(
	// final Object user
	// ) {
	// //TODO: implement
	// if (1 == 1) throw new RuntimeException("Not implemented");
	// UnauthenticatedGetQuery<Serializable> query
	// = new AbstractUnauthenticatedGetQuery<Serializable>("user.getShouts") {
	// @Override
	// public Serializable parse(final Element root) {
	// return null;
	// }
	// };
	// query.addParameter("user", user);
	// return query;
	// }

	/**
	 * Get the top albums listened to by a user. You can stipulate a time period. Sends the overall
	 * chart by default.
	 * 
	 * @param user
	 *            The user name to fetch top albums for (Required).
	 * @param period
	 *            The time period over which to retrieve top albums for (Optional).
	 * @param limit
	 *            The number of results to fetch per page (Optional). Defaults to 50.
	 * @param page
	 *            The page number to fetch (Optional). Defaults to first page.
	 * @return
	 */
	public static UnauthenticatedGetQuery<PaginatedResult<List<Album>>> getTopAlbums(
			final String user,
			final Period period,
			final Integer limit,
			final Integer page) {

		final UnauthenticatedGetQuery<PaginatedResult<List<Album>>> query =
				new AbstractUnauthenticatedGetQuery<PaginatedResult<List<Album>>>("user.getTopAlbums") {

					@Override
					public PaginatedResult<List<Album>> parse(final Element root) {

						return new AlbumsPaginatedResult(root);
					}
				};

		query.addParameter("user", user);
		query.addParameter("period", period);
		query.addParameter("limit", limit);
		query.addParameter("page", page);

		return query;
	}


	/**
	 * Get the top artists listened to by a user. You can stipulate a time period. Sends the overall
	 * chart by default.
	 * 
	 * @param user
	 *            The user name to fetch top artists for (Required).
	 * @param period
	 *            The time period over which to retrieve top artists for (Optional).
	 * @param limit
	 *            The number of results to fetch per page (Optional). Defaults to 50.
	 * @param page
	 *            The page number to fetch (Optional). Defaults to first page.
	 */
	public static UnauthenticatedGetQuery<PaginatedResult<List<Artist>>> getTopArtists(
			final String user,
			final Period period,
			final Integer limit,
			final Integer page) {

		final UnauthenticatedGetQuery<PaginatedResult<List<Artist>>> query =
				new AbstractUnauthenticatedGetQuery<PaginatedResult<List<Artist>>>("user.getTopArtists") {

					@Override
					public PaginatedResult<List<Artist>> parse(final Element root) {

						return new ArtistsPaginatedResult(root);
					}
				};

		query.addParameter("user", user);
		query.addParameter("period", period);
		query.addParameter("limit", limit);
		query.addParameter("page", page);

		return query;
	}


	// /**
	// * Get the top tags used by this user.
	// * @param user The user name (Required).
	// * @param limit Limit the number of tags returned (Optional).
	// * @return
	// */
	// public static UnauthenticatedGetQuery<Serializable> getTopTags(
	// final Object user,
	// final Object limit
	// ) {
	// //TODO: implement
	// if (1 == 1) throw new RuntimeException("Not implemented");
	// UnauthenticatedGetQuery<Serializable> query
	// = new AbstractUnauthenticatedGetQuery<Serializable>("user.getTopTags") {
	// @Override
	// public Serializable parse(final Element root) {
	// return null;
	// }
	// };
	// query.addParameter("user", user);
	// query.addParameter("limit", limit);
	// return query;
	// }

	/**
	 * Get the top tracks listened to by a user. You can stipulate a time period. Sends the overall
	 * chart by default.
	 * 
	 * @param user
	 *            The user name to fetch top tracks for (Required).
	 * @param period
	 *            The time period over which to retrieve top tracks for (Optional).
	 * @param limit
	 *            The number of results to fetch per page (Optional). Defaults to 50.
	 * @param The
	 *            page number to fetch (Optional). Defaults to first page.
	 */
	public static UnauthenticatedGetQuery<PaginatedResult<List<Track>>> getTopTracks(
			final String user,
			final Period period,
			final Integer limit,
			final Integer page) {

		final UnauthenticatedGetQuery<PaginatedResult<List<Track>>> query =
				new AbstractUnauthenticatedGetQuery<PaginatedResult<List<Track>>>("user.getTopTracks") {

					@Override
					public PaginatedResult<List<Track>> parse(final Element root) {

						return new TracksPaginatedResult(root);
					}
				};

		query.addParameter("user", user);
		query.addParameter("period", period);
		query.addParameter("limit", limit);
		query.addParameter("page", page);

		return query;
	}

	// /**
	// * Get an album chart for a user profile, for a given date range. If no daterange is supplied,
	// it will return the most recent album chart for thisuser.
	// * @param user The last.fm username to fetch the charts of. (Required).
	// * @param from The date at which the chart should start from. See User.getChartsList for more.
	// (Optional).
	// * @param to The date at which the chart should end on. See User.getChartsList for more.
	// (Optional).
	// * @return
	// */
	// public static UnauthenticatedGetQuery<Serializable> getWeeklyAlbumChart(
	// final Object user,
	// final Object from,
	// final Object to
	// ) {
	// //TODO: implement
	// if (1 == 1) throw new RuntimeException("Not implemented");
	// UnauthenticatedGetQuery<Serializable> query
	// = new AbstractUnauthenticatedGetQuery<Serializable>("user.getWeeklyAlbumChart") {
	// @Override
	// public Serializable parse(final Element root) {
	// return null;
	// }
	// };
	// query.addParameter("user", user);
	// query.addParameter("from", from);
	// query.addParameter("to", to);
	// return query;
	// }
	//
	//
	// /**
	// * Get an artist chart for a user profile, for a given date range. If no daterange is
	// supplied, it will return the most recent artist chart for thisuser.
	// * @param user The last.fm username to fetch the charts of. (Required).
	// * @param from The date at which the chart should start from. See User.getWeeklyChartList for
	// more. (Optional).
	// * @param to The date at which the chart should end on. See User.getWeeklyChartList for more.
	// (Optional).
	// * @return
	// */
	// public static UnauthenticatedGetQuery<Serializable> getWeeklyArtistChart(
	// final Object user,
	// final Object from,
	// final Object to
	// ) {
	// //TODO: implement
	// if (1 == 1) throw new RuntimeException("Not implemented");
	// UnauthenticatedGetQuery<Serializable> query
	// = new AbstractUnauthenticatedGetQuery<Serializable>("user.getWeeklyArtistChart") {
	// @Override
	// public Serializable parse(final Element root) {
	// return null;
	// }
	// };
	// query.addParameter("user", user);
	// query.addParameter("from", from);
	// query.addParameter("to", to);
	// return query;
	// }
	//
	//
	// /**
	// * Get a list of available charts for this user, expressed as date ranges which can be sent
	// * to the chart services.
	// * TODO: Explicitly using {@link ArrayList} as return type to avoid compliated generics.
	// * @param user The last.fm username to fetch the charts list for. (Required).
	// * @return
	// */
	// public static UnauthenticatedGetQuery<ArrayList<ChartRange>> getWeeklyChartList(
	// final String user
	// ) {
	// UnauthenticatedGetQuery<ArrayList<ChartRange>> query
	// = new AbstractUnauthenticatedGetQuery<ArrayList<ChartRange>>(
	// "user.getWeeklyChartList"
	// ) {
	// @SuppressWarnings("unchecked")
	// @Override
	// public ArrayList<ChartRange> parse(final Element root) {
	// ArrayList<ChartRange> result = new ArrayList<ChartRange>();
	// for (Element chartRangeElement : (List<Element>) root.elements("chart")) {
	// result.add(new ChartRange(chartRangeElement));
	// }
	// return result;
	// }
	// };
	// query.addParameter("user", user);
	// return query;
	// }
	//
	//
	// /**
	// * Get a track chart for a user profile, for a given date range. If no daterange is supplied,
	// * it will return the most recent track chart for thisuser.
	// * TODO: I'm explicitly using ArrayList here.
	// * @param user The last.fm username to fetch the charts of. (Required).
	// * @param from The date at which the chart should start from. See User.getWeeklyChartList
	// * for more. (Optional).
	// * @param to The date at which the chart should end on. See User.getWeeklyChartList for
	// * more. (Optional).
	// * @return
	// */
	// public static UnauthenticatedGetQuery<ArrayList<Track>> getWeeklyTrackChart(
	// final String user,
	// final Date from,
	// final Date to
	// ) {
	// UnauthenticatedGetQuery<ArrayList<Track>> query
	// = new AbstractUnauthenticatedGetQuery<ArrayList<Track>>("user.getWeeklyTrackChart") {
	// @SuppressWarnings("unchecked")
	// @Override
	// public ArrayList<Track> parse(final Element root) {
	// ArrayList<Track> ret = new ArrayList<Track>();
	// for (Element trackElement : (List<Element>) root.elements("track")) {
	// ret.add(new Track(trackElement));
	// }
	// return ret;
	// }
	// };
	// query.addParameter("user", user);
	// if (from != null) {
	// query.addParameter("from", from.getTime() / Duration.SECOND);
	// }
	// if (to != null) {
	// query.addParameter("to", to.getTime() / Duration.SECOND);
	// }
	// return query;
	// }
	//
	//
	// /**
	// * Shout on this user's shoutbox
	// * @param user The name of the user to shout on. (Required).
	// * @param message The message to post to the shoutbox. (Required).
	// * @param sk A session key generated by authenticating a user via the authentication
	// * protocol. (Required).
	// * @return
	// */
	// public static PostQuery<Serializable> shout(
	// final Object user,
	// final Object message,
	// final Object sk
	// ) {
	// //TODO: implement
	// if (1 == 1) throw new RuntimeException("Not implemented");
	// PostQuery<Serializable> query
	// = new AbstractPostQuery<Serializable>("user.shout") {
	// @Override
	// public Serializable parse(final Element root) {
	// return null;
	// }
	// };
	// query.addParameter("user", user);
	// query.addParameter("message", message);
	// query.addParameter("sk", sk);
	// return query;
	// }

}
