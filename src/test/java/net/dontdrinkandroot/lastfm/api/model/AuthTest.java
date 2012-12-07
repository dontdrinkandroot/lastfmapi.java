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

import net.dontdrinkandroot.lastfm.api.LastfmWebServicesException;

import org.junit.Assume;
import org.junit.Test;


public class AuthTest extends AbstractModelTest {

	/**
	 * Test {@link Auth#getMobileSession}.
	 */
	@Test
	public final void testGetMobileSession() throws LastfmWebServicesException {

		Assume.assumeTrue(AbstractModelTest.getTestConf().runAuthTests());

		this.assertMissingParameter(
				Auth.getMobileSession(null, null),
				LastfmWebServicesException.INVALID_TOKEN,
				"Invalid username. No last.fm account associated with that name.");

		final Session session =
				AbstractModelTest.getWs().fetch(
						Auth.getMobileSession(AbstractModelTest.getTestConf().getUser(), Auth.generateMobileToken(
								AbstractModelTest.getTestConf().getUser(),
								AbstractModelTest.getTestConf().getPassword())));

		this.assertEquals(AbstractModelTest.getTestConf().getUser(), session.getUsername());
		this.assertStringSet(session.getKey());
		this.assertEquals(32, session.getKey().length());
		this.assertEquals(AbstractModelTest.getTestConf().isSubscriber(), session.isSubscriber());
	};

	// /**
	// * Test {@link Auth#getSession}.
	// */
	// @Test
	// public final void testAuthGetSession() throws LastfmWebServicesException {
	// Object result = getWs().fetch(Auth.getSession(null));
	// };
	//
	// /**
	// * Test {@link Auth#getToken}.
	// */
	// @Test
	// public final void testAuthGetToken() throws LastfmWebServicesException {
	// Object result = getWs().fetch(Auth.getToken());
	// };

}
