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
package net.dontdrinkandroot.lastfm.api.model.xspf;

import org.w3c.dom.Element;

public class XspfLink {

	private String rel;
	private String content;


	public XspfLink(final Element linkElement) {
		this.content = linkElement.getTextContent();
		this.rel = linkElement.getAttribute("rel");
	}


	public String getContent() {
		return this.content;
	}


	public String getRel() {
		return this.rel;
	}



	public void setContent(final String content) {
		this.content = content;
	}



	public void setRel(final String rel) {
		this.rel = rel;
	}

}
