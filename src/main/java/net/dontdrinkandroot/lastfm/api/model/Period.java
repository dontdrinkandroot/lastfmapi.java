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

import javax.xml.bind.annotation.XmlEnumValue;

import net.dontdrinkandroot.utils.lang.StringUtils;


public enum Period {

	@XmlEnumValue("7day")
	SEVEN_DAY {

		@Override
		public String toString() {

			return "7day";
		}
	},

	@XmlEnumValue("1month")
	ONE_MONTH {

		@Override
		public String toString() {

			return "1month";
		}
	},

	@XmlEnumValue("3month")
	THREE_MONTH {

		@Override
		public String toString() {

			return "3month";
		}
	},

	@XmlEnumValue("6month")
	SIX_MONTH {

		@Override
		public String toString() {

			return "6month";
		}
	},

	@XmlEnumValue("12month")
	TWELVE_MONTH {

		@Override
		public String toString() {

			return "12month";
		}

	},

	@XmlEnumValue("overall")
	OVERALL {

		@Override
		public String toString() {

			return "overall";
		}
	};

	public String getString() {

		return this.toString();
	}


	public static Period fromString(final String s) {

		if (StringUtils.isEmpty(s)) {
			return Period.OVERALL;
		}

		if (s.toLowerCase().equals("7day") || s.toLowerCase().equals("seven_day")) {

			return Period.SEVEN_DAY;

		} else if (s.toLowerCase().equals("1month") || s.toLowerCase().equals("one_month")) {

			return Period.ONE_MONTH;

		} else if (s.toLowerCase().equals("3month") || s.toLowerCase().equals("three_month")) {

			return Period.THREE_MONTH;

		} else if (s.toLowerCase().equals("6month") || s.toLowerCase().equals("six_month")) {

			return Period.SIX_MONTH;

		} else if (s.toLowerCase().equals("12month") || s.toLowerCase().equals("twelve_month")) {

			return Period.TWELVE_MONTH;
		}

		return Period.OVERALL;
	}

}
