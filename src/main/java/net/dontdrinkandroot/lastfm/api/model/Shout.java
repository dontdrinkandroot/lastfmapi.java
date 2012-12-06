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

import java.util.Date;

import net.dontdrinkandroot.lastfm.api.model.entitytypes.AuthorEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.BodyEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.DateEntity;

import org.w3c.dom.Element;


public class Shout extends LfmEntity implements DateEntity, AuthorEntity, BodyEntity {

	private static final long serialVersionUID = 1L;

	private Date date;

	private String body;

	private String author;


	/**
	 * Creates a {@link Shout} from a Dom {@link Element}.
	 * 
	 * @param element
	 *            The {@link Element} to parse.
	 */
	public Shout(final Element element) {

		super(element);
	}


	@Override
	public Date getDate() {

		return this.date;
	}


	@Override
	public void setDate(final Date date) {

		this.date = date;
	}


	@Override
	public String getBody() {

		return this.body;
	}


	@Override
	public void setBody(final String body) {

		this.body = body;
	}


	@Override
	public String getAuthor() {

		return this.author;
	}


	@Override
	public void setAuthor(final String author) {

		this.author = author;
	}

}
