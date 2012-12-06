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

import java.io.Serializable;


/**
 * Wrapper class for paginated results so you can retrieve the status information to fetch the next
 * page.
 * 
 * @author Philip W. Sorst
 * 
 * @param <T>
 *            Type of the entities on the page.
 */
public interface PaginatedResult<T> extends Serializable {

	/**
	 * Get the entries of the current page.
	 */
	T getEntries();


	/**
	 * Check if the paginated result has more pages.
	 */
	boolean hasMorePages();


	/**
	 * Get the number of the next page.
	 */
	int getNextPage();


	/**
	 * Get the number of total results.
	 */
	int getTotal();


	/**
	 * Get the number of the current page.
	 */
	int getPage();


	/**
	 * Get the number of entries per page.
	 */
	int getPerPage();


	/**
	 * Get the number of total pages.
	 */
	int getTotalPages();

}
