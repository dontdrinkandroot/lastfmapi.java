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

import java.util.ArrayList;
import java.util.List;

import net.dontdrinkandroot.lastfm.api.model.Tag;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.TagsEntity;
import net.dontdrinkandroot.lastfm.api.xml.DomUtils;

import org.w3c.dom.Element;


public class TagsEntityParser implements EntityParser<TagsEntity> {

	@Override
	public void parse(final Element element, final TagsEntity entity) {

		Element tagsEl = DomUtils.getChildByTagName(element, "toptags");
		if (tagsEl == null) {
			tagsEl = DomUtils.getChildByTagName(element, "tags");
		}
		if (tagsEl != null) {
			final List<Element> tagElements = DomUtils.getChildrenByTagName(tagsEl, "tag");
			if (!tagElements.isEmpty()) {
				final List<Tag> tags = new ArrayList<Tag>();
				for (final Element tagElement : tagElements) {
					tags.add(new Tag(tagElement));
				}
				entity.setTags(tags);
			}
		}
	}

}
