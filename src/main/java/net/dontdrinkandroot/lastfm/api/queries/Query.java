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
package net.dontdrinkandroot.lastfm.api.queries;

import java.io.Serializable;
import java.util.Map;

import org.w3c.dom.Element;


/**
 * Superinterface that has to be implemented by all queries.
 * 
 * @author Philip W. Sorst
 * 
 * @param <T>
 *            The return type of the query.
 */
public interface Query<T extends Serializable> {

	/**
	 * Get query parameters.
	 * 
	 * @return The query parameters.
	 */
	Map<String, String> getParameters();


	/**
	 * Get the last.fm method that is fetched by this query.
	 * 
	 * @return
	 */
	String getMethod();


	/**
	 * Add a parameter to the query parameters.
	 * 
	 * @param key
	 *            The name of the parameter.
	 * @param value
	 *            The value of this parameter. This expects that the object has a reasonable
	 *            toString() method, otherwise the String has to be passed manually instead of the
	 *            object.
	 */
	void addParameter(String key, Object value);


	/**
	 * Parse the result and return the Object model.
	 * 
	 * @param root
	 *            Dom4j root element.
	 * @return The query result.
	 */
	T parse(Element root);
}
