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
package net.dontdrinkandroot.lastfm.api.example;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import net.dontdrinkandroot.lastfm.api.LastfmWebServicesException;
import net.dontdrinkandroot.lastfm.api.model.User;
import net.dontdrinkandroot.lastfm.api.model.paginatedresult.GenericPaginatedResult;
import net.dontdrinkandroot.lastfm.api.ws.DefaultLastfmWebServices;
import net.dontdrinkandroot.lastfm.api.ws.LastfmWebServices;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;


public class ExampleSetup {

	public static final String API_KEY = "";

	public static final String API_SECRET = "";


	public static void main(final String[] args) throws IOException, ParserConfigurationException,
			LastfmWebServicesException {

		final Logger rootLogger = Logger.getRootLogger();
		rootLogger.removeAllAppenders();
		rootLogger.addAppender(new ConsoleAppender(new PatternLayout("%d{ABSOLUTE} %p %c - %m%n")));
		rootLogger.setLevel(Level.INFO);

		LastfmWebServices ws = new DefaultLastfmWebServices(ExampleSetup.API_KEY, ExampleSetup.API_SECRET);

		GenericPaginatedResult<List<User>> result = ws.fetch(User.getFriends("shoxrocks", null, null, null));
		for (User friend : result.getEntries()) {
			System.out.println(friend);
		}
	}
}
