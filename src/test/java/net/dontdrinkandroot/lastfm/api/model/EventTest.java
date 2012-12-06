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
//
//import static org.junit.Assert.*;
//
//import java.util.List;
//
//import net.dontdrinkandroot.lastfm.api.LastfmWebServicesException;
//
//import org.junit.Test;
//
public class EventTest extends AbstractModelTest {
//
//	/**
//	 * Test {@link Event#attend}.
//	 */
//	@Test
//	public final void testEventAttend() throws LastfmWebServicesException {
//		Object result = getWs().fetch(Event.attend(null, null));
//	};
//
//	/**
//	 * Test {@link Event#getAttendees}.
//	 */
//	@Test
//	public final void testEventGetAttendees() throws LastfmWebServicesException {
//		
//		assertMissingParameter(
//				Event.getAttendees(null),
//				LastfmWebServicesException.INVALID_PARAMETERS,
//				"Invalid parameters - Your request is missing a required parameter"
//			);
//		
//		/* Normal query */
//		final Long eventId = 328799L;
//		List<User> attendees = getWs().fetch(Event.getAttendees(eventId));
//		assertNotNull(attendees);
//		assertTrue(attendees.size() > 0);
//		boolean realnameFound = false;
//		boolean imagesFound = false;
//		for (User user : attendees) {
//			assertStringSet(user.getName());
//			assertStringNullOrNotEmpty(user.getRealname());
//			if (user.getRealname() != null) {
//				realnameFound = true;
//			}
//			assertNotNull(user.getUrl());
//			if (assertImages(user.getImages())) {
//				imagesFound = true;
//			}
//		}
//		assertTrue(realnameFound);
//		assertTrue(imagesFound);
//		
//		assertCache(2, 0);
//	};
//
//	/**
//	 * Test {@link Event#getInfo}.
//	 */
//	@Test
//	public final void testEventGetInfo() throws LastfmWebServicesException {
//		Object result = getWs().fetch(Event.getInfo(null));
//	};
//
//	/**
//	 * Test {@link Event#getShouts}.
//	 */
//	@Test
//	public final void testEventGetShouts() throws LastfmWebServicesException {
//		Object result = getWs().fetch(Event.getShouts(null));
//	};
//
//	/**
//	 * Test {@link Event#share}.
//	 */
//	@Test
//	public final void testEventShare() throws LastfmWebServicesException {
//		Object result = getWs().fetch(Event.share(null, null));
//	};
//
//	/**
//	 * Test {@link Event#shout}.
//	 */
//	@Test
//	public final void testEventShout() throws LastfmWebServicesException {
//		Object result = getWs().fetch(Event.shout(null, null));
//	};
//
}
