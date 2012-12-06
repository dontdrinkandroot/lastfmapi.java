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
package net.dontdrinkandroot.lastfm.api.util;

import java.util.List;

import net.dontdrinkandroot.lastfm.api.LastfmWebServicesException;
import net.dontdrinkandroot.lastfm.api.model.Group;
import net.dontdrinkandroot.lastfm.api.model.User;
import net.dontdrinkandroot.lastfm.api.model.paginatedresult.PaginatedResult;
import net.dontdrinkandroot.lastfm.api.model.paginatedresult.GenericPaginatedResult;
import net.dontdrinkandroot.lastfm.api.ws.LastfmWebServices;


public class GroupMembersPaginatedFetcher implements PaginatedFetcher<List<User>> {

	private final String groupName;


	public GroupMembersPaginatedFetcher(final String groupName) {

		this.groupName = groupName;
	}


	@Override
	public final GenericPaginatedResult<List<User>> fetchNextPage(
			final LastfmWebServices ws,
			final PaginatedResult<List<User>> paginatedResult) throws LastfmWebServicesException {

		if (!paginatedResult.hasMorePages()) {
			throw new LastfmWebServicesException(LastfmWebServicesException.NO_MORE_PAGES, "No more pages");
		}

		return ws.fetch(Group.getMembers(this.groupName, paginatedResult.getPerPage(), paginatedResult.getNextPage()));
	}

}
