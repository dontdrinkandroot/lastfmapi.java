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
package net.dontdrinkandroot.lastfm.api.ws;



public class DefaultLastfmWebservicesTest {

	// @Test
	// public void testCacheException() throws ParserConfigurationException, FileNotFoundException,
	// IOException {
	//
	// final TestConf testConf = AbstractModelTest.loadTestConf();
	//
	// final DefaultLastfmWebServices ws =
	// new DefaultLastfmWebServices(testConf.getKey(), testConf.getSecret(), new MemoryCache());
	// final Properties ttlProperties = new Properties();
	// ttlProperties.setProperty("exception", "100000");
	// ws.setTimeToLive(ttlProperties);
	// for (int i = 0; i < 10; i++) {
	// try {
	// ws.fetch(User.getInfo("adsjfaslkfjhdfpgiahepgoiugoishdgosaidowiajffe"));
	// throw new RuntimeException("Exception expected");
	// } catch (final LastfmWebServicesException e) {
	// /* Expected */
	// }
	// }
	// Assert.assertEquals(1, cache.getStatistics().getCacheMisses());
	// Assert.assertEquals(9, cache.getStatistics().getCacheHits());
	// }

}
//
// private Cache<Serializable, Serializable> cache;
// private TestConf authInfo;
// private DefaultLastfmWebServices ws;
//
// @SuppressWarnings("unchecked")
// @Before
// public final void setUp() throws ParserConfigurationException {
// ApplicationContext context = new ClassPathXmlApplicationContext(
// new String[] {
// "spring/dummycache.xml",
// "spring/auth.xml"
// });
// this.cache = (Cache<Serializable, Serializable>) context.getBean("cache");
// String key = (String) context.getBean("apikey");
// String secret = (String) context.getBean("apisecret");
// this.ws = new DefaultLastfmWebServices(
// key,
// secret,
// this.cache
// );
// }
//
// public Cache<Serializable, Serializable> getCache() {
// return cache;
// }
//
// public DefaultLastfmWebServices getWs() {
// return ws;
// }
//
// @Test
// public void testFetch() throws Exception {
// getCache().resetStats();
//
// /* Fetch valid query */
// Artist artist = getWs().fetch(Artist.getInfo("東京スカパラダイスオーケストラ", null, null, null));
// Assert.assertNotNull(artist);
// Assert.assertEquals(artist.getName(), "東京スカパラダイスオーケストラ");
//
// /* Test cache */
// artist = getWs().fetch(Artist.getInfo("東京スカパラダイスオーケストラ", null, null, null));
// Assert.assertEquals(getCache().getCacheHits(), 1);
// Assert.assertEquals(getCache().getCacheMisses(), 1);
// getCache().resetStats();
//
// /* Fetch invalid query */
// User user;
// try {
// user = getWs().fetch(User.getInfo("shoxrocksdffffffffffffffff"));
// throw new Exception("WebServicesException expected");
// } catch (LastfmWebServicesException e) {
// Assert.assertEquals(e.getCode(), 6);
// Assert.assertEquals(e.getMessage(), "No user with that name was found");
// }
//
// /* Test cache */
// user = getWs().fetch(User.getInfo("shoxrocks"));
// Assert.assertEquals(getCache().getCacheHits(), 0);
// Assert.assertEquals(getCache().getCacheMisses(), 2);
// getCache().resetStats();
// }
//
// @Test
// public void testFetchNextPage() throws LastfmWebServicesException {
// getCache().resetStats();
//
// /* Fetch paginated query */
// PaginatedGetQuery<User> query = User.getFriends("last.hq", null, null);
// List<User> friends = getWs().fetchNextPage(query);
// Assert.assertNotNull(friends);
// Assert.assertTrue(query.getTotal() > 2);
// Assert.assertEquals(query.getPerPage(), 50);
// Assert.assertEquals(query.getPage(), 1);
// Assert.assertTrue(query.getTotalPages() > 2);
//
// /* Fetch next page */
// friends = getWs().fetchNextPage(query);
// Assert.assertNotNull(friends);
// Assert.assertTrue(query.getTotal() > 2);
// Assert.assertEquals(query.getPerPage(), 50);
// Assert.assertEquals(query.getPage(), 2);
// Assert.assertTrue(query.getTotalPages() > 2);
// query.reset();
//
// /* Test cache */
// friends = getWs().fetchNextPage(query);
// Assert.assertNotNull(friends);
// Assert.assertEquals(query.getPage(), 1);
// friends = getWs().fetchNextPage(query);
// Assert.assertEquals(query.getPage(), 2);
//
// Assert.assertEquals(getCache().getCacheHits(), 2);
// Assert.assertEquals(getCache().getCacheMisses(), 2);
// getCache().resetStats();
// }
//
// @Test
// public void testFetchAll() throws LastfmWebServicesException {
// getCache().resetStats();
//
// List<User> friends = getWs().fetchAll(User.getFriends("last.hq", null, null));
// Assert.assertNotNull(friends);
// Assert.assertTrue(friends.size() > 1000);
//
// Assert.assertEquals(getCache().getCacheHits(), 0);
// Assert.assertTrue(getCache().getCacheMisses() > 20);
// }
//
// @Test
// public void testCustomExpiration() throws LastfmWebServicesException {
// getCache().resetStats();
//
// Properties properties = new Properties();
// properties.setProperty("user.getinfo", new Long(1000L * 60L * 60L).toString());
//
// User user;
//
// /* Test cache */
// user = getWs().fetch(User.getInfo("shoxrocks"));
// Assert.assertEquals(getCache().getCacheHits(), 0);
// Assert.assertEquals(getCache().getCacheMisses(), 1);
//
// user = getWs().fetch(User.getInfo("shoxrocks"));
// Assert.assertEquals(getCache().getCacheHits(), 0);
// Assert.assertEquals(getCache().getCacheMisses(), 2);
//
// getCache().resetStats();
// getWs().setTimeToLive(properties);
//
// user = getWs().fetch(User.getInfo("shoxrocks"));
// Assert.assertEquals(getCache().getCacheHits(), 0);
// Assert.assertEquals(getCache().getCacheMisses(), 1);
//
// user = getWs().fetch(User.getInfo("shoxrocks"));
// Assert.assertEquals(getCache().getCacheHits(), 1);
// Assert.assertEquals(getCache().getCacheMisses(), 1);
// }
//
// }
