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

import java.io.Serializable;

import net.dontdrinkandroot.lastfm.api.xml.DomUtils;

import org.w3c.dom.Element;


public class Session implements Serializable {

	private static final long serialVersionUID = 1L;

	private String username;

	private String key;

	private Boolean subscriber;


	/**
	 * Create a Session from a dom4j Element.
	 * 
	 * @param root
	 *            The dom4j Element to create the Session object from.
	 */
	public Session(final Element root) {

		this.username = DomUtils.toNonEmptyString(DomUtils.getChildByTagName(root, "name"));
		this.key = DomUtils.toNonEmptyString(DomUtils.getChildByTagName(root, "key"));
		this.subscriber = DomUtils.toBoolean(DomUtils.getChildByTagName(root, "subscriber"));
	}


	public Session(String username, String key, boolean subscriber) {

		this.username = username;
		this.key = key;
		this.subscriber = subscriber;
	}


	public final String getUsername() {

		return this.username;
	}


	public final void setUsername(final String username) {

		this.username = username;
	}


	public final String getKey() {

		return this.key;
	}


	public final void setKey(final String key) {

		this.key = key;
	}


	public final Boolean getSubscriber() {

		return this.subscriber;
	}


	public final void setSubscriber(final Boolean subscriber) {

		this.subscriber = subscriber;
	}


	public final boolean isSubscriber() {

		if (this.subscriber == null) {
			return false;
		}
		return this.subscriber;
	}

}
