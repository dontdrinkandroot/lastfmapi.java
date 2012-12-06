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

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;


@XmlEnum
public enum Gender {

	@XmlEnumValue("m")
	MALE {

		@Override
		public String toString() {

			return "m";
		}
	},

	@XmlEnumValue("f")
	FEMALE {

		@Override
		public String toString() {

			return "f";
		}
	};

	public static Gender fromString(final String s) {

		if (s == null) {
			return null;
		}

		String lcString = s.toLowerCase();

		if (lcString.equals("m") || lcString.equals("male")) {
			return MALE;
		} else if (lcString.equals("f") || lcString.equals("female")) {
			return FEMALE;
		}

		return null;
	}
}