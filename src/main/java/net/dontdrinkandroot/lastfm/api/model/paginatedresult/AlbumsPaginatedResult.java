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
package net.dontdrinkandroot.lastfm.api.model.paginatedresult;

import java.util.ArrayList;
import java.util.List;

import net.dontdrinkandroot.lastfm.api.model.Album;

import org.w3c.dom.Element;


public class AlbumsPaginatedResult extends GenericPaginatedResult<List<Album>> {

	public AlbumsPaginatedResult(Element element) {

		super(element);

		final ArrayList<Album> albums = new ArrayList<Album>();
		final List<Element> albumElements =
				net.dontdrinkandroot.utils.xml.DomUtils.getChildrenByTagName(element, "album");
		for (final Element albumElement : albumElements) {
			albums.add(new Album(albumElement));
		}
		this.setEntries(albums);
	}

}
