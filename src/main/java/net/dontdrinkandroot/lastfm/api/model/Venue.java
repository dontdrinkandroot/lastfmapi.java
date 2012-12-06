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

import java.io.Serializable;

import net.dontdrinkandroot.lastfm.api.model.entitytypes.IdEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.NameEntity;
import net.dontdrinkandroot.lastfm.api.xml.DomUtils;

import org.w3c.dom.Element;


public class Venue extends LfmEntity implements Serializable, NameEntity, IdEntity {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	private Location location;


	/**
	 * Create a Venue from an XML element.
	 * 
	 * @param element
	 *            The XML element to create the Venue from.
	 */
	public Venue(final Element element) {

		super(element);
		final Element locationElement = DomUtils.getChildByTagName(element, "location");
		if (locationElement != null) {
			this.setLocation(new Location(locationElement));
		}

	}


	private void setLocation(final Location location) {

		this.location = location;
	}


	public final Location getLocation() {

		return this.location;
	}


	@Override
	public final Long getId() {

		return this.id;
	}


	@Override
	public final void setId(final Long id) {

		this.id = id;
	}


	@Override
	public final void setName(final String name) {

		this.name = name;
	}


	@Override
	public final String getName() {

		return this.name;
	}

	//
	// /**
	// * Get a list of upcoming events at this venue.
	// * @param venue A Last.fm API key. (Required) :The id for the venue you would like to fetch
	// event listings for.<br /> <span class="param">api_key</span> (Required).
	// * @return
	// */
	// public static UnauthenticatedGetQuery<Serializable> getEvents(
	// final Object venue
	// ) {
	// //TODO: implement
	// if (1 == 1) throw new RuntimeException("Not implemented");
	// UnauthenticatedGetQuery<Serializable> query
	// = new AbstractUnauthenticatedGetQuery<Serializable>("venue.getEvents") {
	// @Override
	// public Serializable parse(final Element root) {
	// return null;
	// }
	// };
	// query.addParameter("venue", venue);
	// return query;
	// }
	//
	//
	// /**
	// * Get a paginated list of all the events held at this venue in the past.
	// * @param venue A Last.fm API key. (Required) :The id for the venue you would like to fetch
	// event listings for.<br /> <span class="param">page</span> (Optional) :The page of results to
	// return.<br /> <span class="param">limit</span> (Optional) :The maximum number of results to
	// return per page.<br /> <span class="param">api_key</span> (Required).
	// * @return
	// */
	// public static UnauthenticatedGetQuery<Serializable> getPastEvents(
	// final Object venue
	// ) {
	// //TODO: implement
	// if (1 == 1) throw new RuntimeException("Not implemented");
	// UnauthenticatedGetQuery<Serializable> query
	// = new AbstractUnauthenticatedGetQuery<Serializable>("venue.getPastEvents") {
	// @Override
	// public Serializable parse(final Element root) {
	// return null;
	// }
	// };
	// query.addParameter("venue", venue);
	// return query;
	// }
	//
	//
	// /**
	// * Search for a venue by venue name
	// * @param venue The venue name you would like to search for. (Required).
	// * @param page The results page you would like to fetch (Optional).
	// * @param limit The number of results to fetch per page. Defaults to 50. (Optional).
	// * @param country Filter your results by country. Expressed as an ISO 3166-2 code. (Optional).
	// * @return
	// */
	// public static UnauthenticatedGetQuery<Serializable> search(
	// final Object venue,
	// final Object page,
	// final Object limit,
	// final Object country
	// ) {
	// //TODO: implement
	// if (1 == 1) throw new RuntimeException("Not implemented");
	// UnauthenticatedGetQuery<Serializable> query
	// = new AbstractUnauthenticatedGetQuery<Serializable>("venue.search") {
	// @Override
	// public Serializable parse(final Element root) {
	// return null;
	// }
	// };
	// query.addParameter("venue", venue);
	// query.addParameter("page", page);
	// query.addParameter("limit", limit);
	// query.addParameter("country", country);
	// return query;
	// }
	//
	//
}
