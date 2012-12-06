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

import org.junit.Test;


public class TrackTest extends AbstractModelTest {

	// /**
	// * Test {@link Track#addTags}.
	// */
	// @Test
	// public final void testTrackAddTags() throws LastfmWebServicesException {
	// Object result = getWs().fetch(Track.addTags(null, null, null));
	// };
	//
	// /**
	// * Test {@link Track#ban}.
	// */
	// @Test
	// public final void testTrackBan() throws LastfmWebServicesException {
	// Object result = getWs().fetch(Track.ban(null, null));
	// };
	//
	// /**
	// * Test {@link Track#getBuylinks}.
	// */
	// @Test
	// public final void testTrackGetBuylinks() throws LastfmWebServicesException {
	// Object result = getWs().fetch(Track.getBuylinks(null, null, null, null));
	// };
	//
	// /**
	// * Test {@link Track#getFingerprintMetadata}.
	// */
	// @Test
	// public final void testTrackGetFingerprintMetadata() throws LastfmWebServicesException {
	// Object result = getWs().fetch(Track.getFingerprintMetadata(null));
	// };

	/**
	 * Test {@link Track#getInfo}.
	 */
	@Test
	public final void testTrackGetInfo() throws LastfmWebServicesException {

		this.assertMissingParameter(
				Track.getInfo(null, null, null, null),
				LastfmWebServicesException.INVALID_PARAMETERS,
				"You must supply either a track & artist name or a track mbid.");

		final Track track =
				AbstractModelTest.getWs().fetch(
						Track.getInfo(
								AbstractModelTest.TEST_ARTIST,
								AbstractModelTest.TEST_TRACK,
								null,
								AbstractModelTest.TEST_USER));
		this.assertIdSet(track);
		this.assertName(track, AbstractModelTest.TEST_TRACK);
		this.assertMbidNullOrNotEmpty(track);
		this.assertUrlSet(track);
		this.assertDurationSet(track);
		this.assertStreamableSet(track);
		this.assertListenersSet(track);
		this.assertPlayCountSet(track);
		this.assertUserPlayCountSet(track);

		// TODO: test toptags
		// TODO: test wiki
		// TODO: test artist
		// TODO: test album
	};


	/**
	 * Test {@link Track#getSimilar}.
	 */
	@Test
	public final void testTrackGetSimilar() throws LastfmWebServicesException {

		this.assertMissingParameter(
				Track.getSimilar(null, null, null, null, null),
				LastfmWebServicesException.INVALID_PARAMETERS,
				"You must supply either a track & artist name or a track mbid.");

		List<Track> tracks =
				AbstractModelTest.getWs().fetch(
						Track.getSimilar(AbstractModelTest.TEST_TRACK, AbstractModelTest.TEST_ARTIST, null, null, null));

		this.assertTrue(tracks.size() > 0);
		boolean streamableFound = false;
		boolean playcountFound = false;
		for (final Track track : tracks) {
			// TrackTest.assertSimpleTrack(track);
			// TODO reimplement testsimpletrack
			this.assertTrue(track.getMatch() >= 0.0f && track.getMatch() <= 1.0f);
			if (track.getPlayCount() > 0) {
				playcountFound = true;
			}
			this.assertTrue(track.getDuration() > 0);
			if (track.getStreamable().isStreamable()) {
				streamableFound = true;
			}
		}
		this.assertTrue(streamableFound);
		this.assertTrue(playcountFound);

		tracks =
				AbstractModelTest.getWs().fetch(
						Track.getSimilar(AbstractModelTest.TEST_TRACK, AbstractModelTest.TEST_ARTIST, null, null, 10));

		this.assertEquals(tracks.size(), 10);
		streamableFound = false;
		playcountFound = false;
		for (final Track track : tracks) {
			// TrackTest.assertSimpleTrack(track);
			// TODO: reimplement test simple track
			this.assertTrue(track.getMatch() >= 0.0f && track.getMatch() <= 1.0f);
			if (track.getPlayCount() > 0) {
				playcountFound = true;
			}
			this.assertTrue(track.getDuration() > 0);
			if (track.getStreamable().isStreamable()) {
				streamableFound = true;
			}
		}
		this.assertTrue(streamableFound);
		this.assertTrue(playcountFound);

		// TODO: Check by mbid
	}

	// /**
	// * Test {@link Track#getTags}.
	// */
	// @Test
	// public final void testTrackGetTags() throws LastfmWebServicesException {
	// Object result = getWs().fetch(Track.getTags(null, null));
	// };
	//
	// /**
	// * Test {@link Track#getTopFans}.
	// */
	// @Test
	// public final void testTrackGetTopFans() throws LastfmWebServicesException {
	// Object result = getWs().fetch(Track.getTopFans(null, null, null));
	// };
	//
	// /**
	// * Test {@link Track#getTopTags}.
	// */
	// @Test
	// public final void testTrackGetTopTags() throws LastfmWebServicesException {
	//
	// getCache().resetStats();
	//
	// /* Test empty query */
	// try {
	// getWs().fetch(Track.getTopTags(null, null, null));
	// throw new RuntimeException("Exception was expected");
	// } catch (LastfmWebServicesException e) {
	// assertEquals(
	// "You must supply either a track & artist name or an mbid.",
	// e.getMessage()
	// );
	// assertEquals(LastfmWebServicesException.INVALID_PARAMETERS, e.getCode());
	// }
	//
	// final String artist = "Deftones";
	// final String track = "Knife Prty";
	// final String mbid = "1471b26e-50aa-4b74-8257-e57040faca1e";
	//
	// List<Tag> topTags = getWs().fetch(Track.getTopTags(track, artist, null));
	// assertNotNull(topTags);
	// assertTrue(topTags.size() > 0);
	//
	// for (Tag topTag : topTags) {
	// assertStringSet(topTag.getName());
	// assertNotNull(topTag.getUrl());
	// assertTrue(topTag.getCount() >= 0 && topTag.getCount() <= 100);
	// }
	//
	// /*
	// * Track MBIDs are badly supported so we skip this test.
	// * List<Tag> topTags = getWs().fetch(Track.getTopTags(null, null, mbid));
	// */
	//
	// /* Check cache */
	// assertCache(2, 0);
	//
	// };
	//
	// /**
	// * Test {@link Track#love}.
	// */
	// @Test
	// public final void testTrackLove() throws LastfmWebServicesException {
	// Object result = getWs().fetch(Track.love(null, null));
	// };
	//
	// /**
	// * Test {@link Track#removeTag}.
	// */
	// @Test
	// public final void testTrackRemoveTag() throws LastfmWebServicesException {
	// Object result = getWs().fetch(Track.removeTag(null, null, null));
	// };
	//
	// /**
	// * Test {@link Track#search}.
	// */
	// @Test
	// public final void testTrackSearch() throws LastfmWebServicesException {
	// Object result = getWs().fetch(Track.search(null, null, null, null));
	// };
	//
	// /**
	// * Test {@link Track#share}.
	// */
	// @Test
	// public final void testTrackShare() throws LastfmWebServicesException {
	// Object result = getWs().fetch(Track.share(null, null, null));
	// }

}
