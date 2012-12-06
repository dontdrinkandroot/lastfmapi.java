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

import net.dontdrinkandroot.lastfm.api.model.User;
import net.dontdrinkandroot.lastfm.api.xml.DomUtils;

import org.w3c.dom.Element;


public class UsersPaginatedResult extends GenericPaginatedResult<List<User>> {

	public UsersPaginatedResult(Element element) {

		super(element);

		final ArrayList<User> users = new ArrayList<User>();
		final List<Element> userElements = DomUtils.getChildrenByTagName(element, "user");
		for (final Element neighbourElement : userElements) {
			users.add(new User(neighbourElement));
		}
		this.setEntries(users);
	}

}
