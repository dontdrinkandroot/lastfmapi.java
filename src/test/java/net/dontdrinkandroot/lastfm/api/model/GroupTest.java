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
import net.dontdrinkandroot.lastfm.api.model.paginatedresult.GenericPaginatedResult;

import org.junit.Test;


public class GroupTest extends AbstractModelTest {

	/**
	 * Test {@link Group#getHype}.
	 */
	// @Test
	// public final void testGroupGetHype() throws LastfmWebServicesException {
	// Object result = getWs().fetch(Group.getHype(null));
	// };

	/**
	 * Test {@link Group#getMembers}.
	 */
	@Test
	public final void testGetMembers() throws LastfmWebServicesException {

		this.assertMissingParameter(
				Group.getMembers(null, null, null),
				LastfmWebServicesException.INVALID_PARAMETERS,
				"Invalid parameters - Your request is missing the [group] parameter");

		/* Fetch first Page */
		final GenericPaginatedResult<List<User>> result =
				AbstractModelTest.getWs().fetch(
						Group.getMembers(AbstractModelTest.TEST_ARTIST, AbstractModelTest.TEST_LIMIT, 2));

		this.assertPaginatedResult(result);
		final List<User> users = result.getEntries();
		this.assertNotEmpty(users);
		boolean realnameFound = false;
		boolean imagesFound = false;
		for (final User user : users) {
			this.assertNameSet(user);
			this.assertUrlSet(user);
			if (this.hasImages(user)) {
				imagesFound = true;
			}
			if (this.hasRealName(user)) {
				realnameFound = true;
			}
		}
		this.assertTrue(realnameFound);
		this.assertTrue(imagesFound);
	};

	/**
	 * Test {@link Group#getWeeklyAlbumChart}.
	 */
	// @Test
	// public final void testGroupGetWeeklyAlbumChart() throws LastfmWebServicesException {
	// Object result = getWs().fetch(Group.getWeeklyAlbumChart(null, null, null));
	// };

	/**
	 * Test {@link Group#getWeeklyArtistChart}.
	 */
	// @Test
	// public final void testGroupGetWeeklyArtistChart() throws LastfmWebServicesException {
	// Object result = getWs().fetch(Group.getWeeklyArtistChart(null, null, null));
	// };

	/**
	 * Test {@link Group#getWeeklyChartList}.
	 */
	// @Test
	// public final void testGroupGetWeeklyChartList() throws LastfmWebServicesException {
	// Object result = getWs().fetch(Group.getWeeklyChartList(null));
	// };

	/**
	 * Test {@link Group#getWeeklyTrackChart}.
	 */
	// @Test
	// public final void testGroupGetWeeklyTrackChart() throws LastfmWebServicesException {
	// Object result = getWs().fetch(Group.getWeeklyTrackChart(null, null, null));
	// };

}
