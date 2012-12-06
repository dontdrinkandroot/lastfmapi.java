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
package net.dontdrinkandroot.lastfm.api;

import java.util.Properties;

import net.dontdrinkandroot.utils.lang.StringUtils;


/**
 * Some properties needed for testing.
 * 
 * @author Philip W. Sorst
 */
public class TestConf {

	/** The user to test with */
	private String user;

	/** The users password */
	private String password;

	/** If the user is subscriber */
	private boolean subscriber = false;

	/** The last.fm apikey */
	private String key;

	/** The last.fm secret */
	private String secret;

	/** If authentication tests should be run */
	private boolean runAuthTests = false;

	/** If authentication tests that manipulate user data should be run */
	private boolean runManipulatingAuthTests = false;


	public TestConf() {

	}


	public TestConf(final Properties properties) {

		final String apikey = properties.getProperty("apikey");
		if (StringUtils.isBlank(apikey)) {
			throw new RuntimeException("Property 'apikey' not found");
		}
		this.key = apikey;

		final String apisecret = properties.getProperty("apisecret");
		if (StringUtils.isBlank(apisecret)) {
			throw new RuntimeException("Property 'apisecret' not found");
		}
		this.secret = apisecret;

		final String runauthtestsString = properties.getProperty("runauthtests");
		if (StringUtils.isBlank(runauthtestsString)) {
			throw new RuntimeException("Property 'runauthtests' not found");
		}
		this.runAuthTests = Boolean.parseBoolean(runauthtestsString);

		final String runmanipulatingauthtestsString = properties.getProperty("runmanipulatingauthtests");
		if (StringUtils.isBlank(runmanipulatingauthtestsString)) {
			throw new RuntimeException("Property 'runmanipulatingauthtests' not found");
		}
		this.runManipulatingAuthTests = Boolean.parseBoolean(runmanipulatingauthtestsString);

		if (this.runAuthTests || this.runManipulatingAuthTests) {
			final String user = properties.getProperty("user");
			if (StringUtils.isBlank(user)) {
				throw new RuntimeException("Property 'user' not found");
			}
			this.user = user;

			final String password = properties.getProperty("password");
			if (StringUtils.isBlank(password)) {
				throw new RuntimeException("Property 'password' not found");
			}
			this.password = password;
		}
	}


	public final String getUser() {

		return this.user;
	}


	public final void setUser(final String user) {

		this.user = user;
	}


	public final String getPassword() {

		return this.password;
	}


	public final void setPassword(final String password) {

		this.password = password;
	}


	public final String getKey() {

		return this.key;
	}


	public final void setKey(final String key) {

		this.key = key;
	}


	public final String getSecret() {

		return this.secret;
	}


	public final void setSecret(final String secret) {

		this.secret = secret;
	}


	public final boolean isSubscriber() {

		return this.subscriber;
	}


	public final void setSubscriber(final boolean subscriber) {

		this.subscriber = subscriber;
	}


	public final boolean runAuthTests() {

		return this.runAuthTests;
	}


	public final void setRunAuthTests(final boolean runAuthTests) {

		this.runAuthTests = runAuthTests;
	}


	public final boolean runManipulatingAuthTests() {

		return this.runManipulatingAuthTests;
	}


	public final void setRunManipulatingAuthTests(final boolean runManipulatingAuthTests) {

		this.runManipulatingAuthTests = runManipulatingAuthTests;
	}


	@Override
	public final String toString() {

		return this.user + " (" + this.password + ", " + this.subscriber + ")";
	}

}
