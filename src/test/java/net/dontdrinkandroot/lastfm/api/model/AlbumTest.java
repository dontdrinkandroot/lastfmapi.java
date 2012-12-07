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
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.dontdrinkandroot.lastfm.api.LastfmWebServicesException;
import net.dontdrinkandroot.lastfm.api.model.paginatedresult.PaginatedResult;
import net.dontdrinkandroot.utils.ISO_3166_1_alpha2;

import org.junit.Assume;
import org.junit.Ignore;
import org.junit.Test;


public class AlbumTest extends AbstractModelTest {

	/**
	 * Test {@link Album#addTags(String, String, String, String)}, this is actually performed by
	 * {@link AlbumTest#testAddRemoveGetTags()}
	 * 
	 * @throws Exception
	 */
	@Test
	public final void testAddTags() {

		this.assertMissingParameter(
				Album.addTags(null, null, null, null),
				LastfmWebServicesException.INVALID_PARAMETERS,
				"Invalid parameters - Your request is missing the [artist] parameter");
	}


	/**
	 * Test {@link Album#addTags}, {@link Album#removeTag(String, String, String, String)},
	 * {@link Album#getTags()}.
	 */
	@Test
	public final void testAddRemoveGetTags() throws LastfmWebServicesException {

		Assume.assumeTrue(AbstractModelTest.getTestConf().runManipulatingAuthTests());

		final List<String> toAddTags = new ArrayList<String>();
		toAddTags.add(AbstractModelTest.DUMMY_TAG_1);
		toAddTags.add(AbstractModelTest.DUMMY_TAG_2);
		toAddTags.add(AbstractModelTest.DUMMY_TAG_3);

		/* Acquire session */
		final Session session = AbstractModelTest.getSession();

		/* Add tags */
		AbstractModelTest.getWs().execute(
				Album.addTags(
						AbstractModelTest.DUMMY_ARTIST,
						AbstractModelTest.DUMMY_ALBUM,
						toAddTags,
						session.getKey()));

		/* Get tags */
		List<Tag> tags =
				AbstractModelTest.getWs().fetch(
						Album.getTags(
								AbstractModelTest.DUMMY_ARTIST,
								AbstractModelTest.DUMMY_ALBUM,
								null,
								AbstractModelTest.getTestConf().getUser()));
		// TODO This should actually be invalidated after checking as otherwise it remains in test
		// cache

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
				Album.removeTag(
						AbstractModelTest.DUMMY_ARTIST,
						AbstractModelTest.DUMMY_ALBUM,
						AbstractModelTest.DUMMY_TAG_1,
						session.getKey()));
		AbstractModelTest.getWs().execute(
				Album.removeTag(
						AbstractModelTest.DUMMY_ARTIST,
						AbstractModelTest.DUMMY_ALBUM,
						AbstractModelTest.DUMMY_TAG_2,
						session.getKey()));
		AbstractModelTest.getWs().execute(
				Album.removeTag(
						AbstractModelTest.DUMMY_ARTIST,
						AbstractModelTest.DUMMY_ALBUM,
						AbstractModelTest.DUMMY_TAG_3,
						session.getKey()));

		/* Fetch tags */
		tags =
				AbstractModelTest.getWs().fetch(
						Album.getTags(
								AbstractModelTest.DUMMY_ARTIST,
								AbstractModelTest.DUMMY_ALBUM,
								null,
								session.getKey()));

		/* Check if tags where removed */
		this.assertEquals(0, tags.size());

		// TODO: test mbid

	};


	/**
	 * Test {@link Album#getBuylinks}.
	 */
	@Test
	public final void testGetBuylinks() throws LastfmWebServicesException {

		this.assertMissingParameter(
				Album.getBuylinks(null, null, null, null),
				LastfmWebServicesException.INVALID_PARAMETERS,
				"Invalid parameters - Your request is missing the [country] parameter");

		Affiliations affiliations =
				AbstractModelTest.getWs().fetch(
						Album.getBuylinks(
								AbstractModelTest.TEST_ARTIST,
								AbstractModelTest.TEST_ALBUM,
								null,
								ISO_3166_1_alpha2.DE));
		this.assertGetBuyLinks(affiliations);

		affiliations =
				AbstractModelTest.getWs().fetch(
						Album.getBuylinks(null, null, AbstractModelTest.TEST_ALBUM_MBID, ISO_3166_1_alpha2.DE));
		this.assertGetBuyLinks(affiliations);
	};


	private void assertGetBuyLinks(final Affiliations affiliations) {

		final List<Affiliation> physicals = affiliations.getPhysicalAffiliations();
		this.assertNotEmpty(physicals);
		for (final Affiliation affiliation : physicals) {
			this.assertSupplierNameSet(affiliation);
			this.assertPriceValid(affiliation);
			this.assertBuyLinkSet(affiliation);
			this.assertSupplierIconSet(affiliation);
			this.assertIsSearchSet(affiliation);
		}

		final List<Affiliation> downloads = affiliations.getPhysicalAffiliations();
		this.assertNotEmpty(downloads);
		for (final Affiliation affiliation : downloads) {
			this.assertSupplierNameSet(affiliation);
			this.assertPriceValid(affiliation);
			this.assertBuyLinkSet(affiliation);
			this.assertSupplierIconSet(affiliation);
			this.assertIsSearchSet(affiliation);
		}

	}


	/**
	 * Test {@link Album#getInfo}.
	 */
	@Test
	public final void testGetInfo() throws LastfmWebServicesException {

		this.assertMissingParameter(
				Album.getInfo(null, null, null, null, null),
				LastfmWebServicesException.INVALID_PARAMETERS,
				"You must supply either an album & artist name or an album mbid.");

		/* Fetch by artist and title */
		Album album =
				AbstractModelTest.getWs().fetch(
						Album.getInfo(
								AbstractModelTest.TEST_ARTIST,
								AbstractModelTest.TEST_ALBUM,
								null,
								AbstractModelTest.TEST_USER,
								ISO_3166_1_alpha2.EN));
		this.assertGetInfo(album);

		/* Fetch by mbid */
		album =
				AbstractModelTest.getWs().fetch(
						Album.getInfo(null, null, AbstractModelTest.TEST_ALBUM_MBID, AbstractModelTest.TEST_USER, null));
		this.assertGetInfo(album);
	}


	private void assertGetInfo(final Album album) {

		this.assertName(album, AbstractModelTest.TEST_ALBUM);
		this.assertName(album.getArtist(), AbstractModelTest.TEST_ARTIST);
		this.assertMbid(album, AbstractModelTest.TEST_ALBUM_MBID);
		this.assertIdSet(album);
		this.assertUrlSet(album);
		this.assertReleaseDate(album, AbstractModelTest.TEST_ALBUM_RELEASEDATE_TIMESTAMP);
		this.assertImagesSet(album);
		this.assertListenersSet(album);
		this.assertPlayCountSet(album);
		this.assertUserPlayCountSet(album);
		this.assertNotNull(album.getTracks());
		this.assertFalse(album.getTracks().isEmpty());
		for (final Track track : album.getTracks()) {
			this.assertRankSet(track);
			this.assertDurationSet(track);
			this.assertMbidNullOrNotEmpty(track);
			this.assertUrlSet(track);
			this.assertStreamableSet(track);
			final Artist trackArtist = track.getArtist();
			this.assertNameSet(trackArtist);
			this.assertMbidSet(trackArtist);
			this.assertUrlSet(trackArtist);
		}
		this.assertNotNull(album.getTags());
		this.assertFalse(album.getTags().isEmpty());
		for (final Tag tag : album.getTags()) {
			this.assertNameSet(tag);
			this.assertUrlSet(tag);
		}
		final Wiki wiki = album.getWiki();
		this.assertNotNull(wiki);
		this.assertTrue(wiki.getPublished().getTime() > AbstractModelTest.TIMESTAMP_2005);
		this.assertStringNotEmpty(wiki.getContent());
	}


	/**
	 * Test {@link Album#getShouts}.
	 * 
	 * @throws LastfmWebServicesException
	 */
	@Test
	public final void testGetShouts() throws LastfmWebServicesException {

		this.assertMissingParameter(
				Album.getShouts(null, null, null, null, null),
				LastfmWebServicesException.INVALID_PARAMETERS,
				"You must supply either an album & artist name or an album mbid.");

		PaginatedResult<List<Shout>> result =
				AbstractModelTest.getWs().fetch(
						Album.getShouts(
								AbstractModelTest.TEST_ARTIST,
								AbstractModelTest.TEST_ALBUM,
								null,
								AbstractModelTest.TEST_LIMIT,
								2));
		this.assertGetShouts(result);

		result =
				AbstractModelTest.getWs().fetch(
						Album.getShouts(null, null, AbstractModelTest.TEST_ALBUM_MBID, AbstractModelTest.TEST_LIMIT, 2));
		this.assertGetShouts(result);
	}


	private void assertGetShouts(final PaginatedResult<List<Shout>> result) {

		this.assertEquals(AbstractModelTest.TEST_LIMIT, result.getPerPage());
		this.assertEquals(2, result.getPage());
		this.assertGreaterZero(result.getTotal());
		this.assertGreaterZero(result.getTotalPages());

		final List<Shout> shouts = result.getEntries();
		this.assertNotEmpty(shouts);
		for (final Shout shout : shouts) {
			this.assertBodySet(shout);
			this.assertAuthorSet(shout);
			this.assertDateSet(shout);
		}
	}


	/**
	 * Test {@link Album#getTags()}, this is actually performed by
	 * {@link AlbumTest#testAddRemoveGetTags()}.
	 * 
	 * @throws LastfmWebServicesException
	 */
	@Test
	public final void testGetTags() throws LastfmWebServicesException {

		this.assertMissingParameter(
				Album.getTags(null, null, null, null),
				LastfmWebServicesException.INVALID_PARAMETERS,
				"You must supply either an album & artist name or an album mbid.");

	}


	/**
	 * Test {@link Album#getTopTags}.
	 */
	@Test
	public final void testGetTopTags() throws LastfmWebServicesException {

		this.assertMissingParameter(
				Album.getTopTags(null, null, null),
				LastfmWebServicesException.INVALID_PARAMETERS,
				"You must supply either an album & artist name or an album mbid.");

		ArrayList<Tag> tags =
				AbstractModelTest.getWs().fetch(
						Album.getTopTags(AbstractModelTest.TEST_ARTIST, AbstractModelTest.TEST_ALBUM, null));
		this.asssertGetTags(tags);

		tags = AbstractModelTest.getWs().fetch(Album.getTopTags(null, null, AbstractModelTest.TEST_ALBUM_MBID));
		this.asssertGetTags(tags);

	};


	private void asssertGetTags(final ArrayList<Tag> tags) {

		this.assertNotEmpty(tags);
		for (final Tag tag : tags) {
			this.assertNameSet(tag);
			this.assertUrlSet(tag);
			this.assertCountSet(tag);
		}
	}


	/**
	 * Test {@link Album#removeTag(String, String, String, String)}, this is actually performed by
	 * {@link AlbumTest#testAddRemoveGetTags()}.
	 */
	@Test
	public final void testRemoveTag() {

		this.assertMissingParameter(
				Album.removeTag(null, null, null, null),
				LastfmWebServicesException.INVALID_PARAMETERS,
				"Invalid parameters - Your request is missing the [artist] parameter");
	}


	/**
	 * Test {@link Album#search}.
	 */
	@Test
	public final void testSearch() throws LastfmWebServicesException {

		this.assertMissingParameter(
				Album.search(null, null, null),
				LastfmWebServicesException.INVALID_PARAMETERS,
				"Invalid parameters - Your request is missing the [album] parameter");
		final PaginatedResult<List<Album>> result =
				AbstractModelTest.getWs().fetch(
						Album.search(AbstractModelTest.TEST_ALBUM, AbstractModelTest.TEST_LIMIT, 2));
		this.assertPaginatedResult(result);
		final List<Album> albums = result.getEntries();
		this.assertNotEmpty(albums);
		boolean mbidFound = false;
		for (final Album album : albums) {
			this.assertNameSet(album);
			this.assertIdSet(album);
			this.assertUrlSet(album);
			this.assertImagesSet(album);
			this.assertStreamableSet(album);
			this.assertMbidNullOrNotEmpty(album);
			if (album.getMbid() != null) {
				mbidFound = true;
			}
			final Artist artist = album.getArtist();
			this.assertNameSet(artist);
		}
		this.assertTrue(mbidFound);
	};


	/**
	 * Test {@link Album#share}.
	 */
	@Test
	@Ignore("This test cannot be executed automatically")
	public final void testShare() throws LastfmWebServicesException {

		this.assertMissingParameter(
				Album.share(null, null, null, null, null, null),
				LastfmWebServicesException.INVALID_PARAMETERS,
				"Invalid parameters - Your request is missing a required parameter");

		Assume.assumeTrue(AbstractModelTest.getTestConf().runManipulatingAuthTests());

		final Session session = AbstractModelTest.getSession();

		AbstractModelTest.getWs().execute(
				Album.share(
						AbstractModelTest.TEST_ARTIST,
						AbstractModelTest.TEST_ALBUM,
						Collections.singletonList(AbstractModelTest.getTestConf().getUser()),
						false,
						"This is a test sharing message.",
						session.getKey()));
		System.err.println("CHECK MANUALLY THAT THE USER "
				+ AbstractModelTest.getTestConf().getUser()
				+ " HAS RECEIVED A RECOMMENDATION FOR "
				+ AbstractModelTest.TEST_ARTIST
				+ " - "
				+ AbstractModelTest.TEST_ALBUM);
	};

}
