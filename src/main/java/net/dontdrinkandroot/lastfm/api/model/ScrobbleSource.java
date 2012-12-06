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
import java.util.Map;

import net.dontdrinkandroot.lastfm.api.model.entitytypes.ImageEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.NameEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.UrlEntity;

import org.w3c.dom.Element;


public class ScrobbleSource extends LfmEntity implements NameEntity, UrlEntity, ImageEntity {

	private static final long serialVersionUID = 1L;

	private Map<ImageSize, URL> images;

	private URL url;

	private String name;


	/**
	 * Create a ScrobbleSource from a Dom {@link Element}.
	 * 
	 * @param element
	 *            The {@link Element} to parse.
	 */
	public ScrobbleSource(final Element element) {

		super(element);
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
	public URL getUrl() {

		return this.url;
	}


	@Override
	public void setUrl(final URL url) {

		this.url = url;
	}


	@Override
	public void setName(final String name) {

		this.name = name;
	}


	@Override
	public String getName() {

		return this.name;
	}

}
