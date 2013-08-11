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
import java.util.ArrayList;
import java.util.List;

import net.dontdrinkandroot.lastfm.api.model.entitytypes.CountEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.NameEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.ReachEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.StreamableEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.TaggingsEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.UrlEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.WikiEntity;
import net.dontdrinkandroot.lastfm.api.model.paginatedresult.AlbumsPaginatedResult;
import net.dontdrinkandroot.lastfm.api.model.paginatedresult.ArtistsPaginatedResult;
import net.dontdrinkandroot.lastfm.api.model.paginatedresult.PaginatedResult;
import net.dontdrinkandroot.lastfm.api.model.paginatedresult.TracksPaginatedResult;
import net.dontdrinkandroot.lastfm.api.queries.AbstractUnauthenticatedGetQuery;
import net.dontdrinkandroot.lastfm.api.queries.UnauthenticatedGetQuery;
import net.dontdrinkandroot.lastfm.api.xml.DomUtils;
import net.dontdrinkandroot.utils.ISO_3166_1_alpha2;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public class Tag extends LfmEntity
		implements NameEntity, UrlEntity, CountEntity, TaggingsEntity, ReachEntity, StreamableEntity, WikiEntity {

	private static final long serialVersionUID = 2L;

	private String name;

	private URL url;

	private Integer count;

	private Integer reach;

	private Integer taggings;

	private Streamable streamable;

	private Wiki wiki;


	public Tag() {

	}


	/**
	 * Construct Tag from dom Element.
	 * 
	 * @param element
	 *            The dom Element to convert.
	 */
	public Tag(final Element element) {

		super(element);
	}


	public Tag(final String name) {

		this.name = name;
	}


	@Override
	public final String getName() {

		return this.name;
	}


	@Override
	public final void setName(final String name) {

		this.name = name;
	}


	@Override
	public final URL getUrl() {

		return this.url;
	}


	@Override
	public final void setUrl(final URL url) {

		this.url = url;
	}


	@Override
	public final Integer getCount() {

		return this.count;
	}


	@Override
	public final void setCount(final Integer count) {

		this.count = count;
	}


	@Override
	public final Integer getReach() {

		return this.reach;
	}


	@Override
	public final void setReach(final Integer reach) {

		this.reach = reach;
	}


	@Override
	public final Integer getTaggings() {

		return this.taggings;
	}


	@Override
	public final void setTaggings(final Integer taggings) {

		this.taggings = taggings;
	}


	@Override
	public final Streamable getStreamable() {

		return this.streamable;
	}


	@Override
	public final void setStreamable(final Streamable streamable) {

		this.streamable = streamable;
	}


	@Override
	public final Wiki getWiki() {

		return this.wiki;
	}


	@Override
	public final void setWiki(final Wiki wiki) {

		this.wiki = wiki;
	}


	@Override
	public final String toString() {

		final String ret = this.name;
		return ret;
	}


	/*
	 * HashCode and Equals based on the assumption that the name is the unique identifier.
	 */

	@Override
	public final int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result;
		if (this.name != null) {
			result += this.name.hashCode();
		}
		return result;
	}


	@Override
	public final boolean equals(final Object obj) {

		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final Tag other = (Tag) obj;
		if (this.name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!this.name.equals(other.name)) {
			return false;
		}
		return true;
	}


	/**
	 * Get the metadata for a tag.
	 * 
	 * @param tag
	 *            The tag name (Required).
	 * @param lang
	 *            The language to return the wiki in, expressed as an ISO 639 alpha-2 code
	 *            (Optional).
	 * @return
	 */
	public static UnauthenticatedGetQuery<Tag> getInfo(final String tag, final ISO_3166_1_alpha2 lang) {

		final UnauthenticatedGetQuery<Tag> query = new AbstractUnauthenticatedGetQuery<Tag>("tag.getInfo") {

			@Override
			public Tag parse(final Element root) {

				final Tag tag = new Tag(root);
				tag.setReach(DomUtils.toInteger(DomUtils.getChildByTagName(root, "reach")));
				tag.setTaggings(DomUtils.toInteger(DomUtils.getChildByTagName(root, "taggings")));
				tag.setStreamable(new Streamable(DomUtils.getChildByTagName(root, "streamable")));
				tag.setWiki(new Wiki(DomUtils.getChildByTagName(root, "wiki")));

				return tag;
			}
		};
		query.addParameter("tag", tag);
		if (lang != null) {
			query.addParameter("lang", lang.toString());
		}

		return query;
	}


	/**
	 * Search for tags similar to this one. Returns tags ranked by similarity, based on listening
	 * data.
	 * 
	 * @param tag
	 *            The tag name (Required).
	 */
	public static UnauthenticatedGetQuery<ArrayList<Tag>> getSimilar(final String tag) {

		UnauthenticatedGetQuery<ArrayList<Tag>> query =
				new AbstractUnauthenticatedGetQuery<ArrayList<Tag>>("tag.getSimilar") {

					@Override
					public ArrayList<Tag> parse(final Element root) {

						final List<Element> tagElements = DomUtils.getChildrenByTagName(root, "tag");
						final ArrayList<Tag> tags = new ArrayList<Tag>();
						for (final Element tagElement : tagElements) {
							tags.add(new Tag(tagElement));
						}

						return tags;
					}
				};
		query.addParameter("tag", tag);
		return query;
	}


	/**
	 * Get the top albums tagged by this tag, ordered by tag count.
	 * 
	 * @param tagName
	 *            The tag name (Required).
	 * @param limit
	 *            The number of results to fetch per page (Optional). Defaults to 50.
	 * @param page
	 *            he page number to fetch (Optional). Defaults to first page.
	 */
	public static UnauthenticatedGetQuery<PaginatedResult<List<Album>>> getTopAlbums(
			String tagName,
			Integer limit,
			Integer page) {

		UnauthenticatedGetQuery<PaginatedResult<List<Album>>> query =
				new AbstractUnauthenticatedGetQuery<PaginatedResult<List<Album>>>("tag.getTopAlbums") {

					@Override
					public PaginatedResult<List<Album>> parse(final Element root) {

						return new AlbumsPaginatedResult(root);
					}
				};

		query.addParameter("tag", tagName);
		query.addParameter("limit", limit);
		query.addParameter("page", page);

		return query;
	}


	/**
	 * Get the top artists tagged by this tag, ordered by tag count.
	 * 
	 * @param tagName
	 *            The tag name (Required).
	 * @param limit
	 *            The number of results to fetch per page (Optional). Defaults to 50.
	 * @param page
	 *            he page number to fetch (Optional). Defaults to first page.
	 */
	public static UnauthenticatedGetQuery<PaginatedResult<List<Artist>>> getTopArtists(
			String tagName,
			Integer limit,
			Integer page) {

		UnauthenticatedGetQuery<PaginatedResult<List<Artist>>> query =
				new AbstractUnauthenticatedGetQuery<PaginatedResult<List<Artist>>>("tag.getTopArtists") {

					@Override
					public PaginatedResult<List<Artist>> parse(final Element root) {

						return new ArtistsPaginatedResult(root);
					}
				};

		query.addParameter("tag", tagName);
		query.addParameter("limit", limit);
		query.addParameter("page", page);

		return query;
	}


	// TODO: Using arraylist here to avoid complex generics.
	/**
	 * Fetches the top global tags on Last.fm, sorted by popularity (number oftimes used).
	 * 
	 * @return The query needed to fetch the TopTags.
	 */
	public static UnauthenticatedGetQuery<ArrayList<Tag>> getTopTags() {

		final UnauthenticatedGetQuery<ArrayList<Tag>> query =
				new AbstractUnauthenticatedGetQuery<ArrayList<Tag>>("tag.getTopTags") {

					@Override
					public ArrayList<Tag> parse(final Element root) {

						final ArrayList<Tag> ret = new ArrayList<Tag>();
						final NodeList tagElList = root.getElementsByTagName("tag");
						if (tagElList != null) {
							for (int i = 0; i < tagElList.getLength(); i++) {
								final Element tagElement = (Element) tagElList.item(i);
								ret.add(new Tag(tagElement));
							}
						}

						return ret;
					}

				};

		return query;
	}


	/**
	 * Get the top tracks tagged by this tag, ordered by tag count.
	 * 
	 * @param tagName
	 *            The tag name (Required).
	 * @param limit
	 *            The number of results to fetch per page (Optional). Defaults to 50.
	 * @param page
	 *            he page number to fetch (Optional). Defaults to first page.
	 */
	public static UnauthenticatedGetQuery<PaginatedResult<List<Track>>> getTopTracks(
			String tagName,
			Integer limit,
			Integer page) {

		UnauthenticatedGetQuery<PaginatedResult<List<Track>>> query =
				new AbstractUnauthenticatedGetQuery<PaginatedResult<List<Track>>>("tag.getTopTracks") {

					@Override
					public PaginatedResult<List<Track>> parse(final Element root) {

						return new TracksPaginatedResult(root);
					}
				};

		query.addParameter("tag", tagName);
		query.addParameter("limit", limit);
		query.addParameter("page", page);

		return query;
	}
	//
	//
	// /**
	// * Get an artist chart for a tag, for a given date range. If no date range issupplied, it will
	// return the most recent artist chart for this tag.
	// * @param tag The tag name in question (Required).
	// * @param from The date at which the chart should start from. See Tag.getWeeklyChartList for
	// more. (Optional).
	// * @param to The date at which the chart should end on. See Tag.getWeeklyChartList for more.
	// (Optional).
	// * @param limit The number of chart items to return. (Optional, default = 50).
	// * @return
	// */
	// public static UnauthenticatedGetQuery<Serializable> getWeeklyArtistChart(
	// final Object tag,
	// final Object from,
	// final Object to,
	// final Object limit
	// ) {
	// //TODO: implement
	// if (1 == 1) throw new RuntimeException("Not implemented");
	// UnauthenticatedGetQuery<Serializable> query
	// = new AbstractUnauthenticatedGetQuery<Serializable>("tag.getWeeklyArtistChart") {
	// @Override
	// public Serializable parse(final Element root) {
	// return null;
	// }
	// };
	// query.addParameter("tag", tag);
	// query.addParameter("from", from);
	// query.addParameter("to", to);
	// query.addParameter("limit", limit);
	// return query;
	// }
	//
	//
	// /**
	// * Get a list of available charts for this tag, expressed as date ranges whichcan be sent to
	// the chart services.
	// * @param tag The tag name in question (Required).
	// * @return
	// */
	// public static UnauthenticatedGetQuery<Serializable> getWeeklyChartList(
	// final Object tag
	// ) {
	// //TODO: implement
	// if (1 == 1) throw new RuntimeException("Not implemented");
	// UnauthenticatedGetQuery<Serializable> query
	// = new AbstractUnauthenticatedGetQuery<Serializable>("tag.getWeeklyChartList") {
	// @Override
	// public Serializable parse(final Element root) {
	// return null;
	// }
	// };
	// query.addParameter("tag", tag);
	// return query;
	// }
	//
	//
	// /**
	// * Search for a tag by name. Returns matches sorted by relevance.
	// * @param limit Limit the number of tags returned at one time. Default (maximum) is 30.
	// (Optional).
	// * @param page Scan into the results by specifying a page number. Defaults to first page.
	// (Optional).
	// * @param tag The tag name in question. (Required).
	// * @return
	// */
	// public static UnauthenticatedGetQuery<Serializable> search(
	// final Object limit,
	// final Object page,
	// final Object tag
	// ) {
	// //TODO: implement
	// if (1 == 1) throw new RuntimeException("Not implemented");
	// UnauthenticatedGetQuery<Serializable> query
	// = new AbstractUnauthenticatedGetQuery<Serializable>("tag.search") {
	// @Override
	// public Serializable parse(final Element root) {
	// return null;
	// }
	// };
	// query.addParameter("limit", limit);
	// query.addParameter("page", page);
	// query.addParameter("tag", tag);
	// return query;
	// }

}
