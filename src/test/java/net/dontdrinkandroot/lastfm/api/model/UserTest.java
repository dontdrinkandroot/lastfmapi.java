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
import java.util.List;

import net.dontdrinkandroot.lastfm.api.LastfmWebServicesException;
import net.dontdrinkandroot.lastfm.api.model.paginatedresult.GenericPaginatedResult;
import net.dontdrinkandroot.lastfm.api.model.paginatedresult.PaginatedResult;
import net.dontdrinkandroot.utils.ISO_3166_1_alpha2;
import net.dontdrinkandroot.utils.lang.StringUtils;

import org.junit.Assert;
import org.junit.Test;


public class UserTest extends AbstractModelTest {

	/**
	 * Test {@link User#getArtistTracks}.
	 */
	@Test
	public final void testGetArtistTracks() throws LastfmWebServicesException {

		this.assertMissingParameter(
				User.getArtistTracks(null, null, null, null, null),
				LastfmWebServicesException.INVALID_PARAMETERS,
				"Invalid parameters - Your request is missing the [user] parameter");

		final long startTimeStamp = 1123513699000L;
		final long endTimeStamp = 1305890958000L;

		final GenericPaginatedResult<List<Track>> result =
				AbstractModelTest.getWs().fetch(
						User.getArtistTracks(
								AbstractModelTest.TEST_USER,
								AbstractModelTest.TEST_ARTIST,
								startTimeStamp,
								endTimeStamp,
								2));

		this.assertPaginatedResult(result, 50);

		final List<Track> tracks = result.getEntries();
		boolean albumFound = false;
		for (final Track track : tracks) {

			this.assertNameSet(track);
			this.assertMbidNullOrNotEmpty(track);
			this.assertUrlSet(track);
			this.assertDateSet(track);
			this.assertTrue(track.getDate().getTime() > startTimeStamp);
			this.assertTrue(track.getDate().getTime() < endTimeStamp);

			final Artist artist = track.getArtist();
			this.assertNotNull(artist);
			this.assertNameSet(artist);
			this.assertMbidSet(artist);

			final Album album = track.getAlbum();
			if (album != null) {
				this.assertNameSet(album);
				this.assertMbidSet(album);
				albumFound = true;
			}
		}
		this.assertTrue(albumFound);
	};


	@Test
	public void testGetBannedTracks() throws LastfmWebServicesException {

		this.assertMissingParameter(
				User.getBannedTracks(null, null, null),
				LastfmWebServicesException.INVALID_PARAMETERS,
				"Invalid parameters - Your request is missing the [user] parameter");

		final GenericPaginatedResult<List<Track>> result =
				AbstractModelTest.getWs().fetch(
						User.getBannedTracks(AbstractModelTest.TEST_USER_2, AbstractModelTest.TEST_LIMIT, 2));

		this.assertPaginatedResult(result);

		boolean allInfo = false;
		for (final Track track : result.getEntries()) {
			allInfo = allInfo || this.assertSimpleTrack(track, true, true);

		}
		this.assertTrue(allInfo);
	}


	/**
	 * Test {@link User#getEvents}.//TODO: complete
	 */
	// @Test
	// public final void testUserGetEvents() throws LastfmWebServicesException {
	//
	// this.assertMissingParameter(
	// User.getEvents(null),
	// LastfmWebServicesException.INVALID_PARAMETERS,
	// "Invalid parameters - Your request is missing a required parameter");
	//
	// final List<Event> events = this.getWs().fetch(User.getEvents(AbstractModelTest.TEST_USER_2));
	//
	// this.assertNotEmpty(events);
	// for (final Event event : events) {
	// this.assertIdSet(event);
	// this.assertTitleSet(event);
	// this.assertImage(event);
	// this.assertNotNull(event.getVenue());
	// this.assertNotNull(event.getVenue().getLocation());
	// this.assertNotNull(event.getVenue().getLocation().getCoordinate());
	// this.assertNotNull(event.getVenue().getLocation().getCoordinate().getLatitude());
	// this.assertNotNull(event.getVenue().getLocation().getCoordinate().getLongitude());
	// }
	//
	// };

	/**
	 * Test {@link User#getFriends}.
	 */
	@Test
	public final void testGetFriends() throws LastfmWebServicesException {

		this.assertMissingParameter(
				User.getFriends(null, null, null, null),
				LastfmWebServicesException.INVALID_PARAMETERS,
				"Invalid parameters - Your request is missing the [user] parameter");

		final GenericPaginatedResult<List<User>> result =
				AbstractModelTest.getWs().fetch(
						User.getFriends(AbstractModelTest.TEST_USER_2, true, AbstractModelTest.TEST_LIMIT, 2));
		this.assertPaginatedResult(result);

		final List<User> friends = result.getEntries();
		this.assertNotEmpty(friends);

		boolean realNameFound = false;
		boolean imagesFound = false;
		boolean recentTrackFound = false;
		boolean recentTrackAlbumFound = false;
		boolean scrobbleSourceUrlFound = false;
		boolean scrobbleSourceFound = false;
		boolean scrobbleSourceImageFound = false;

		for (final User friend : friends) {
			this.assertNameSet(friend);
			if (!StringUtils.isEmpty(friend.getRealname())) {
				realNameFound = true;
			}
			if (this.hasImages(friend)) {
				imagesFound = true;
			}
			this.assertUrlSet(friend);

			final Track recentTrack = friend.getRecentTrack();
			if (recentTrack != null) {
				recentTrackFound = true;
				// TODO: 20110613 Date of recenttrack currently not supported by last.fm
				this.assertNotNull(recentTrack);
				this.assertNameSet(recentTrack);
				this.assertUrlSet(recentTrack);
				this.assertMbidNullOrNotEmpty(recentTrack);
				final Artist artist = recentTrack.getArtist();
				this.assertNameSet(artist);
				this.assertMbidNullOrNotEmpty(artist);
				this.assertUrlSet(artist);
				final Album album = recentTrack.getAlbum();
				if (album != null) {
					this.assertNameSet(album);
					this.assertUrlSet(album);
					this.assertMbidNullOrNotEmpty(album);
					recentTrackAlbumFound = true;
				}
				final ScrobbleSource scrobbleSource = friend.getScrobbleSource();
				if (scrobbleSource != null) {
					this.assertNameSet(scrobbleSource);
					if (scrobbleSource.getUrl() != null) {
						scrobbleSourceUrlFound = true;
					}
					if (scrobbleSource.getImages() != null && scrobbleSource.getImages().values().size() > 0) {
						scrobbleSourceImageFound = true;
					}
					scrobbleSourceFound = true;
				}
			}
		}
		/* Not all users have realname */
		this.assertTrue(realNameFound);
		/* Not all users have images */
		this.assertTrue(imagesFound);
		/* Not all users have recenttracks */
		this.assertTrue(recentTrackFound);
		/* Not all recenttracks have albums */
		this.assertTrue(recentTrackAlbumFound);
		/* Not all scrobblesources have urls */
		this.assertTrue(scrobbleSourceUrlFound);
		/* Not all friends have scrobblesources */
		this.assertTrue(scrobbleSourceFound);
		/* Not all scrobblesources have images */
		this.assertTrue(scrobbleSourceImageFound);
	};


	/**
	 * Test {@link User#getInfo}.
	 */
	@Test
	public final void testGetInfo() throws LastfmWebServicesException, MalformedURLException {

		/* Test with empty parameters */
		this.assertMissingParameter(
				User.getInfo((String) null),
				LastfmWebServicesException.INVALID_PARAMETERS,
				"No user with that name was found");

		/* Test user RJ */
		final String userName = "RJ";
		final String realName = "Richard Jones "; // whitespace intentional!
		final URL url = new URL("http://www.last.fm/user/RJ");
		final Long id = new Long(1000002);
		final ISO_3166_1_alpha2 country = ISO_3166_1_alpha2.UK;
		final Gender gender = Gender.MALE;
		final long registeredTime = 1037793040L * 1000L;

		User user = AbstractModelTest.getWs().fetch(User.getInfo("RJ"));
		this.assertName(user, userName);
		this.assertUrl(user, url);
		this.assertImagesSet(user);
		this.assertId(user, id);
		this.assertRealname(user, realName);
		this.assertCountry(user, country);
		this.assertGender(user, gender);
		this.assertSubscriber(user, true);
		this.assertBootstrap(user, false);
		this.assertPlayCount(user);
		this.assertAge(user);
		this.assertTrue(user.getNumPlaylists() > 0);
		this.assertEquals(registeredTime, user.getRegistered().getTime());

		/* Test user with incomplete information */
		user = AbstractModelTest.getWs().fetch(User.getInfo("renny_s"));
		this.assertNull(user.getRealname());
		this.assertNull(user.getAge());
	};


	// /**
	// * Test {@link User#getLovedTracks}.testUserGetTopAlbums
	// */
	// @Test
	// public final void testUserGetLovedTracks() throws LastfmWebServicesException {
	// Object result = getWs().fetch(User.getLovedTracks(null, null, null));
	// };

	/**
	 * Test {@link User#getNeighbours}.
	 */
	@Test
	public final void testGetNeighbours() throws LastfmWebServicesException {

		this.assertMissingParameter(
				User.getNeighbours(null, null),
				LastfmWebServicesException.INVALID_PARAMETERS,
				"Invalid parameters - Your request is missing the [user] parameter");

		final List<User> neighbours =
				AbstractModelTest.getWs().fetch(
						User.getNeighbours(AbstractModelTest.TEST_USER, AbstractModelTest.TEST_LIMIT));
		this.assertNotNull(neighbours);
		this.assertEquals(AbstractModelTest.TEST_LIMIT, neighbours.size());
		boolean realNameFound = false;
		boolean imageFound = false;
		for (final User user : neighbours) {
			this.assertNameSet(user);
			this.assertStringNullOrNotEmpty(user.getRealname());
			if (user.getRealname() != null) {
				realNameFound = true;
			}
			this.assertUrlSet(user);
			if (this.hasImages(user)) {
				imageFound = true;
			}
			this.assertMatchSet(user);
		}
		this.assertTrue(realNameFound);
		this.assertTrue(imageFound);
	}


	// /**
	// * Test {@link User#getPastEvents}.
	// */
	// @Test
	// public final void testUserGetPastEvents() throws LastfmWebServicesException {
	// Object result = getWs().fetch(User.getPastEvents(null, null, null));
	// };
	//
	// /**
	// * Test {@link User#getPlaylists}.
	// */
	// @Test
	// public final void testUserGetPlaylists() throws LastfmWebServicesException {
	// Object result = getWs().fetch(User.getPlaylists(null));
	// };
	//
	// /**
	// * Test {@link User#getRecentStations}.
	// */
	// @Test
	// public final void testUserGetRecentStations() throws LastfmWebServicesException {
	// Object result = getWs().fetch(User.getRecentStations(null, null, null));
	// };

	//
	/**
	 * Test {@link User#getRecentTracks}.
	 */
	@Test
	public final void testGetRecentTracks() throws LastfmWebServicesException {

		PaginatedResult<List<Track>> result =
				AbstractModelTest.getWs().fetch(User.getRecentTracks("shoxrocks", null, null, 1115610824, 1115612683));

		Assert.assertEquals(9, result.getTotal());
		List<Track> tracks = result.getEntries();

		Track firstTrack = tracks.get(0);
		this.assertName(firstTrack, "Now Or Never");
		this.assertArtist(firstTrack, "Godsmack");
		this.assertMbid(firstTrack, "31c9f5b8-d3cf-4921-81ed-6c2ed8bcb215");
		this.assertAlbum(firstTrack, "Godsmack");
		this.assertDate(firstTrack, 1115612682L);

		for (Track track : tracks) {
			this.assertArtistSet(track);
			this.assertNameSet(track);
			this.assertStreamableSet(track);
			this.assertMbidNullOrNotEmpty(track);
			this.assertUrlSet(track);
			this.assertDateSet(track);
		}
	};


	//
	// /**
	// * Test {@link User#getRecommendedArtists}.
	// */
	// @Test
	// public final void testUserGetRecommendedArtists() throws LastfmWebServicesException {
	// Object result = getWs().fetch(User.getRecommendedArtists());
	// };
	//
	// /**
	// * Test {@link User#getRecommendedEvents}.
	// */
	// @Test
	// public final void testUserGetRecommendedEvents() throws LastfmWebServicesException {
	// Object result = getWs().fetch(User.getRecommendedEvents(null, null));
	// };
	//
	// /**
	// * Test {@link User#getShouts}.
	// */
	// @Test
	// public final void testUserGetShouts() throws LastfmWebServicesException {
	// Object result = getWs().fetch(User.getShouts(null));
	// };

	/**
	 * Test {@link User#getTopAlbums}.
	 */
	@Test
	public final void testGetTopAlbums() throws LastfmWebServicesException {

		this.assertMissingParameter(
				User.getTopAlbums(null, null, null, null),
				LastfmWebServicesException.INVALID_PARAMETERS,
				"Invalid parameters - Your request is missing the [user] parameter");

		final PaginatedResult<List<Album>> result =
				AbstractModelTest.getWs().fetch(
						User.getTopAlbums(
								AbstractModelTest.TEST_USER,
								Period.TWELVE_MONTH,
								AbstractModelTest.TEST_LIMIT,
								2));
		this.assertPaginatedResult(result);

		final List<Album> albums = result.getEntries();
		this.assertNotEmpty(albums);

		boolean albumMbidFound = false;
		for (final Album album : albums) {
			this.assertNameSet(album);
			this.assertPlayCountSet(album);
			this.assertMbidNullOrNotEmpty(album);
			if (album.getMbid() != null) {
				albumMbidFound = true;
			}
			this.assertUrlSet(album);
			this.assertImagesSet(album);
			final Artist artist = album.getArtist();
			this.assertNameSet(artist);
			this.assertMbidSet(artist);
			this.assertUrlSet(artist);
		}
		this.assertTrue(albumMbidFound);
	};


	/**
	 * Test {@link User#getTopArtists}.
	 */
	@Test
	public final void testGetTopArtists() throws LastfmWebServicesException {

		this.assertMissingParameter(
				User.getTopArtists(null, null, null, null),
				LastfmWebServicesException.INVALID_PARAMETERS,
				"Invalid parameters - Your request is missing the [user] parameter");

		final PaginatedResult<List<Artist>> result =
				AbstractModelTest.getWs().fetch(
						User.getTopArtists(
								AbstractModelTest.TEST_USER,
								Period.TWELVE_MONTH,
								AbstractModelTest.TEST_LIMIT,
								2));

		this.assertPaginatedResult(result);

		final List<Artist> artists = result.getEntries();
		this.assertNotEmpty(artists);
		boolean mbidFound = false;
		boolean imagesFound = false;
		for (final Artist artist : artists) {

			this.assertNameSet(artist);
			this.assertPlayCountSet(artist);
			this.assertMbidNullOrNotEmpty(artist);
			if (artist.getMbid() != null) {
				mbidFound = true;
			}
			this.assertStreamableSet(artist);
			if (this.hasImages(artist)) {
				imagesFound = true;
			}
		}

		this.assertTrue(mbidFound);
		this.assertTrue(imagesFound);
	};


	// /**
	// * Test {@link User#getTopTags}.
	// */
	// @Test
	// public final void testUserGetTopTags() throws LastfmWebServicesException {
	// Object result = getWs().fetch(User.getTopTags(null, null));
	// };

	/**
	 * Test {@link User#getTopTracks}.
	 */
	@Test
	public final void testGetTopTracks() throws LastfmWebServicesException {

		this.assertMissingParameter(
				User.getTopTracks(null, null, null, null),
				LastfmWebServicesException.INVALID_PARAMETERS,
				"Invalid parameters - Your request is missing the [user] parameter");

		final PaginatedResult<List<Track>> tracksResult =
				AbstractModelTest.getWs().fetch(User.getTopTracks("shoxrocks", Period.OVERALL, 75, 2));

		Assert.assertEquals(2, tracksResult.getPage());
		Assert.assertEquals(75, tracksResult.getPerPage());
		Assert.assertEquals(260, tracksResult.getTotalPages());
		Assert.assertEquals(19430, tracksResult.getTotal());

		int rank = 76;
		for (final Track track : tracksResult.getEntries()) {
			this.assertSimpleTrack(track, false, false);
			this.assertEquals(new Integer(rank), track.getRank());
			rank++;
		}
	};

	// TODO: test periods

	// /**
	// * Test {@link User#getWeeklyAlbumChart}.
	// */
	// @Test
	// public final void testUserGetWeeklyAlbumChart() throws LastfmWebServicesException {
	// Object result = getWs().fetch(User.getWeeklyAlbumChart(null, null, null));
	// };
	//
	// /**
	// * Test {@link User#getWeeklyArtistChart}.
	// */
	// @Test
	// public final void testUserGetWeeklyArtistChart() throws LastfmWebServicesException {
	// Object result = getWs().fetch(User.getWeeklyArtistChart(null, null, null));
	// };
	//
	// /**
	// * Test {@link User#getWeeklyChartList}.
	// */
	// @Test
	// public final void testUserGetWeeklyChartList() throws LastfmWebServicesException {
	//
	// getCache().resetStats();
	//
	// /* Test with empty parameters */
	// try {
	// getWs().fetch(User.getWeeklyChartList(null));
	// throw new RuntimeException("Exception expected");
	// } catch (LastfmWebServicesException e) {
	// assertEquals(LastfmWebServicesException.INVALID_PARAMETERS, e.getCode());
	// assertEquals(
	// "Invalid parameters - Your request is missing a required parameter",
	// e.getMessage()
	// );
	// }
	//
	// final String username = "RJ";
	// final Date firstChartFrom = new Date(1108296002000L);
	// final Date firstChartTo = new Date(1108900802000L);
	//
	// List<ChartRange> chartRanges = getWs().fetch(User.getWeeklyChartList(username));
	//
	// /* Check known entites */
	// final ChartRange firstRange = chartRanges.iterator().next();
	// assertEquals(firstChartFrom, firstRange.getFrom());
	// assertEquals(firstChartTo, firstRange.getTo());
	//
	// /* Check for desired properties */
	// Date lastTo = null;
	// for (ChartRange currentRange : chartRanges) {
	// assertTrue(currentRange.getTo().after(currentRange.getFrom()));
	// if (lastTo != null) {
	// /* This doen't work, ranges seem to be overlapping a bit
	// * in real life data.
	// */
	// // assertTrue(currentRange.getFrom().after(lastTo));
	// assertTrue(lastTo.getTime() - Duration.DAY < currentRange.getFrom().getTime());
	// }
	// lastTo = currentRange.getTo();
	// }
	//
	// /* Check cache access */
	// assertEquals(2, getCache().getCacheMisses());
	// assertEquals(0, getCache().getCacheHits());
	// };
	//
	// /**
	// * Test {@link User#getWeeklyTrackChart}.
	// */
	// @Test
	// public final void testUserGetWeeklyTrackChart() throws LastfmWebServicesException {
	//
	// getCache().resetStats();
	//
	// /* Test with empty parameters */
	// try {
	// getWs().fetch(User.getWeeklyTrackChart(null, null, null));
	// throw new RuntimeException("Exception expected");
	// } catch (LastfmWebServicesException e) {
	// assertEquals(LastfmWebServicesException.INVALID_PARAMETERS, e.getCode());
	// assertEquals(
	// "Invalid parameters - Your request is missing a required parameter",
	// e.getMessage()
	// );
	// }
	//
	// final String username = "RJ";
	// final int expectedSize = 309;
	// final String pjHarveyMbid = "e795e03d-b5d5-4a5f-834d-162cfb308a2c";
	// final String urlString = "http://www.last.fm/music/PJ+Harvey/_/This+Is+Love";
	//
	// /* Fetch valid dates */
	// List<ChartRange> chartRanges = getWs().fetch(User.getWeeklyChartList(username));
	// ChartRange firstRange = chartRanges.iterator().next();
	//
	// /* Fetch list */
	// List<Track> trackCharts = getWs().fetch(
	// User.getWeeklyTrackChart(username, firstRange.getFrom(), firstRange.getTo())
	// );
	// assertNotNull(trackCharts);
	// assertEquals(expectedSize, trackCharts.size());
	//
	// /* Check first track */
	// Track firstTrack = trackCharts.iterator().next();
	// assertEquals("This Is Love", firstTrack.getName());
	// assertEquals("PJ Harvey", firstTrack.getArtist().getName());
	// assertEquals(pjHarveyMbid, firstTrack.getArtist().getMbid());
	// assertEquals(urlString, firstTrack.getUrl().toString());
	//
	// /* Check properties of all tracks */
	// Integer count = 1;
	// for (Track track : trackCharts) {
	// assertEquals(count, track.getRank());
	// TrackTest.assertSimpleTrack(track);
	// count++;
	// }
	//
	// /* Check cache */
	// assertEquals(3, getCache().getCacheMisses());
	// assertEquals(0, getCache().getCacheHits());
	//
	// };
	//
	// /**
	// * Test {@link User#shout}.
	// */
	// @Test
	// public final void testUserShout() throws LastfmWebServicesException {
	// Assume.assumeTrue(this.getTestConf().runManipulatingAuthTests());
	// Object result = getWs().execute(User.shout(null, null, null));
	// };

}
