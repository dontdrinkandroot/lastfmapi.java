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

import net.dontdrinkandroot.lastfm.api.model.entitytypes.NameEntity;
import net.dontdrinkandroot.lastfm.api.xml.DomUtils;

import org.w3c.dom.Element;


public class NameEntityParser implements EntityParser<NameEntity> {

	@Override
	public final void parse(final Element element, final NameEntity entity) {

		/*
		 * Sometimes the entites are only complextypesimplecontent, the name is then the element
		 * text
		 */
		String name = DomUtils.toNonEmptyString(DomUtils.getChildByTagName(element, "name"));
		if (name == null) {
			name = DomUtils.toNonEmptyString(element);
		}
		entity.setName(name);
	}
}
