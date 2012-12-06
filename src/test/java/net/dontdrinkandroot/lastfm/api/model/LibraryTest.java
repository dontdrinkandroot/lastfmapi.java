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

import java.util.List;

import net.dontdrinkandroot.lastfm.api.LastfmWebServicesException;
import net.dontdrinkandroot.lastfm.api.model.paginatedresult.PaginatedResult;

import org.junit.Assume;
import org.junit.Test;


public class LibraryTest extends AbstractModelTest {

	/**
	 * Test {@link Library#addAlbum}, actually performed by
	 * {@link LibraryTest#testLibraryAddRemoveAlbum()}.
	 */
	// @Test
	// public final void testLibraryAddAlbum() throws LastfmWebServicesException {
	//
	// this.assertMissingParameter(
	// Library.addAlbum(null, null, null),
	// LastfmWebServicesException.INVALID_PARAMETERS,
	// "bla");
	// };

	/**
	 * Test {@link Library#getAlbums}.
	 */
	// @Test
	// public final void testLibraryGetAlbums() throws LastfmWebServicesException {
	//
	// this.assertMissingParameter(
	// Library.getAlbums(null, null, null, null),
	// LastfmWebServicesException.INVALID_PARAMETERS,
	// "bla");
	// };

	/**
	 * Test {@link Library#removeAlbum}, actually performed by
	 * {@link LibraryTest#testLibraryAddRemoveAlbum()}.
	 */
	// @Test
	// public final void testLibraryRemoveAlbum() throws LastfmWebServicesException {
	//
	// this.assertMissingParameter(
	// Library.removeAlbum(null, null, null),
	// LastfmWebServicesException.INVALID_PARAMETERS,
	// "bla");
	// };

	@Test
	public void testLibraryAddRemoveAlbum() throws LastfmWebServicesException {

		Assume.assumeTrue(AbstractModelTest.getTestConf().runManipulatingAuthTests());

		final Session session = AbstractModelTest.getSession();

		AbstractModelTest.getWs().execute(
				Library.addAlbum(AbstractModelTest.DUMMY_ARTIST, AbstractModelTest.DUMMY_ALBUM, session.getKey()));

		PaginatedResult<List<Album>> result =
				AbstractModelTest.getWs().fetch(
						Library.getAlbums(
								AbstractModelTest.getTestConf().getUser(),
								AbstractModelTest.DUMMY_ARTIST,
								null,
								null));
		this.assertEquals(1, result.getTotal());

		AbstractModelTest.getWs().execute(
				Library.removeAlbum(AbstractModelTest.DUMMY_ARTIST, AbstractModelTest.DUMMY_ALBUM, session.getKey()));

		result =
				AbstractModelTest.getWs().fetch(
						Library.getAlbums(
								AbstractModelTest.getTestConf().getUser(),
								AbstractModelTest.DUMMY_ARTIST,
								null,
								null));
		this.assertEquals(0, result.getTotal());
	}


	/**
	 * Test {@link Library#addArtist}, actually performed by
	 * {@link LibraryTest#testLibraryAddRemoveArtist()}.
	 */
	// @Test
	// public final void testLibraryAddArtist() throws LastfmWebServicesException {
	//
	// this.assertMissingParameter(Library.addArtist(null, null),
	// LastfmWebServicesException.INVALID_PARAMETERS, "bla");
	// };

	/**
	 * Test {@link Library#getArtists}.
	 */
	// @Test
	// public final void testLibraryGetArtists() throws LastfmWebServicesException {
	//
	// this.assertMissingParameter(
	// Library.getArtists(null, null, null),
	// LastfmWebServicesException.INVALID_PARAMETERS,
	// "Invalid parameters - Your request is missing a required parameter");
	//
	// PaginatedResult<List<Artist>> paginatedArtists =
	// AbstractModelTest.getWs().fetch(Library.getArtists(AbstractModelTest.TEST_USER, null, null));
	//
	// this.assertEquals(1, paginatedArtists.getPage());
	// this.assertEquals(50, paginatedArtists.getPerPage());
	// this.assertTrue(paginatedArtists.getTotalPages() > 0);
	// this.assertTrue(paginatedArtists.getTotal() > 0);
	//
	// this.assertNotNull(paginatedArtists.getEntries());
	// this.assertTrue(paginatedArtists.getEntries().size() > 0);
	// for (final Artist artist : paginatedArtists.getEntries()) {
	// // ArtistTest.assertSimpleArtist(artist);
	// // TODO: reimplement testsimpleartist
	// this.assertImagesSet(artist);
	// this.assertTrue(artist.getPlayCount() > 0);
	// this.assertTrue(artist.getTagCount() >= 0);
	// }
	//
	// paginatedArtists =
	// AbstractModelTest.getWs().fetch(Library.getArtists(AbstractModelTest.TEST_USER, null, 2));
	//
	// this.assertEquals(2, paginatedArtists.getPage());
	// this.assertEquals(50, paginatedArtists.getPerPage());
	// this.assertTrue(paginatedArtists.getTotalPages() > 0);
	// this.assertTrue(paginatedArtists.getTotal() > 0);
	//
	// this.assertNotNull(paginatedArtists.getEntries());
	// this.assertTrue(paginatedArtists.getEntries().size() > 0);
	// for (final Artist artist : paginatedArtists.getEntries()) {
	// // ArtistTest.assertSimpleArtist(artist);
	// // TODO: reimplement test simple artist
	// this.assertImagesSet(artist);
	// this.assertTrue(artist.getPlayCount() > 0);
	// this.assertTrue(artist.getTagCount() >= 0);
	// }
	//
	// /* TODO: Test pagination parameters */
	// };

	/**
	 * Test {@link Library#removeArtist}, actually performed by
	 * {@link LibraryTest#testLibraryAddRemoveArtist()}.
	 */
	// @Test
	// public final void testLibraryRemoveArtist() throws LastfmWebServicesException {
	//
	// this.assertMissingParameter(
	// Library.removeArtist(null, null),
	// LastfmWebServicesException.INVALID_PARAMETERS,
	// "bla");
	// };

	// @Test
	// public void testLibraryAddRemoveArtist() throws LastfmWebServicesException {
	//
	// Assume.assumeTrue(AbstractModelTest.getTestConf().runManipulatingAuthTests());
	//
	// final Session session = AbstractModelTest.getSession();
	//
	// AbstractModelTest.getWs().execute(Library.addArtist(AbstractModelTest.DUMMY_ARTIST,
	// session.getKey()));
	//
	// // TODO: this won't work
	// IPaginatedResult<List<Artist>> result =
	// AbstractModelTest.getWs().fetch(
	// Library.getArtists(AbstractModelTest.getTestConf().getUser(), null, null));
	// this.assertEquals(1, result.getTotal());
	//
	// AbstractModelTest.getWs().execute(Library.removeArtist(AbstractModelTest.DUMMY_ARTIST,
	// session.getKey()));
	//
	// // TODO: this won't work
	// result =
	// AbstractModelTest.getWs().fetch(
	// Library.getArtists(AbstractModelTest.getTestConf().getUser(), null, null));
	// this.assertEquals(0, result.getTotal());
	// }

	/**
	 * Test {@link Library#addTrack}, actually performed by
	 * {@link LibraryTest#testLibraryAddRemoveTrack()}.
	 */
	// @Test
	// public final void testLibraryAddTrack() throws LastfmWebServicesException {
	//
	// this.assertMissingParameter(
	// Library.addTrack(null, null, null),
	// LastfmWebServicesException.INVALID_PARAMETERS,
	// "bla");
	// };

	// /**
	// * Test {@link Library#getTracks}.
	// */
	// @Test
	// public final void testLibraryGetTracks() throws LastfmWebServicesException {
	//
	// this.assertMissingParameter(
	// Library.getTracks(null, null, null, null, null),
	// LastfmWebServicesException.INVALID_PARAMETERS,
	// "bla");
	// };

	/**
	 * Test {@link Library#removeTrack}, actually performed by
	 * {@link LibraryTest#testLibraryAddRemoveTrack()}.
	 */
	// @Test
	// public final void testLibraryRemoveTrack() throws LastfmWebServicesException {
	//
	// this.assertMissingParameter(
	// Library.removeTrack(null, null, null),
	// LastfmWebServicesException.INVALID_PARAMETERS,
	// "bla");
	// };

	@Test
	public void testLibraryAddRemoveTrack() throws LastfmWebServicesException {

		Assume.assumeTrue(AbstractModelTest.getTestConf().runManipulatingAuthTests());

		final Session session = AbstractModelTest.getSession();

		AbstractModelTest.getWs().execute(
				Library.addTrack(AbstractModelTest.DUMMY_ARTIST, AbstractModelTest.DUMMY_TRACK, session.getKey()));

		PaginatedResult<List<Track>> result =
				AbstractModelTest.getWs().fetch(
						Library.getTracks(
								AbstractModelTest.getTestConf().getUser(),
								AbstractModelTest.DUMMY_ARTIST,
								null,
								null,
								null));
		this.assertEquals(1, result.getTotal());

		AbstractModelTest.getWs().execute(
				Library.removeTrack(AbstractModelTest.DUMMY_ARTIST, AbstractModelTest.DUMMY_TRACK, session.getKey()));

		result =
				AbstractModelTest.getWs().fetch(
						Library.getTracks(
								AbstractModelTest.getTestConf().getUser(),
								AbstractModelTest.DUMMY_ARTIST,
								null,
								null,
								null));
		this.assertEquals(0, result.getTotal());
	}

}
