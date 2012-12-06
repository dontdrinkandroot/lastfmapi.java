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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.dontdrinkandroot.lastfm.api.LastfmWebServicesException;
import net.dontdrinkandroot.lastfm.api.model.paginatedresult.PaginatedResult;
import net.dontdrinkandroot.utils.ISO_3166_1_alpha2;

import org.junit.Assume;
import org.junit.Test;


/**
 * TODO: actually we should always check a set of artists.
 */
public class ArtistTest extends AbstractModelTest {

	/**
	 * Test {@link Artist#addTags}, actually performed by
	 * {@link ArtistTest#testArtistAddRemoveGetTags}.
	 */
	@Test
	public final void testArtistAddTags() throws LastfmWebServicesException {

		this.assertMissingParameter(
				Artist.addTags(null, null, null),
				LastfmWebServicesException.INVALID_PARAMETERS,
				"Invalid parameters - Your request is missing the [artist] parameter");
	};


	/**
	 * Test {@link Artist#addTags}, {@link Artist#removeTag}, {@link Artist#getTags()}.
	 */
	@Test
	public final void testArtistAddRemoveGetTags() throws LastfmWebServicesException {

		Assume.assumeTrue(AbstractModelTest.getTestConf().runManipulatingAuthTests());

		final List<String> toAddTags = new ArrayList<String>();
		toAddTags.add(AbstractModelTest.DUMMY_TAG_1);
		toAddTags.add(AbstractModelTest.DUMMY_TAG_2);
		toAddTags.add(AbstractModelTest.DUMMY_TAG_3);

		/* Acquire session */
		final Session session =
				AbstractModelTest.getWs().fetch(
						Auth.getMobileSession(AbstractModelTest.getTestConf().getUser(), Auth.generateMobileToken(
								AbstractModelTest.getTestConf().getUser(),
								AbstractModelTest.getTestConf().getPassword())

						));

		/* Add tags */
		AbstractModelTest.getWs().execute(Artist.addTags(AbstractModelTest.DUMMY_ARTIST, toAddTags, session.getKey()));

		/* Get tags */
		List<Tag> tags =
				AbstractModelTest.getWs().fetch(Artist.getTags(AbstractModelTest.DUMMY_ARTIST, null, session.getKey()));
		// TODO This should be invalidated after checking as it will otherwise remain in test cache

		/* Check if tags were found */
		this.assertNotNull(tags);
		final Set<String> expectedTags = new HashSet<String>(toAddTags);
		this.assertEquals(expectedTags.size(), tags.size());
		for (final Tag tag : tags) {
			this.assertNameSet(tag);
			this.assertUrlSet(tag);
			this.assertTrue(expectedTags.contains(tag.getName().toLowerCase()));
			expectedTags.remove(tag.getName().toLowerCase());
		}
		this.assertTrue(expectedTags.isEmpty());

		/* Remove tags */
		AbstractModelTest.getWs().execute(
				Artist.removeTag(AbstractModelTest.DUMMY_ARTIST, AbstractModelTest.DUMMY_TAG_1, session.getKey()));
		AbstractModelTest.getWs().execute(
				Artist.removeTag(AbstractModelTest.DUMMY_ARTIST, AbstractModelTest.DUMMY_TAG_2, session.getKey()));
		AbstractModelTest.getWs().execute(
				Artist.removeTag(AbstractModelTest.DUMMY_ARTIST, AbstractModelTest.DUMMY_TAG_3, session.getKey()));

		/* Fetch tags */
		tags = AbstractModelTest.getWs().fetch(Artist.getTags(AbstractModelTest.DUMMY_ARTIST, null, session.getKey()));

		/* Check if tags where removed */
		this.assertEquals(0, tags.size());

		// TODO: test mbid

	};


	/**
	 * Test {@link Artist#getEvents}.
	 */
	// @Test
	// public final void testArtistGetEvents() throws LastfmWebServicesException {
	// Object result = getWs().fetch(Artist.getEvents(null));
	// };

	/**
	 * Test {@link Artist#getImages}.
	 */
	// @Test
	// public final void testArtistGetImages() throws LastfmWebServicesException {
	// Object result = getWs().fetch(Artist.getImages(null, null, null, null));
	// };

	/**
	 * Test {@link Artist#getCorrection(String)}.
	 * 
	 * @throws LastfmWebServicesException
	 */
	@Test
	public final void testArtistGetCorrection() throws LastfmWebServicesException {

		this.assertMissingParameter(
				Artist.getCorrection(null),
				LastfmWebServicesException.INVALID_PARAMETERS,
				"Invalid parameters - Your request is missing the [artist] parameter");

		final List<Artist> artists = AbstractModelTest.getWs().fetch(Artist.getCorrection("Guns and Roses"));
		this.assertNotEmpty(artists);
		final Artist artist = artists.iterator().next();
		this.assertName(artist, "Guns N' Roses");
		this.assertMbidSet(artist);
		this.assertUrlSet(artist);
	}


	/**
	 * Test {@link Artist#getInfo}.
	 */
	@Test
	public final void testArtistGetInfo() throws LastfmWebServicesException {

		/* Check empty query */
		this.assertMissingParameter(
				Artist.getInfo(null, null, null, null),
				LastfmWebServicesException.INVALID_PARAMETERS,
				"You must supply either an artist name or a musicbrainz id");

		/* Do fetch by name */
		Artist artist =
				AbstractModelTest.getWs().fetch(
						Artist.getInfo(
								AbstractModelTest.TEST_ARTIST,
								null,
								AbstractModelTest.TEST_USER,
								ISO_3166_1_alpha2.DE));
		this.assertArtistGetInfo(artist);

		/* Do fetch by mbid */
		artist =
				AbstractModelTest.getWs().fetch(
						Artist.getInfo(null, AbstractModelTest.TEST_ARTIST_MBID, AbstractModelTest.TEST_USER, null));
		this.assertArtistGetInfo(artist);
	};


	private void assertArtistGetInfo(final Artist artist) {

		this.assertName(artist, AbstractModelTest.TEST_ARTIST);
		this.assertMbid(artist, AbstractModelTest.TEST_ARTIST_MBID);
		this.assertUrlSet(artist);
		this.assertImagesSet(artist);
		this.assertStreamableSet(artist);
		this.assertPlayCountSet(artist);
		this.assertListenersSet(artist);
		this.assertUserPlayCountSet(artist);
		// TODO: assert similar set
		this.assertFalse(artist.getTags().isEmpty());
		for (final Tag tag : artist.getTags()) {
			this.assertNameSet(tag);
			this.assertUrlSet(tag);
		}
		final Wiki wiki = artist.getWiki();
		this.assertNotNull(wiki);
		this.assertTrue(wiki.getPublished().getTime() > AbstractModelTest.TIMESTAMP_2005);
		this.assertStringNotEmpty(wiki.getContent());

	}


	// /**
	// * Test {@link Artist#getPastEvents}.
	// */
	// @Test
	// public final void testArtistGetPastEvents() throws LastfmWebServicesException {
	//
	// this.assertMissingParameter(
	// Artist.getPastEvents(null, null, null, null),
	// LastfmWebServicesException.INVALID_PARAMETERS,
	// "You must supply either an artist name or a musicbrainz id");
	//
	// PaginatedResult<List<Event>> result =
	// AbstractModelTest.getWs().fetch(Artist.getPastEvents(AbstractModelTest.TEST_ARTIST, null, 2,
	// 25));
	// this.assertPaginatedResult(result, 25);
	//
	// List<Event> events = result.getEntries();
	// for (Event event : events) {
	// this.assertIdSet(event);
	// this.assertTitleSet(event);
	// }
	// };

	/**
	 * Test {@link Artist#getPodcast}.
	 */
	// @Test
	// public final void testArtistGetPodcast() throws LastfmWebServicesException {
	// Object result = getWs().fetch(Artist.getPodcast(null));
	// };

	/**
	 * Test {@link Artist#getShouts}.
	 * 
	 * @throws LastfmWebServicesException
	 */
	@Test
	public final void testArtistGetShouts() throws LastfmWebServicesException {

		this.assertMissingParameter(
				Artist.getShouts(null, null, null, null),
				LastfmWebServicesException.INVALID_PARAMETERS,
				"You must supply either an artist name or a musicbrainz id");

		PaginatedResult<List<Shout>> result =
				AbstractModelTest.getWs().fetch(
						Artist.getShouts(AbstractModelTest.TEST_ARTIST, null, AbstractModelTest.TEST_LIMIT, 2));
		this.assertArtistGetShouts(result);

		result =
				AbstractModelTest.getWs().fetch(
						Artist.getShouts(null, AbstractModelTest.TEST_ARTIST_MBID, AbstractModelTest.TEST_LIMIT, 2));
		this.assertArtistGetShouts(result);
	}


	private void assertArtistGetShouts(final PaginatedResult<List<Shout>> result) {

		this.assertPaginatedResult(result);

		final List<Shout> shouts = result.getEntries();
		this.assertNotEmpty(shouts);
		for (final Shout shout : shouts) {
			this.assertBodySet(shout);
			this.assertAuthorSet(shout);
			this.assertDateSet(shout);
		}
	}


	/**
	 * Test {@link Artist#getSimilar}.
	 */
	@Test
	public final void testArtistGetSimilar() throws LastfmWebServicesException {

		this.assertMissingParameter(
				Artist.getSimilar(null, null, null),
				LastfmWebServicesException.INVALID_PARAMETERS,
				"You must supply either an artist name or a musicbrainz id");

		List<Artist> artists =
				AbstractModelTest.getWs().fetch(
						Artist.getSimilar(AbstractModelTest.TEST_ARTIST, null, AbstractModelTest.TEST_LIMIT));
		this.assertArtistGetSimilar(artists);

		artists =
				AbstractModelTest.getWs().fetch(
						Artist.getSimilar(null, AbstractModelTest.TEST_ARTIST_MBID, AbstractModelTest.TEST_LIMIT));
		this.assertArtistGetSimilar(artists);
	}


	private void assertArtistGetSimilar(final List<Artist> artists) {

		this.assertNotEmpty(artists);
		this.assertEquals(AbstractModelTest.TEST_LIMIT, artists.size());
		boolean mbidFound = false;
		for (final Artist artist : artists) {
			this.assertNameSet(artist);
			this.assertMbidNullOrNotEmpty(artist);
			if (artist.getMbid() != null) {
				mbidFound = true;
			}
			this.assertMatchSet(artist);
			this.assertUrlSet(artist);
			this.assertStreamableSet(artist);
			this.assertImagesSet(artist);
		}
		this.assertTrue(mbidFound);
	}


	/**
	 * Test {@link Artist#getTags}, actually performed by
	 * {@link ArtistTest#testArtistAddRemoveGetTags}.
	 */
	@Test
	public final void testArtistGetTags() throws LastfmWebServicesException {

		this.assertMissingParameter(
				Artist.getTags(null, null, null),
				LastfmWebServicesException.INVALID_PARAMETERS,
				"You must supply either an artist name or a musicbrainz id");
	};


	/**
	 * Test {@link Artist#getTopAlbums}.
	 */
	// @Test
	// public final void testArtistGetTopAlbums() throws LastfmWebServicesException {
	// Object result = getWs().fetch(Artist.getTopAlbums(null));
	// };

	/**
	 * Test {@link Artist#getTopFans}.
	 */
	// @Test
	// public final void testArtistGetTopFans() throws LastfmWebServicesException {
	// Object result = getWs().fetch(Artist.getTopFans(null));
	// };

	/**
	 * Test {@link Artist#getTopTags}.
	 */
	@Test
	public final void testArtistGetTopTags() throws LastfmWebServicesException {

		this.assertMissingParameter(
				Artist.getTopTags(null, null),
				LastfmWebServicesException.INVALID_PARAMETERS,
				"You must supply either an artist name or a musicbrainz id");

		List<Tag> topTags = AbstractModelTest.getWs().fetch(Artist.getTopTags(AbstractModelTest.TEST_ARTIST, null));
		this.assertArtistGetTopTags(topTags);

		topTags = AbstractModelTest.getWs().fetch(Artist.getTopTags(null, AbstractModelTest.TEST_ARTIST_MBID));
		this.assertArtistGetTopTags(topTags);
	};


	private void assertArtistGetTopTags(final List<Tag> topTags) {

		this.assertNotNull(topTags);
		for (final Tag tag : topTags) {
			this.assertNameSet(tag);
			this.assertCountSet(tag);
			this.assertUrlSet(tag);
		}
	}


	/**
	 * Test {@link Artist#getTopTracks}.
	 */
	@Test
	public final void testArtistGetTopTracks() throws LastfmWebServicesException {

		this.assertMissingParameter(
				Artist.getTopTracks(null, null, null, null),
				LastfmWebServicesException.INVALID_PARAMETERS,
				"You must supply either an artist name or a musicbrainz id");

		PaginatedResult<List<Track>> result =
				AbstractModelTest.getWs().fetch(
						Artist.getTopTracks(AbstractModelTest.TEST_ARTIST, null, AbstractModelTest.TEST_LIMIT, 2));
		this.assertArtistGetTopTracks(result);

		result =
				AbstractModelTest.getWs().fetch(
						Artist.getTopTracks(null, AbstractModelTest.TEST_ARTIST_MBID, AbstractModelTest.TEST_LIMIT, 2));
		this.assertArtistGetTopTracks(result);
	};


	private void assertArtistGetTopTracks(final PaginatedResult<List<Track>> result) {

		this.assertPaginatedResult(result);

		final List<Track> tracks = result.getEntries();
		this.assertNotEmpty(tracks);

		boolean durationFound = false;
		for (final Track track : tracks) {
			this.assertRankSet(track);
			this.assertNameSet(track);
			this.assertMbidNullOrNotEmpty(track);
			if (track.getDuration() != null && track.getDuration() > 0) {
				durationFound = true;
			}
			this.assertPlayCountSet(track);
			this.assertListenersSet(track);
			this.assertStreamableSet(track);
			final Artist artist = track.getArtist();
			this.assertNameSet(artist);
			this.assertMbidSet(artist);
			this.assertUrlSet(artist);
		}
		this.assertTrue(durationFound);

	}


	/**
	 * Test {@link Artist#removeTag}, actually performed by
	 * {@link ArtistTest#testArtistAddRemoveGetTags}.
	 */
	@Test
	public final void testArtistRemoveTag() throws LastfmWebServicesException {

		this.assertMissingParameter(
				Artist.removeTag(null, null, null),
				LastfmWebServicesException.INVALID_PARAMETERS,
				"Invalid parameters - Your request is missing the [artist] parameter");
	};

	/**
	 * Test {@link Artist#search}.
	 */
	// @Test
	// public final void testArtistSearch() throws LastfmWebServicesException {
	// Object result = getWs().fetch(Artist.search(null, null, null));
	// };

	/**
	 * Test {@link Artist#share}.
	 */
	// @Test
	// public final void testArtistShare() throws LastfmWebServicesException {
	// Object result = getWs().fetch(Artist.share(null, null));
	// };

	/**
	 * Test {@link Artist#shout}.
	 */
	// @Test
	// public final void testArtistShout() throws LastfmWebServicesException {
	// Object result = getWs().fetch(Artist.shout(null, null));
	// };

}
