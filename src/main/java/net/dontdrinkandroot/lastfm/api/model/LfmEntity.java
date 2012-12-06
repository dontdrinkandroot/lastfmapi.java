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
import java.lang.reflect.Method;

import net.dontdrinkandroot.lastfm.api.model.entitytypes.Entity;
import net.dontdrinkandroot.lastfm.api.model.parser.EntityParser;
import net.dontdrinkandroot.lastfm.api.model.parser.Parser;

import org.w3c.dom.Element;


public abstract class LfmEntity implements Serializable {

	private static final long serialVersionUID = 1L;


	protected LfmEntity() {

		/* Default constructor to create empty entities */
	}


	/**
	 * Create entity from an xml element.
	 * 
	 * @param element
	 *            The xml element to create the entity from.
	 */
	public LfmEntity(final Element element) {

		final Class<?>[] interfaces = this.getClass().getInterfaces();
		for (final Class<?> iface : interfaces) {
			if (Entity.class.isAssignableFrom(iface)) {
				final Parser parser = iface.getAnnotation(Parser.class);
				if (parser == null) {
					throw new RuntimeException("No Parser defined for " + iface.getName());
				}
				try {
					// TODO try to invoke the parse method directly with proper generics
					final EntityParser<?> entityParser = parser.value().newInstance();
					final Method parseMethod = parser.value().getMethod("parse", Element.class, Entity.class);
					parseMethod.invoke(entityParser, element, this);
				} catch (final Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

}
