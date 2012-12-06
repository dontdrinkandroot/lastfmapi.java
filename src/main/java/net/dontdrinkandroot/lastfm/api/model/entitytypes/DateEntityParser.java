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
package net.dontdrinkandroot.lastfm.api.model.entitytypes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import net.dontdrinkandroot.lastfm.api.model.parser.EntityParser;
import net.dontdrinkandroot.utils.lang.time.DateUtils;
import net.dontdrinkandroot.utils.xml.DomUtils;

import org.w3c.dom.Element;


public class DateEntityParser implements EntityParser<DateEntity> {

	@Override
	public void parse(final Element element, final DateEntity entity) {

		final Element dateElement = DomUtils.getChildByTagName(element, "date");
		final Long uts = DomUtils.toLong(dateElement, "uts");
		if (uts != null) {
			entity.setDate(new Date(uts * DateUtils.MILLIS_PER_SECOND));
		} else {
			final String dateText = DomUtils.getElementText(element, "date");
			if (dateText != null) {
				final SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss", Locale.UK);
				sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
				try {
					entity.setDate(sdf.parse(dateText));
				} catch (final ParseException e) {
					/* Swallow */
				}
			}
		}

	}

}
