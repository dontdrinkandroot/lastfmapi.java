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

import net.dontdrinkandroot.lastfm.api.model.entitytypes.BuyLinkEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.IsSearchEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.PriceEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.SupplierIconEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.SupplierNameEntity;

import org.w3c.dom.Element;


public class Affiliation extends LfmEntity
		implements SupplierNameEntity, PriceEntity, BuyLinkEntity, SupplierIconEntity, IsSearchEntity {

	private static final long serialVersionUID = 1L;

	private String supplierName;

	private Price price;

	private URL buyLink;

	private URL supplierIcon;

	private Boolean search;


	public Affiliation(final Element element) {

		super(element);
	}


	@Override
	public Boolean isSearch() {

		return this.search;
	}


	@Override
	public void setSearch(final Boolean search) {

		this.search = search;
	}


	@Override
	public URL getSupplierIcon() {

		return this.supplierIcon;
	}


	@Override
	public void setSupplierIcon(final URL supplierIcon) {

		this.supplierIcon = supplierIcon;
	}


	@Override
	public URL getBuyLink() {

		return this.buyLink;
	}


	@Override
	public void setBuyLink(final URL buyLink) {

		this.buyLink = buyLink;
	}


	@Override
	public Price getPrice() {

		return this.price;
	}


	@Override
	public void setPrice(final Price price) {

		this.price = price;
	}


	@Override
	public String getSupplierName() {

		return this.supplierName;
	}


	@Override
	public void setSupplierName(final String supplierName) {

		this.supplierName = supplierName;
	}

}
