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

import net.dontdrinkandroot.lastfm.api.xml.DomUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;


/**
 * Default generic implementation of a {@link PaginatedResult}.
 * 
 * @author Philip W. Sorst
 * 
 * @param <T>
 *            Type of the actual result.
 */
public class GenericPaginatedResult<T> implements PaginatedResult<T> {

	private static final long serialVersionUID = 2L;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private int total;

	private int page;

	private int perPage;

	private int totalPages;

	private T entries;


	/**
	 * Create a paginated result from an XML {@link Element}.
	 * 
	 * @param element
	 *            The element to convert.
	 */
	public GenericPaginatedResult(final Element element) {

		final Element openSearchQuery = DomUtils.getChildByTagName(element, "opensearch:Query");
		if (openSearchQuery != null) {

			this.fromOpenSearch(element);

		} else {

			Integer page = DomUtils.toInteger(element, "page");
			// TODO: this does not work for tag.getTopArtists e.g. Watch for changes.
			if (page == null) {
				this.logger.warn("Page not found");
				page = 0;
			}
			this.setPage(page);

			Integer perPage = DomUtils.toInteger(element, "perPage");
			// TODO: this does not work for tag.getTopArtists e.g. Watch for changes.
			if (perPage == null) {
				this.logger.warn("Per Page not found");
				perPage = 0;
			}
			this.setPerPage(perPage);

			Integer totalPages = DomUtils.toInteger(element, "totalPages");
			// TODO: this does not work for tag.getTopArtists e.g. Watch for changes.
			if (totalPages == null) {
				this.logger.warn("Total Pages not found");
				totalPages = 0;
			}
			this.setTotalPages(totalPages);

			Integer total = DomUtils.toInteger(element, "total");
			/* Sometimes total is missing at all, use the upper bound instead */
			if (total == null) {
				total = totalPages * perPage;
			}
			this.setTotal(total);
		}
	}


	private void fromOpenSearch(final Element element) {

		final Element openSearchQuery = DomUtils.getChildByTagName(element, "opensearch:Query");
		final Integer startPage = DomUtils.toInteger(openSearchQuery, "startPage");
		this.setPage(startPage);

		final Element openSearchTotalResults = DomUtils.getChildByTagName(element, "opensearch:totalResults");
		final Integer totalResults = DomUtils.toInteger(openSearchTotalResults);
		this.setTotal(totalResults);

		// final Element openSearchStartIndex = DomUtils.getChildByTagName(element,
		// "opensearch:startIndex");

		final Element openSearchItemsPerPage = DomUtils.getChildByTagName(element, "opensearch:itemsPerPage");
		final Integer itemsPerPage = DomUtils.toInteger(openSearchItemsPerPage);
		this.setPerPage(itemsPerPage);

		this.setTotalPages((int) Math.ceil(totalResults / (double) this.perPage));
	}


	@Override
	public final T getEntries() {

		return this.entries;
	}


	public final void setEntries(final T entries) {

		this.entries = entries;
	}


	@Override
	public final boolean hasMorePages() {

		return this.page < this.totalPages;
	}


	@Override
	public final int getNextPage() {

		return this.page + 1;
	}


	@Override
	public final int getTotal() {

		return this.total;
	}


	public final void setTotal(final int total) {

		this.total = total;
	}


	@Override
	public final int getPage() {

		return this.page;
	}


	public final void setPage(final int page) {

		this.page = page;
	}


	@Override
	public final int getPerPage() {

		return this.perPage;
	}


	public final void setPerPage(final int perPage) {

		this.perPage = perPage;
	}


	@Override
	public final int getTotalPages() {

		return this.totalPages;
	}


	public final void setTotalPages(final int totalPages) {

		this.totalPages = totalPages;
	}

}
