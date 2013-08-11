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
import java.util.List;

import net.dontdrinkandroot.lastfm.api.LastfmWebServicesException;

import org.junit.Test;


public class TagTest extends AbstractModelTest {

	/**
	 * Test {@link Tag#getSimilar}.
	 */
	@Test
	public final void testTagGetSimilar() throws LastfmWebServicesException {

		this.assertMissingParameter(
				Tag.getSimilar(null),
				LastfmWebServicesException.INVALID_PARAMETERS,
				"Invalid parameters - Your request is missing the [tag] parameter");

		ArrayList<Tag> similarTags = AbstractModelTest.getWs().fetch(Tag.getSimilar("disco"));
		this.assertNotEmpty(similarTags);
		for (Tag tag : similarTags) {
			this.assertNameSet(tag);
			this.assertUrlSet(tag);
			this.assertStreamableSet(tag);
		}
	};


	//
	// /**
	// * Test {@link Tag#getTopAlbums}.
	// */
	// @Test
	// public final void testTagGetTopAlbums() throws LastfmWebServicesException {
	// Object result = getWs().fetch(Tag.getTopAlbums());
	// };
	//
	// /**
	// * Test {@link Tag#getTopArtists}.
	// */
	// @Test
	// public final void testTagGetTopArtists() throws LastfmWebServicesException {
	// Object result = getWs().fetch(Tag.getTopArtists());
	// };
	//

	/**
	 * Test {@link Tag#getTopTags}.
	 */
	@Test
	public final void testGetTopTags() throws LastfmWebServicesException {

		final List<Tag> tags = AbstractModelTest.getWs().fetch(Tag.getTopTags());
		this.assertNotNull(tags);
		this.assertTrue(tags.size() > 0);

		for (final Tag tag : tags) {
			this.assertStringSet(tag.getName());
			this.assertTrue(tag.getCount() > 0);
			this.assertNotNull(tag.getUrl());
		}

	};


	//
	// /**
	// * Test {@link Tag#getTopTracks}.
	// */
	// @Test
	// public final void testTagGetTopTracks() throws LastfmWebServicesException {
	// Object result = getWs().fetch(Tag.getTopTracks());
	// };
	//
	// /**
	// * Test {@link Tag#getWeeklyArtistChart}.
	// */
	// @Test
	// public final void testTagGetWeeklyArtistChart() throws LastfmWebServicesException {
	// Object result = getWs().fetch(Tag.getWeeklyArtistChart(null, null, null, null));
	// };
	//
	// /**
	// * Test {@link Tag#getWeeklyChartList}.
	// */
	// @Test
	// public final void testTagGetWeeklyChartList() throws LastfmWebServicesException {
	// Object result = getWs().fetch(Tag.getWeeklyChartList(null));
	// };
	//
	// /**
	// * Test {@link Tag#search}.
	// */
	// @Test
	// public final void testTagSearch() throws LastfmWebServicesException {
	// Object result = getWs().fetch(Tag.search(null, null, null));
	// }

	@Test
	public final void testGetInfo() throws LastfmWebServicesException {

		this.assertMissingParameter(
				Tag.getInfo(null, null),
				LastfmWebServicesException.INVALID_PARAMETERS,
				"Invalid parameters - Your request is missing the [tag] parameter");

		final Tag tag = AbstractModelTest.getWs().fetch(Tag.getInfo("alternative", null));
		this.assertStringSet(tag.getName());
		this.assertNotNull(tag.getUrl());
		this.assertTrue(tag.getTaggings() > 0);
		this.assertTrue(tag.getReach() > 0);
		this.assertTrue(tag.getStreamable().isStreamable());
		this.assertNotNull(tag.getWiki());
		this.assertStringSet(tag.getWiki().getContent());
		this.assertNotNull(tag.getWiki().getPublished());

		// TODO: Test lang parameter
	}

}
