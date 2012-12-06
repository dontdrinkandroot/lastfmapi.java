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

import net.dontdrinkandroot.lastfm.api.model.entitytypes.AmountEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.CurrencyEntity;

import org.w3c.dom.Element;


public class Price extends LfmEntity implements CurrencyEntity, AmountEntity {

	private static final long serialVersionUID = 1L;

	private String currency;

	private Float amount;


	/**
	 * Creates a {@link Price} from a DOM {@link Element}.
	 * 
	 * @param element
	 *            The {@link Element} to parse.
	 */
	public Price(final Element element) {

		super(element);
	}


	@Override
	public Float getAmount() {

		return this.amount;
	}


	@Override
	public void setAmount(final Float amount) {

		this.amount = amount;
	}


	@Override
	public String getCurrency() {

		return this.currency;
	}


	@Override
	public void setCurrency(final String currency) {

		this.currency = currency;
	}

}
