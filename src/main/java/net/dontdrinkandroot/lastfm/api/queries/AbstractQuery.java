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
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Implementation of the common methods of a {@link Query}.
 *
 * @author Philip W. Sorst
 *
 * @param <T>
 *            The return type of the query.
 */
public abstract class AbstractQuery<T extends Serializable> implements Query<T> {

	private final Map<String, String> parameters = new HashMap<String, String>();


	public AbstractQuery(final String method) {

		this.parameters.put("method", method);
	}


	@Override
	public final Map<String, String> getParameters() {

		this.addAdditionalParameters();
		/* Return an ordered copy to support query building and prevent manipulation */
		final TreeMap<String, String> ret = new TreeMap<String, String>(this.parameters);

		return ret;
	}


	protected void addAdditionalParameters() {

	}


	@Override
	public final String getMethod() {

		return this.parameters.get("method");
	}


	@Override
	public final void addParameter(final String key, final Object value) {

		if (value != null) {
			this.parameters.put(key, value.toString());
		}
	}

}
