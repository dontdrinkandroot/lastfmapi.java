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
package net.dontdrinkandroot.lastfm.api.model.parser;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import net.dontdrinkandroot.lastfm.api.model.ImageSize;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.ImageEntity;
import net.dontdrinkandroot.lastfm.api.xml.DomUtils;
import net.dontdrinkandroot.utils.lang.StringUtils;

import org.w3c.dom.Element;


public class ImageEntityParser implements EntityParser<ImageEntity> {

	@Override
	public void parse(final Element element, final ImageEntity entity) {

		final Map<ImageSize, URL> ret = new HashMap<ImageSize, URL>();

		for (final Element imageElement : DomUtils.getChildrenByTagName(element, "image")) {

			try {

				final String urlText = imageElement.getTextContent();
				if (urlText != null && !urlText.equals("")) {
					final URL url = new URL(urlText);
					ImageSize size;
					final String sizeString = StringUtils.trimToNull(imageElement.getAttribute("size"));
					if (sizeString != null) {
						size = ImageSize.parseImageSize(imageElement.getAttribute("size"));
					} else {
						/* Size not defined in scrobblesource, only one image, put it to medium */
						size = ImageSize.MEDIUM;
					}
					ret.put(size, url);
				}

			} catch (final MalformedURLException e) {
				e.printStackTrace();
			}

		}

		entity.setImages(ret);
	}

}
