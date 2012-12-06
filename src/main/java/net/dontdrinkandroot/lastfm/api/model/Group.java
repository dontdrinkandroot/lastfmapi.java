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

import net.dontdrinkandroot.lastfm.api.model.paginatedresult.GenericPaginatedResult;
import net.dontdrinkandroot.lastfm.api.queries.AbstractUnauthenticatedGetQuery;
import net.dontdrinkandroot.lastfm.api.queries.UnauthenticatedGetQuery;
import net.dontdrinkandroot.lastfm.api.xml.DomUtils;

import org.w3c.dom.Element;


public class Group {

	private String name;


	public Group(final String name) {

		this.name = name;
	}


	public final String getName() {

		return this.name;
	}


	public final void setName(final String name) {

		this.name = name;
	}


	/**
	 * Get the hype list for a group
	 * 
	 * @param Group
	 *            The last.fm group name (Required).
	 * @return
	 */
	// public static UnauthenticatedGetQuery<Serializable> getHype(
	// final Object Group
	// ) {
	// //TODO: implement
	// if (1 == 1) {
	// throw new RuntimeException("Not implemented");
	// }
	// final UnauthenticatedGetQuery<Serializable> query
	// = new AbstractUnauthenticatedGetQuery<Serializable>("group.getHype") {
	// @Override
	// public Serializable parse(final Element root) {
	// return null;
	// }
	// };
	// query.addParameter("Group", Group);
	// return query;
	// }

	/**
	 * Get a list of members for this group.
	 * 
	 * @param group
	 *            The group name to fetch the members of. (Required).
	 * @param limit
	 *            The number of results to fetch per page (Optional). Defaults to 50.
	 * @param page
	 *            The page number to fetch. (Optional).
	 * @return
	 */
	public static UnauthenticatedGetQuery<GenericPaginatedResult<List<User>>> getMembers(
			final String group,
			final Integer limit,
			final Integer page) {

		final UnauthenticatedGetQuery<GenericPaginatedResult<List<User>>> query =
				new AbstractUnauthenticatedGetQuery<GenericPaginatedResult<List<User>>>("group.getMembers") {

					@Override
					public GenericPaginatedResult<List<User>> parse(final Element root) {

						final GenericPaginatedResult<List<User>> result = new GenericPaginatedResult<List<User>>(root);
						final List<Element> userElements = DomUtils.getChildrenByTagName(root, "user");
						final List<User> members = new ArrayList<User>();
						for (final Element userElement : userElements) {
							members.add(new User(userElement));
						}
						result.setEntries(members);

						return result;
					}
				};

		query.addParameter("group", group);
		query.addParameter("page", page);
		query.addParameter("limit", limit);

		return query;
	}

	/**
	 * Get an album chart for a group, for a given date range. If no date range issupplied, it will
	 * return the most recent album chart for this group.
	 * 
	 * @param group
	 *            The last.fm group name to fetch the charts of. (Required).
	 * @param from
	 *            The date at which the chart should start from. See Group.getWeeklyChartList for
	 *            more. (Optional).
	 * @param to
	 *            The date at which the chart should end on. See Group.getWeeklyChartList for more.
	 *            (Optional).
	 * @return
	 */
	// public static UnauthenticatedGetQuery<Serializable> getWeeklyAlbumChart(
	// final Object group,
	// final Object from,
	// final Object to
	// ) {
	// //TODO: implement
	// if (1 == 1) {
	// throw new RuntimeException("Not implemented");
	// }
	// final UnauthenticatedGetQuery<Serializable> query
	// = new AbstractUnauthenticatedGetQuery<Serializable>("group.getWeeklyAlbumChart") {
	// @Override
	// public Serializable parse(final Element root) {
	// return null;
	// }
	// };
	// query.addParameter("group", group);
	// query.addParameter("from", from);
	// query.addParameter("to", to);
	// return query;
	// }

	/**
	 * Get an artist chart for a group, for a given date range. If no date rangeis supplied, it will
	 * return the most recent album chart for this group.
	 * 
	 * @param group
	 *            The last.fm group name to fetch the charts of. (Required).
	 * @param from
	 *            The date at which the chart should start from. See Group.getWeeklyChartList for
	 *            more. (Optional).
	 * @param to
	 *            The date at which the chart should end on. See Group.getWeeklyChartList for more.
	 *            (Optional).
	 * @return
	 */
	// public static UnauthenticatedGetQuery<Serializable> getWeeklyArtistChart(
	// final Object group,
	// final Object from,
	// final Object to
	// ) {
	// //TODO: implement
	// if (1 == 1) {
	// throw new RuntimeException("Not implemented");
	// }
	// final UnauthenticatedGetQuery<Serializable> query
	// = new AbstractUnauthenticatedGetQuery<Serializable>("group.getWeeklyArtistChart") {
	// @Override
	// public Serializable parse(final Element root) {
	// return null;
	// }
	// };
	// query.addParameter("group", group);
	// query.addParameter("from", from);
	// query.addParameter("to", to);
	// return query;
	// }

	/**
	 * Get a list of available charts for this group, expressed as date rangeswhich can be sent to
	 * the chart services.
	 * 
	 * @param group
	 *            The last.fm group name to fetch the charts list for. (Required).
	 * @return
	 */
	// public static UnauthenticatedGetQuery<Serializable> getWeeklyChartList(
	// final Object group
	// ) {
	// //TODO: implement
	// if (1 == 1) {
	// throw new RuntimeException("Not implemented");
	// }
	// final UnauthenticatedGetQuery<Serializable> query
	// = new AbstractUnauthenticatedGetQuery<Serializable>("group.getWeeklyChartList") {
	// @Override
	// public Serializable parse(final Element root) {
	// return null;
	// }
	// };
	// query.addParameter("group", group);
	// return query;
	// }

	/**
	 * Get a track chart for a group, for a given date range. If no date range issupplied, it will
	 * return the most recent album chart for this group.
	 * 
	 * @param group
	 *            The last.fm group name to fetch the charts of. (Required).
	 * @param from
	 *            The date at which the chart should start from. See Group.getWeeklyChartList for
	 *            more. (Optional).
	 * @param to
	 *            The date at which the chart should end on. See Group.getWeeklyChartList for more.
	 *            (Optional).
	 * @return
	 */
	// public static UnauthenticatedGetQuery<Serializable> getWeeklyTrackChart(
	// final Object group,
	// final Object from,
	// final Object to
	// ) {
	// //TODO: implement
	// if (1 == 1) {
	// throw new RuntimeException("Not implemented");
	// }
	// final UnauthenticatedGetQuery<Serializable> query
	// = new AbstractUnauthenticatedGetQuery<Serializable>("group.getWeeklyTrackChart") {
	// @Override
	// public Serializable parse(final Element root) {
	// return null;
	// }
	// };
	// query.addParameter("group", group);
	// query.addParameter("from", from);
	// query.addParameter("to", to);
	// return query;
	// }

}
