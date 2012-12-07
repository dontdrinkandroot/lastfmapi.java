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

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import net.dontdrinkandroot.lastfm.api.LastfmWebServicesException;
import net.dontdrinkandroot.lastfm.api.model.xspf.XspfLink;
import net.dontdrinkandroot.lastfm.api.model.xspf.XspfPlaylist;
import net.dontdrinkandroot.lastfm.api.model.xspf.XspfTrack;
import net.dontdrinkandroot.utils.ISO_639_1;
import net.dontdrinkandroot.utils.lang.time.DateUtils;

import org.junit.Assume;
import org.junit.Test;
import org.xml.sax.SAXException;


public class RadioTest extends AbstractModelTest {

	/**
	 * Test {@link Radio#getPlaylist}.
	 * 
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	@Test
	public final void testGetPlaylist() throws LastfmWebServicesException, IOException, SAXException,
			ParserConfigurationException {

		Assume.assumeTrue(AbstractModelTest.getTestConf().runAuthTests());

		this.assertMissingParameter(
				Radio.getPlaylist(null, null, null, null, null, null),
				LastfmWebServicesException.INVALID_SESSIONKEY,
				"Invalid session key - Please re-authenticate");

		final String user = AbstractModelTest.getTestConf().getUser();
		final URI stationUri = Station.getLibraryStationUri(user);

		final Session session =
				AbstractModelTest.getWs().fetch(
						Auth.getMobileSession(
								user,
								Auth.generateMobileToken(user, AbstractModelTest.getTestConf().getPassword())));

		final Station station =
				AbstractModelTest.getWs().execute(Radio.tune(stationUri, ISO_639_1.DE, session.getKey()));

		final XspfPlaylist playlist =
				AbstractModelTest.getWs().fetch(Radio.getPlaylist(session.getKey(), null, null, null, null, null));

		this.assertStringSet(playlist.getTitle());
		this.assertEquals("Last.fm", playlist.getCreator());
		this.assertTrue(System.currentTimeMillis() - DateUtils.MILLIS_PER_DAY < playlist.getDate().getTime());
		this.assertEquals(1, playlist.getLinks().size());
		final XspfLink expLink = playlist.getLinks().iterator().next();
		this.assertEquals("http://www.last.fm/expiry", expLink.getRel());
		this.assertTrue(Integer.parseInt(expLink.getContent()) > 0);
		final List<XspfTrack> tracks = playlist.getTracks();
		this.assertNotNull(tracks);
		this.assertTrue(tracks.size() > 0);
		for (final XspfTrack track : tracks) {
			final List<URI> locations = track.getLocations();
			this.assertEquals(1, locations.size());
			final URI firstLocation = locations.iterator().next();
			this.assertTrue(firstLocation.toString().startsWith("http://play.last.fm/"));
			this.assertStringSet(track.getTitle());
		}
	};


	/**
	 * Test {@link Radio#tune}.
	 * 
	 * @return
	 * @throws MalformedURLException
	 */
	@Test
	public final void testTune() throws LastfmWebServicesException, MalformedURLException {

		Assume.assumeTrue(AbstractModelTest.getTestConf().runAuthTests());

		this.assertMissingParameter(
				Radio.tune(null, null, null),
				LastfmWebServicesException.INVALID_PARAMETERS,
				"Invalid parameters - Your request is missing a required parameter");

		final String user = AbstractModelTest.getTestConf().getUser();
		final URI stationUri = Station.getLibraryStationUri(user);

		final Session session =
				AbstractModelTest.getWs().fetch(
						Auth.getMobileSession(
								user,
								Auth.generateMobileToken(user, AbstractModelTest.getTestConf().getPassword())));

		final Station station =
				AbstractModelTest.getWs().execute(Radio.tune(stationUri, ISO_639_1.DE, session.getKey()));

		this.assertStringNullOrNotEmpty(station.getType());
		this.assertStringNullOrNotEmpty(station.getName());
		this.assertNotNull(station.getUrl());
		this.assertNotNull(station.getSupportsDiscovery());

		this.assertEquals("user", station.getType());
		this.assertEquals(user + "’ Musiksammlung", station.getName());
		this.assertEquals(new URL("http://www.last.fm/listen/user/" + user + "/personal"), station.getUrl());
		this.assertEquals(new Boolean(true), station.getSupportsDiscovery());
	};

}
