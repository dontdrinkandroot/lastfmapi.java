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

import net.dontdrinkandroot.lastfm.api.model.entitytypes.DateAddedEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.FormatEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.SizesEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.TitleEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.UrlEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.VotesEntity;

import org.w3c.dom.Element;


public class Image extends LfmEntity
		implements TitleEntity, UrlEntity, DateAddedEntity, FormatEntity, VotesEntity, SizesEntity {

	private static final long serialVersionUID = 1L;

	private String title;

	private URL url;

	private Date dateAdded;

	private String format;

	private Votes votes;

	private List<Size> sizes;


	/**
	 * Creates an Image from a Dom {@link Element}.
	 * 
	 * @param imageElement
	 *            The {@link Element} to parse.
	 */
	public Image(final Element imageElement) {

		super(imageElement);
	}


	@Override
	public List<Size> getSizes() {

		return this.sizes;
	}


	@Override
	public void setSizes(final List<Size> sizes) {

		this.sizes = sizes;
	}


	@Override
	public Votes getVotes() {

		return this.votes;
	}


	@Override
	public void setVotes(final Votes votes) {

		this.votes = votes;
	}


	@Override
	public String getFormat() {

		return this.format;
	}


	@Override
	public void setFormat(final String format) {

		this.format = format;
	}


	@Override
	public Date getDateAdded() {

		return this.dateAdded;
	}


	@Override
	public void setDateAdded(final Date dateAdded) {

		this.dateAdded = dateAdded;
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
	public void setTitle(final String title) {

		this.title = title;
	}


	@Override
	public String getTitle() {

		return this.title;
	}

}
