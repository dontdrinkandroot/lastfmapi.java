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

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.dontdrinkandroot.lastfm.api.model.entitytypes.ArtistsEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.AttendanceEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.CancelledEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.DescriptionEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.EndDateEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.HeadlinerEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.IdEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.ImageEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.MachineTagEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.ReviewsEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.StartDateEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.TicketsEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.TitleEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.UrlEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.VenueEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.WebsiteEntity;
import net.dontdrinkandroot.lastfm.api.xml.DomUtils;

import org.w3c.dom.Element;


public class Event extends LfmEntity
		implements IdEntity, TitleEntity, VenueEntity, StartDateEntity, EndDateEntity, AttendanceEntity, ReviewsEntity,
		MachineTagEntity, ImageEntity, UrlEntity, DescriptionEntity, WebsiteEntity, CancelledEntity, TicketsEntity,
		ArtistsEntity, HeadlinerEntity {

	private static final long serialVersionUID = 1L;

	private String title;

	private Long id;

	private Venue venue;

	private List<Artist> artists;

	private Artist headliner;

	private Date startDate;

	private String description;

	private Map<ImageSize, URL> images;

	private Integer attendance;

	private Integer reviews;

	private URL url;

	private URL website;

	private boolean cancelled;

	private String machineTag;

	private List<Ticket> tickets;

	private Date endDate;


	/**
	 * Create an Event from an XML Element.
	 * 
	 * @param element
	 *            The XML Element to create the Event from.
	 */
	public Event(final Element element) {

		super(element);
		final Element venueElement = DomUtils.getChildByTagName(element, "venue");
		if (venueElement != null) {
			this.setVenue(new Venue(venueElement));
		}
	}


	@Override
	public void setVenue(final Venue venue) {

		this.venue = venue;
	}


	@Override
	public final Venue getVenue() {

		return this.venue;
	}


	@Override
	public URL getUrl() {

		return this.url;
	}


	@Override
	public void setUrl(final URL url) {

		this.url = url;
	}


	@Override
	public final void setTitle(final String title) {

		this.title = title;
	}


	@Override
	public final String getTitle() {

		return this.title;
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
	public Map<ImageSize, URL> getImages() {

		return this.images;
	}


	@Override
	public void setImages(final Map<ImageSize, URL> images) {

		this.images = images;
	}


	@Override
	public String getDescription() {

		return this.description;
	}


	@Override
	public void setDescription(final String description) {

		this.description = description;
	}


	@Override
	public URL getWebsite() {

		return this.website;
	}


	@Override
	public void setWebsite(final URL url) {

		this.website = url;
	}


	@Override
	public Artist getHeadliner() {

		return this.headliner;
	}


	@Override
	public void setHeadliner(final Artist headLiner) {

		this.headliner = headLiner;
	}


	@Override
	public List<Artist> getArtists() {

		return this.artists;
	}


	@Override
	public void setArtists(final List<Artist> artists) {

		this.artists = artists;
	}


	@Override
	public List<Ticket> getTickets() {

		return this.tickets;
	}


	@Override
	public void setTickets(final List<Ticket> tickets) {

		this.tickets = tickets;
	}


	@Override
	public Boolean isCancelled() {

		return this.cancelled;
	}


	@Override
	public void setCancelled(final Boolean cancelled) {

		this.cancelled = cancelled;
	}


	@Override
	public String getMachineTag() {

		return this.machineTag;
	}


	@Override
	public void setMachineTag(final String machineTag) {

		this.machineTag = machineTag;
	}


	@Override
	public Integer getReviews() {

		return this.reviews;
	}


	@Override
	public void setReviews(Integer reviews) {

		reviews = this.reviews;
	}


	@Override
	public Integer getAttendance() {

		return this.attendance;
	}


	@Override
	public void setAttendance(final Integer attendance) {

		this.attendance = attendance;
	}


	@Override
	public Date getEndDate() {

		return this.endDate;
	}


	@Override
	public void setEndDate(final Date date) {

		this.endDate = date;
	}


	@Override
	public Date getStartDate() {

		return this.startDate;
	}


	@Override
	public void setStartDate(final Date date) {

		this.startDate = date;
	}

	//
	// /**
	// * Set a user's attendance status for an event.
	// * @param event The numeric last.fm event id (Required).
	// * @param status The attendance status (0=Attending, 1=Maybe attending, 2=Not attending)
	// (Required).
	// * @return
	// */
	// public static AuthenticatedGetQuery<Serializable> attend(
	// final Object event,
	// final Object status
	// ) {
	// //TODO: implement
	// if (1 == 1) throw new RuntimeException("Not implemented");
	// AuthenticatedGetQuery<Serializable> query
	// = new AbstractAuthenticatedGetQuery<Serializable>("event.attend") {
	// @Override
	// public Serializable parse(final Element root) {
	// return null;
	// }
	// };
	// query.addParameter("event", event);
	// query.addParameter("status", status);
	// return query;
	// }
	//
	//
	// /**
	// * Get a list of attendees for an event.
	// * TODO: Using ArrayList to avoid complex generics.
	// * @param event The numeric last.fm event id (Required).
	// * @return
	// */
	// public static UnauthenticatedGetQuery<ArrayList<User>> getAttendees(
	// final Long event
	// ) {
	// UnauthenticatedGetQuery<ArrayList<User>> query
	// = new AbstractUnauthenticatedGetQuery<ArrayList<User>>("event.getAttendees") {
	// @SuppressWarnings("unchecked")
	// @Override
	// public ArrayList<User> parse(final Element root) {
	// ArrayList<User> ret = new ArrayList<User>();
	// for (Element userElement : (List<Element>) root.elements("user")) {
	// ret.add(new User(userElement));
	// }
	// return ret;
	// }
	// };
	// query.addParameter("event", event);
	// return query;
	// }
	//
	//
	// /**
	// * Get the metadata for an event on Last.fm. Includes attendance and lineupinformation.
	// * @param event The numeric last.fm event id (Required).
	// * @return
	// */
	// public static UnauthenticatedGetQuery<Serializable> getInfo(
	// final Object event
	// ) {
	// //TODO: implement
	// if (1 == 1) throw new RuntimeException("Not implemented");
	// UnauthenticatedGetQuery<Serializable> query
	// = new AbstractUnauthenticatedGetQuery<Serializable>("event.getInfo") {
	// @Override
	// public Serializable parse(final Element root) {
	// return null;
	// }
	// };
	// query.addParameter("event", event);
	// return query;
	// }
	//
	//
	// /**
	// * Get shouts for this event. Also available as an rss feed.
	// * @param event The numeric last.fm event id (Required).
	// * @return
	// */
	// public static UnauthenticatedGetQuery<Serializable> getShouts(
	// final Object event
	// ) {
	// //TODO: implement
	// if (1 == 1) throw new RuntimeException("Not implemented");
	// UnauthenticatedGetQuery<Serializable> query
	// = new AbstractUnauthenticatedGetQuery<Serializable>("event.getShouts") {
	// @Override
	// public Serializable parse(final Element root) {
	// return null;
	// }
	// };
	// query.addParameter("event", event);
	// return query;
	// }
	//
	//
	// /**
	// * Share an event with one or more Last.fm users or other friends.
	// * @param event An event ID (Required).
	// * @param publicViewable A Last.fm API key. (Optional): Optionally show the share in the
	// sharing users recent activity. Defaults to 0 (false).<br /> <span
	// class="param">message</span> (Optional): An optional message to send with the recommendation.
	// If not supplied a default message will be used.<br /> <span class="param">recipient</span>
	// (Required): Email Address | Last.fm Username - A comma delimited list of email addresses or
	// Last.fm usernames. Maximum is 10.<br /> <span class="param">api_key</span> (Required).
	// * @return
	// */
	// public static AuthenticatedGetQuery<Serializable> share(
	// final Object event,
	// final Object publicViewable
	// ) {
	// //TODO: implement
	// if (1 == 1) throw new RuntimeException("Not implemented");
	// AuthenticatedGetQuery<Serializable> query
	// = new AbstractAuthenticatedGetQuery<Serializable>("event.share") {
	// @Override
	// public Serializable parse(final Element root) {
	// return null;
	// }
	// };
	// query.addParameter("event", event);
	// query.addParameter("public", publicViewable);
	// return query;
	// }
	//
	//
	// /**
	// * Shout in this event's shoutbox
	// * @param event The id of the event to shout on (Required).
	// * @param message The message to post to the shoutbox (Required).
	// * @return
	// */
	// public static AuthenticatedGetQuery<Serializable> shout(
	// final Object event,
	// final Object message
	// ) {
	// //TODO: implement
	// if (1 == 1) throw new RuntimeException("Not implemented");
	// AuthenticatedGetQuery<Serializable> query
	// = new AbstractAuthenticatedGetQuery<Serializable>("event.shout") {
	// @Override
	// public Serializable parse(final Element root) {
	// return null;
	// }
	// };
	// query.addParameter("event", event);
	// query.addParameter("message", message);
	// return query;
	// }
	//
	//

}
