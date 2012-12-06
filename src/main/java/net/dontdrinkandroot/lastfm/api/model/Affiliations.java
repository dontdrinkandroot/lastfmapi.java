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

import java.util.List;

import net.dontdrinkandroot.lastfm.api.model.entitytypes.DownloadAffiliationsEntity;
import net.dontdrinkandroot.lastfm.api.model.entitytypes.PhysicalAffiliationsEntity;

import org.w3c.dom.Element;


public class Affiliations extends LfmEntity implements PhysicalAffiliationsEntity, DownloadAffiliationsEntity {

	private static final long serialVersionUID = 1L;


	/**
	 * Construct Affiliations from a DOM {@link Element}.
	 * 
	 * @param element
	 *            The {@link Element} to parse.
	 */
	public Affiliations(final Element element) {

		super(element);
	}


	private List<Affiliation> physicalAffiliations;

	private List<Affiliation> downloadAffiliations;


	@Override
	public List<Affiliation> getPhysicalAffiliations() {

		return this.physicalAffiliations;
	}


	@Override
	public List<Affiliation> getDownloadAffiliations() {

		return this.downloadAffiliations;
	}


	@Override
	public void setDownloadAffiliations(final List<Affiliation> downloadAffiliations) {

		this.downloadAffiliations = downloadAffiliations;
	}


	@Override
	public void setPhysicalAffiliations(final List<Affiliation> physicalAffiliations) {

		this.physicalAffiliations = physicalAffiliations;
	}

}
