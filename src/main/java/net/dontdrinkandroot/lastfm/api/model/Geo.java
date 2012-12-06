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
//import java.io.Serializable;
//
//import org.dom4j.Element;
//
//import net.dontdrinkandroot.lastfm.api.queries.AuthenticatedGetQuery;
//import net.dontdrinkandroot.lastfm.api.queries.UnauthenticatedGetQuery;
//import net.dontdrinkandroot.lastfm.api.queries.impl.AbstractAuthenticatedGetQuery;
//import net.dontdrinkandroot.lastfm.api.queries.impl.AbstractUnauthenticatedGetQuery;
//
public class Geo {
//
//	/**
//	 * Get all events in a specific location by country or city name.
//	 * @param location Specifies a location to retrieve events for (service returns nearby events by default) (Optional).
//	 * @param latitude Specifies a latitude value to retrieve events for (service returns nearby events by default) (Optional).
//	 * @param longitude Specifies a longitude value to retrieve events for (service returns nearby events by default) (Optional).
//	 * @param page Display more results by pagination (Optional).
//	 * @param distance Find events within a specified radius (in kilometres) (Optional).
//	 * @return 
//	 */
//	public static UnauthenticatedGetQuery<Serializable> getEvents(
//		final Object location,
//		final Object latitude,
//		final Object longitude,
//		final Object page,
//		final Object distance
//	) {
//		//TODO: implement
//		if (1 == 1) throw new RuntimeException("Not implemented");
//		UnauthenticatedGetQuery<Serializable> query
//			 = new AbstractUnauthenticatedGetQuery<Serializable>("geo.getEvents") {
//				@Override
//				public Serializable parse(final Element root) {
//					return null;
//				}
//		};
//		query.addParameter("location", location);
//		query.addParameter("lat", latitude);
//		query.addParameter("long", longitude);
//		query.addParameter("page", page);
//		query.addParameter("distance", distance);
//		return query;
//	}
//
//
//	/**
//	 * Get a chart of artists for a metro
//	 * @param country A country name, as defined by the ISO 3166-1 country names standard (Required).
//	 * @param metro The metro's name (Required).
//	 * @param start Beginning timestamp of the weekly range requested (c.f. geo.getWeeklyChartlist) (Optional).
//	 * @param end Ending timestamp of the weekly range requested (c.f. geo.getWeeklyChartlist) (Optional).
//	 * @return 
//	 */
//	public static UnauthenticatedGetQuery<Serializable> getMetroArtistChart(
//		final Object country,
//		final Object metro,
//		final Object start,
//		final Object end
//	) {
//		//TODO: implement
//		if (1 == 1) throw new RuntimeException("Not implemented");
//		UnauthenticatedGetQuery<Serializable> query
//			 = new AbstractUnauthenticatedGetQuery<Serializable>("geo.getMetroArtistChart") {
//				@Override
//				public Serializable parse(final Element root) {
//					return null;
//				}
//		};
//		query.addParameter("country", country);
//		query.addParameter("metro", metro);
//		query.addParameter("start", start);
//		query.addParameter("end", end);
//		return query;
//	}
//
//
//	/**
//	 * Get a chart of hyped (up and coming) artists for a metro
//	 * @param country A country name, as defined by the ISO 3166-1 country names standard (Required).
//	 * @param metro The metro's name (Required).
//	 * @param start Beginning timestamp of the weekly range requested (c.f. geo.getWeeklyChartlist) (Optional).
//	 * @param end Ending timestamp of the weekly range requested (c.f. geo.getWeeklyChartlist) (Optional).
//	 * @return 
//	 */
//	public static UnauthenticatedGetQuery<Serializable> getMetroHypeArtistChart(
//		final Object country,
//		final Object metro,
//		final Object start,
//		final Object end
//	) {
//		//TODO: implement
//		if (1 == 1) throw new RuntimeException("Not implemented");
//		UnauthenticatedGetQuery<Serializable> query
//			 = new AbstractUnauthenticatedGetQuery<Serializable>("geo.getMetroHypeArtistChart") {
//				@Override
//				public Serializable parse(final Element root) {
//					return null;
//				}
//		};
//		query.addParameter("country", country);
//		query.addParameter("metro", metro);
//		query.addParameter("start", start);
//		query.addParameter("end", end);
//		return query;
//	}
//
//
//	/**
//	 * Get a chart of tracks for a metro
//	 * @param country A country name, as defined by the ISO 3166-1 country names standard (Required).
//	 * @param metro The metro's name (Required).
//	 * @param start Beginning timestamp of the weekly range requested (c.f. geo.getWeeklyChartlist) (Optional).
//	 * @param end Ending timestamp of the weekly range requested (c.f. geo.getWeeklyChartlist) (Optional).
//	 * @return 
//	 */
//	public static UnauthenticatedGetQuery<Serializable> getMetroHypeTrackChart(
//		final Object country,
//		final Object metro,
//		final Object start,
//		final Object end
//	) {
//		//TODO: implement
//		if (1 == 1) throw new RuntimeException("Not implemented");
//		UnauthenticatedGetQuery<Serializable> query
//			 = new AbstractUnauthenticatedGetQuery<Serializable>("geo.getMetroHypeTrackChart") {
//				@Override
//				public Serializable parse(final Element root) {
//					return null;
//				}
//		};
//		query.addParameter("country", country);
//		query.addParameter("metro", metro);
//		query.addParameter("start", start);
//		query.addParameter("end", end);
//		return query;
//	}
//
//
//	/**
//	 * Get a chart of tracks for a metro
//	 * @param country A country name, as defined by the ISO 3166-1 country names standard (Required).
//	 * @param metro The metro's name (Required).
//	 * @param start Beginning timestamp of the weekly range requested (c.f. geo.getWeeklyChartlist) (Optional).
//	 * @param end Ending timestamp of the weekly range requested (c.f. geo.getWeeklyChartlist) (Optional).
//	 * @return 
//	 */
//	public static UnauthenticatedGetQuery<Serializable> getMetroTrackChart(
//		final Object country,
//		final Object metro,
//		final Object start,
//		final Object end
//	) {
//		//TODO: implement
//		if (1 == 1) throw new RuntimeException("Not implemented");
//		UnauthenticatedGetQuery<Serializable> query
//			 = new AbstractUnauthenticatedGetQuery<Serializable>("geo.getMetroTrackChart") {
//				@Override
//				public Serializable parse(final Element root) {
//					return null;
//				}
//		};
//		query.addParameter("country", country);
//		query.addParameter("metro", metro);
//		query.addParameter("start", start);
//		query.addParameter("end", end);
//		return query;
//	}
//
//
//	/**
//	 * Get a chart of the artists which make that metro unique
//	 * @param country A country name, as defined by the ISO 3166-1 country names standard (Required).
//	 * @param metro The metro's name (Required).
//	 * @param start Beginning timestamp of the weekly range requested (c.f. geo.getWeeklyChartlist) (Optional).
//	 * @param end Ending timestamp of the weekly range requested (c.f. geo.getWeeklyChartlist) (Optional).
//	 * @return 
//	 */
//	public static UnauthenticatedGetQuery<Serializable> getMetroUniqueArtistChart(
//		final Object country,
//		final Object metro,
//		final Object start,
//		final Object end
//	) {
//		//TODO: implement
//		if (1 == 1) throw new RuntimeException("Not implemented");
//		UnauthenticatedGetQuery<Serializable> query
//			 = new AbstractUnauthenticatedGetQuery<Serializable>("geo.getMetroUniqueArtistChart") {
//				@Override
//				public Serializable parse(final Element root) {
//					return null;
//				}
//		};
//		query.addParameter("country", country);
//		query.addParameter("metro", metro);
//		query.addParameter("start", start);
//		query.addParameter("end", end);
//		return query;
//	}
//
//
//	/**
//	 * Get a chart of tracks for a metro
//	 * @param country A country name, as defined by the ISO 3166-1 country names standard (Required).
//	 * @param metro The metro's name (Required).
//	 * @param start Beginning timestamp of the weekly range requested (c.f. geo.getWeeklyChartlist) (Optional).
//	 * @param end Ending timestamp of the weekly range requested (c.f. geo.getWeeklyChartlist) (Optional).
//	 * @return 
//	 */
//	public static UnauthenticatedGetQuery<Serializable> getMetroUniqueTrackChart(
//		final Object country,
//		final Object metro,
//		final Object start,
//		final Object end
//	) {
//		//TODO: implement
//		if (1 == 1) throw new RuntimeException("Not implemented");
//		UnauthenticatedGetQuery<Serializable> query
//			 = new AbstractUnauthenticatedGetQuery<Serializable>("geo.getMetroUniqueTrackChart") {
//				@Override
//				public Serializable parse(final Element root) {
//					return null;
//				}
//		};
//		query.addParameter("country", country);
//		query.addParameter("metro", metro);
//		query.addParameter("start", start);
//		query.addParameter("end", end);
//		return query;
//	}
//
//
//	/**
//	 * Get a list of available chart periods for this metro, expressed as dateranges which can be sent to the chart services.
//	 * @return 
//	 */
//	public static UnauthenticatedGetQuery<Serializable> getMetroWeeklyChartlist(
//	) {
//		//TODO: implement
//		if (1 == 1) throw new RuntimeException("Not implemented");
//		UnauthenticatedGetQuery<Serializable> query
//			 = new AbstractUnauthenticatedGetQuery<Serializable>("geo.getMetroWeeklyChartlist") {
//				@Override
//				public Serializable parse(final Element root) {
//					return null;
//				}
//		};
//		return query;
//	}
//
//
//	/**
//	 * Get a list of valid countries and metros for use in the other webservices
//	 * @param country Optionally restrict the results to those Metros from a particular country, as defined by the ISO 3166-1 country names standard (Optional).
//	 * @return 
//	 */
//	public static UnauthenticatedGetQuery<Serializable> getMetros(
//		final Object country
//	) {
//		//TODO: implement
//		if (1 == 1) throw new RuntimeException("Not implemented");
//		UnauthenticatedGetQuery<Serializable> query
//			 = new AbstractUnauthenticatedGetQuery<Serializable>("geo.getMetros") {
//				@Override
//				public Serializable parse(final Element root) {
//					return null;
//				}
//		};
//		query.addParameter("country", country);
//		return query;
//	}
//
//
//	/**
//	 * Get the most popular artists on Last.fm by country
//	 * @param country A country name, as defined by the ISO 3166-1 country names standard (Required).
//	 * @return 
//	 */
//	public static UnauthenticatedGetQuery<Serializable> getTopArtists(
//		final Object country
//	) {
//		//TODO: implement
//		if (1 == 1) throw new RuntimeException("Not implemented");
//		UnauthenticatedGetQuery<Serializable> query
//			 = new AbstractUnauthenticatedGetQuery<Serializable>("geo.getTopArtists") {
//				@Override
//				public Serializable parse(final Element root) {
//					return null;
//				}
//		};
//		query.addParameter("country", country);
//		return query;
//	}
//
//
//	/**
//	 * Get the most popular tracks on Last.fm last week by country
//	 * @param country A country name, as defined by the ISO 3166-1 country names standard (Required).
//	 * @param location A metro name, to fetch the charts for (must be within the country specified) (Optional).
//	 * @return 
//	 */
//	public static UnauthenticatedGetQuery<Serializable> getTopTracks(
//		final Object country,
//		final Object location
//	) {
//		//TODO: implement
//		if (1 == 1) throw new RuntimeException("Not implemented");
//		UnauthenticatedGetQuery<Serializable> query
//			 = new AbstractUnauthenticatedGetQuery<Serializable>("geo.getTopTracks") {
//				@Override
//				public Serializable parse(final Element root) {
//					return null;
//				}
//		};
//		query.addParameter("country", country);
//		query.addParameter("location", location);
//		return query;
//	}
//
//
}
