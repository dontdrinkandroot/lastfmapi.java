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

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Collection;
import java.util.Date;
import java.util.Map.Entry;
import java.util.Properties;

import net.dontdrinkandroot.lastfm.api.LastfmWebServicesException;
import net.dontdrinkandroot.lastfm.api.TestConf;
import net.dontdrinkandroot.lastfm.api.cache.MemoryCache;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.AgeEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.AlbumEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.ArtistEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.AuthorEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.BodyEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.BootstrapEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.BuyLinkEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.CountEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.CountryEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.DateEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.DurationEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.GenderEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.IdEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.ImageEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.IsSearchEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.ListenersEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.MatchEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.MbidEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.NameEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.PlayCountEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.PriceEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.RankEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.RealnameEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.ReleaseDateEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.StreamableEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.SubscriberEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.SupplierIconEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.SupplierNameEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.TitleEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.UrlEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.UserPlayCountEntity;
import net.dontdrinkandroot.lastfm.api.model.paginatedresult.PaginatedResult;
import net.dontdrinkandroot.lastfm.api.queries.AuthenticatedGetQuery;
import net.dontdrinkandroot.lastfm.api.queries.PostQuery;
import net.dontdrinkandroot.lastfm.api.queries.Query;
import net.dontdrinkandroot.lastfm.api.queries.UnauthenticatedGetQuery;
import net.dontdrinkandroot.lastfm.api.ws.DefaultLastfmWebServices;
import net.dontdrinkandroot.lastfm.api.ws.LastfmWebServices;
import net.dontdrinkandroot.lastfm.api.ws.fetcher.DiskBufferedFetcher;
import net.dontdrinkandroot.utils.ISO_3166_1_alpha2;
import net.dontdrinkandroot.utils.lang.StringUtils;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/*
 * TODO: Think about a way to only create a session once for all tests.
 */

public abstract class AbstractModelTest {

	public static final String TEST_USER = "shoxrocks";

	public static final String TEST_USER_2 = "mokele";

	public static final String TEST_ARTIST = "Tool";

	public static final String TEST_ARTIST_MBID = "66fc5bf8-daa4-4241-b378-9bc9077939d2";

	public static final String TEST_ALBUM = "Lateralus";

	public static final String TEST_ALBUM_MBID = "451df9bc-1ba2-453c-9c60-9537a5f3c6e3";

	public static final long TEST_ALBUM_RELEASEDATE_TIMESTAMP = 989884800000L;

	public static final String TEST_TRACK = "Schism";

	public static final String TEST_ARTIST_2 = "Cher";

	public static final String TEST_ALBUM_2 = "Believe";

	public static final String DUMMY_ARTIST = "TestArtist";

	public static final String DUMMY_ALBUM = "TestAlbum";

	public static final String DUMMY_TRACK = "TestTrack";

	public static final String DUMMY_TAG_1 = "tag1";

	public static final String DUMMY_TAG_2 = "tag2";

	public static final String DUMMY_TAG_3 = "tag3";

	public static final int TEST_LIMIT = 20;

	public static final long TIMESTAMP_2005 = 1104537600000L;

	private static Session session;

	private static TestConf testConf;

	private static LastfmWebServices ws;

	private static Logger LOGGER = LoggerFactory.getLogger(AbstractModelTest.class);


	protected static Session getSession() {

		if (AbstractModelTest.session == null) {

			try {

				final TestConf testConf = AbstractModelTest.getTestConf();

				AbstractModelTest.session =
						AbstractModelTest.getWs().fetch(
								Auth.getMobileSession(
										testConf.getUser(),
										Auth.generateMobileToken(testConf.getUser(), testConf.getPassword())

								));

			} catch (final LastfmWebServicesException e) {
				throw new RuntimeException(e);
			}

		}

		return AbstractModelTest.session;
	}


	protected static TestConf getTestConf() {

		if (AbstractModelTest.testConf == null) {
			AbstractModelTest.testConf = AbstractModelTest.loadTestConf();
		}

		return AbstractModelTest.testConf;
	}


	protected static LastfmWebServices getWs() {

		try {

			if (AbstractModelTest.ws == null) {

				final TestConf testConf = AbstractModelTest.getTestConf();

				AbstractModelTest.ws =
						new DefaultLastfmWebServices(
								testConf.getKey(),
								testConf.getSecret(),
								new MemoryCache(),
								new DiskBufferedFetcher());

			}

		} catch (final Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return AbstractModelTest.ws;

	}


	public static TestConf loadTestConf() {

		final String apiKey = System.getProperty("lfm.api.key");
		if (apiKey != null) {

			AbstractModelTest.LOGGER.info("API key found in system properties: Loading test configuration from system properties");
			final TestConf testConf = new TestConf();
			testConf.setKey(apiKey);
			testConf.setSecret(System.getProperty("lfm.api.secret"));
			testConf.setUser(System.getProperty("lfm.api.user.name"));
			testConf.setPassword(System.getProperty("lfm.api.user.password"));
			final String subscriber = System.getProperty("lfm.api.user.subscriber");
			if (subscriber != null) {
				testConf.setSubscriber(Boolean.parseBoolean(subscriber));
			}
			final String authtests = System.getProperty("lfm.api.run.authtests");
			if (authtests != null) {
				testConf.setRunAuthTests(Boolean.parseBoolean(authtests));
			}
			final String posttests = System.getProperty("lfm.api.run.posttests");
			if (posttests != null) {
				testConf.setRunManipulatingAuthTests(Boolean.parseBoolean(posttests));
			}

			return testConf;

		} else {

			final String defaultName = "testconf.properties";
			AbstractModelTest.LOGGER.info(
					"API key not found in system properties: Trying to use {} test configuration on classpath",
					defaultName);
			try {

				final String systemPropertyName = "lfmApiTestConfProperties";

				final String testPropertiesLocation = System.getProperty(systemPropertyName);
				final Properties properties = new Properties();

				if (testPropertiesLocation == null) {
					AbstractModelTest.LOGGER.warn(
							"No system property '{}' found, trying to use '{}' on classpath",
							new Object[] { systemPropertyName, defaultName });
					properties.load(AbstractModelTest.class.getClassLoader().getResourceAsStream(defaultName));
				} else {
					final File propertiesFile = new File(testPropertiesLocation);
					properties.load(new FileInputStream(propertiesFile));
				}

				return new TestConf(properties);

			} catch (final Exception e) {
				throw new RuntimeException(e);
			}

		}
	}


	protected final void assertAge(final AgeEntity ageEntity) {

		Assert.assertNotNull(ageEntity.getAge());
		Assert.assertTrue(ageEntity.getAge() > 0);
	}


	protected void assertAlbum(AlbumEntity albumEntity, String albumName) {

		Assert.assertNotNull(albumEntity.getAlbum());
		Album album = albumEntity.getAlbum();
		Assert.assertEquals(albumName, album.getName());
	}


	protected void assertArtist(ArtistEntity artistEntity, String artistName) {

		Assert.assertNotNull(artistEntity.getArtist());
		Artist artist = artistEntity.getArtist();
		Assert.assertEquals(artistName, artist.getName());
	}


	protected void assertArtistSet(ArtistEntity artistEntity) {

		Assert.assertNotNull(artistEntity.getArtist());
		Artist artist = artistEntity.getArtist();
		this.assertNameSet(artist);
		this.assertMbidSet(artist);
	}


	protected void assertAuthorSet(final AuthorEntity entity) {

		this.assertStringSet(entity.getAuthor());
	}


	protected void assertBodySet(final BodyEntity entity) {

		this.assertStringSet(entity.getBody());
	}


	protected final void assertBootstrap(final BootstrapEntity bootstrapEntity, final Boolean expected) {

		Assert.assertNotNull(bootstrapEntity.isBootstrap());
		Assert.assertEquals(expected, bootstrapEntity.isBootstrap());
	}


	protected void assertBuyLinkSet(final BuyLinkEntity entity) {

		this.assertNotNull(entity.getBuyLink());
	}


	protected void assertCountry(final CountryEntity countryEntity, final ISO_3166_1_alpha2 expectedCountry) {

		Assert.assertNotNull(countryEntity.getCountry());
		Assert.assertEquals(expectedCountry, countryEntity.getCountry());
	}


	protected void assertCountSet(final CountEntity entity) {

		Assert.assertNotNull(entity.getCount());
		Assert.assertTrue(entity.getCount() >= 0);
	}


	protected void assertDate(DateEntity dateEntity, Long secondsTimestamp) {

		this.assertDateSet(dateEntity);
		Date expected = new Date(secondsTimestamp * 1000);
		Assert.assertEquals(expected, dateEntity.getDate());
	}


	protected void assertDateSet(final DateEntity dateEntity) {

		Assert.assertNotNull(dateEntity.getDate());
	}


	protected void assertDurationSet(final DurationEntity durationEntity) {

		Assert.assertNotNull(durationEntity.getDuration());
		Assert.assertTrue(durationEntity.getDuration() > 0);
	}


	protected void assertEquals(final Object expected, final Object actual) {

		Assert.assertEquals(expected, actual);
	}


	protected void assertFalse(final boolean condition) {

		Assert.assertFalse(condition);
	}


	protected void assertGender(final GenderEntity genderEntity, final Gender expectedGender) {

		Assert.assertNotNull(genderEntity.getGender());
		Assert.assertEquals(expectedGender, genderEntity.getGender());
	}


	protected void assertGreaterZero(final Float f) {

		this.assertNotNull(f);
		this.assertTrue(f > 0.0f);
	}


	protected final void assertGreaterZero(final Integer integer) {

		Assert.assertNotNull(integer);
		Assert.assertTrue(integer > 0);
	}


	protected void assertId(final IdEntity idEntity, final Long expectedId) {

		Assert.assertNotNull(idEntity.getId());
		this.assertIdSet(idEntity);
		Assert.assertEquals(expectedId, idEntity.getId());
	}


	protected void assertIdSet(final IdEntity entity) {

		Assert.assertNotNull(entity.getId());
		Assert.assertTrue(entity.getId() > 0);
	}


	protected void assertImagesSet(final ImageEntity entity) {

		boolean imageFound = false;
		Assert.assertNotNull(entity.getImages());
		for (final Entry<ImageSize, URL> image : entity.getImages().entrySet()) {
			Assert.assertNotNull(image.getKey());
			Assert.assertNotNull(image.getValue());
			imageFound = true;
		}

		this.assertTrue(imageFound);
	}


	protected void assertIsSearchSet(final IsSearchEntity entity) {

		this.assertNotNull(entity.isSearch());
	}


	protected void assertListenersSet(final ListenersEntity entity) {

		Assert.assertNotNull(entity.getListeners());
		Assert.assertTrue(entity.getListeners() > 0);
	}


	protected void assertMatchSet(final MatchEntity entity) {

		this.assertNotNull(entity.getMatch());
		this.assertTrue(entity.getMatch() >= 0f);
		this.assertTrue(entity.getMatch() <= 1f);
	}


	protected void assertMbid(final MbidEntity entity, final String mbid) {

		this.assertStringSet(entity.getMbid());
		Assert.assertEquals(mbid, entity.getMbid());
	}


	protected void assertMbidNullOrNotEmpty(final MbidEntity entity) {

		this.assertStringNullOrNotEmpty(entity.getMbid());
	}


	protected void assertMbidSet(final MbidEntity entity) {

		this.assertStringSet(entity.getMbid());
	}


	protected final void assertMissingParameter(final Query<?> query, final int code, final String message) {

		if (query instanceof UnauthenticatedGetQuery<?>) {

			try {
				AbstractModelTest.getWs().fetch((UnauthenticatedGetQuery<?>) query);
				throw new AssertionError("Exception expected");
			} catch (final LastfmWebServicesException e) {
				Assert.assertEquals(code, e.getCode());
				Assert.assertEquals(message, e.getMessage());
			}

		} else if (query instanceof PostQuery<?>) {

			try {
				AbstractModelTest.getWs().execute((PostQuery<?>) query);
				throw new AssertionError("Exception expected");
			} catch (final LastfmWebServicesException e) {
				Assert.assertEquals(code, e.getCode());
				Assert.assertEquals(message, e.getMessage());
			}

		} else if (query instanceof AuthenticatedGetQuery<?>) {

			try {
				AbstractModelTest.getWs().fetch((AuthenticatedGetQuery<?>) query);
				throw new AssertionError("Exception expected");
			} catch (final LastfmWebServicesException e) {
				Assert.assertEquals(code, e.getCode());
				Assert.assertEquals(message, e.getMessage());
			}

		} else {
			// TODO: IMPLEMENT
			throw new RuntimeException("Not Implemented");
		}
	}


	protected void assertName(final NameEntity nameEntity, final String expectedName) {

		Assert.assertNotNull(nameEntity.getName());
		Assert.assertEquals(expectedName, nameEntity.getName());
	}


	protected void assertNameSet(final NameEntity nameEntity) {

		this.assertStringSet(nameEntity.getName());
	}


	protected void assertNotEmpty(final Collection<?> coll) {

		Assert.assertNotNull(coll);
		Assert.assertFalse(coll.isEmpty());
	}


	protected void assertNotNull(final Object o) {

		Assert.assertNotNull(o);
	}


	protected void assertNull(final Object o) {

		Assert.assertNull(o);
	}


	protected void assertPaginatedResult(final PaginatedResult<?> result) {

		this.assertEquals(AbstractModelTest.TEST_LIMIT, result.getPerPage());
		this.assertEquals(2, result.getPage());
		this.assertGreaterZero(result.getTotal());
		this.assertGreaterZero(result.getTotalPages());
	}


	/**
	 * Asserts a paginated result for the second page.
	 */
	protected void assertPaginatedResult(final PaginatedResult<?> result, final Integer expectedPerPage) {

		this.assertEquals(expectedPerPage, result.getPerPage());
		this.assertEquals(2, result.getPage());
		this.assertGreaterZero(result.getTotal());
		this.assertGreaterZero(result.getTotalPages());
	}


	protected final void assertPlayCount(final PlayCountEntity playCountEntity) {

		Assert.assertNotNull(playCountEntity.getPlayCount());
		Assert.assertTrue(playCountEntity.getPlayCount() > 0);
	}


	protected void assertPlayCountEntity(final Album album) {

		Assert.assertTrue(album.getUserPlayCount() > 0);
	}


	protected void assertPlayCountSet(final PlayCountEntity playCountEntity) {

		Assert.assertNotNull(playCountEntity.getPlayCount());
		Assert.assertTrue(playCountEntity.getPlayCount() > 0);
	}


	protected void assertPriceValid(final PriceEntity entity) {

		final Price price = entity.getPrice();
		this.assertNotNull(price);
		this.assertStringSet(price.getCurrency());
		this.assertGreaterZero(price.getAmount());
	}


	protected void assertRankSet(final RankEntity entity) {

		Assert.assertNotNull(entity.getRank());
		Assert.assertTrue(entity.getRank() > 0);
	}


	protected void assertRealname(final RealnameEntity realnameEntity, final String expectedName) {

		Assert.assertNotNull(realnameEntity.getRealname());
		Assert.assertEquals(expectedName, realnameEntity.getRealname());
	}


	protected void assertReleaseDate(final ReleaseDateEntity entity, final long releaseDateTimeStamp) {

		Assert.assertNotNull(entity.getReleaseDate());
		Assert.assertEquals(releaseDateTimeStamp, entity.getReleaseDate().getTime());
	}


	protected boolean assertSimpleTrack(Track track, boolean ignoreDuration, boolean ignorePlayCount) {

		this.assertNameSet(track);
		if (!ignoreDuration) {
			this.assertDurationSet(track);
		}
		this.assertStreamableSet(track);
		if (!ignorePlayCount) {
			this.assertPlayCountSet(track);
		}
		this.assertUrlSet(track);

		Artist artist = track.getArtist();
		this.assertNameSet(artist);
		this.assertUrlSet(artist);
		if (!StringUtils.isEmpty(artist.getMbid())) {
			return true;
		}

		return false;
	}


	protected void assertStreamableSet(final StreamableEntity entity) {

		Assert.assertNotNull(entity.getStreamable());
	}


	protected void assertStringNotEmpty(final String s) {

		Assert.assertFalse(StringUtils.isBlank(s));
	}


	protected void assertStringNullOrNotEmpty(final String string) {

		if (string != null) {
			Assert.assertFalse(string.equals(""));
		}
	}


	protected void assertStringSet(final String string) {

		Assert.assertNotNull(string);
		Assert.assertFalse(string.equals(""));
	}


	protected void assertSubscriber(final SubscriberEntity subscriberEntity, final Boolean expected) {

		Assert.assertNotNull(subscriberEntity.isSubscriber());
		Assert.assertEquals(expected, subscriberEntity.isSubscriber());
	}


	protected void assertSupplierIconSet(final SupplierIconEntity entity) {

		this.assertNotNull(entity.getSupplierIcon());
	}


	protected void assertSupplierNameSet(final SupplierNameEntity entity) {

		this.assertStringNotEmpty(entity.getSupplierName());
	}


	protected void assertTitleSet(final TitleEntity entity) {

		this.assertStringNotEmpty(entity.getTitle());
	}


	protected void assertTrue(final boolean condition) {

		Assert.assertTrue(condition);
	}


	protected void assertUrl(final UrlEntity urlEntity, final URL expectedUrl) {

		Assert.assertNotNull(urlEntity.getUrl());
		Assert.assertEquals(expectedUrl, urlEntity.getUrl());
	}


	protected void assertUrlSet(final UrlEntity urlEntity) {

		Assert.assertNotNull(urlEntity.getUrl());
	}


	protected void assertUserPlayCountSet(final UserPlayCountEntity userPlayCountEntity) {

		Assert.assertNotNull(userPlayCountEntity.getUserPlayCount());
		Assert.assertTrue(userPlayCountEntity.getUserPlayCount() > 0);
	}


	protected boolean hasImages(final ImageEntity entity) {

		boolean imageFound = false;
		Assert.assertNotNull(entity.getImages());
		for (final Entry<ImageSize, URL> image : entity.getImages().entrySet()) {
			Assert.assertNotNull(image.getKey());
			Assert.assertNotNull(image.getValue());
			imageFound = true;
		}

		return imageFound;
	}


	protected boolean hasRealName(final RealnameEntity entity) {

		this.assertStringNullOrNotEmpty(entity.getRealname());
		return entity.getRealname() != null;
	}

}
