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
package net.dontdrinkandroot.lastfm.api.ws;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.dontdrinkandroot.utils.lang.time.DateUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Enforce last.fm fetch limit. "You will not make more than 5 requests per originating IP address
 * per second, averaged over a 5 minute period".
 * 
 * @author Philip W. Sorst <philip@sorst.net>
 */
public class FetchLimitEnforcer {

	/** Maximum number of requests per second. */
	private static final int REQUESTS_PER_SECOND = 5;

	/** How long a request must take in average to comply to req/sec limitation */
	private static final long DURATION_PER_REQUEST = DateUtils.MILLIS_PER_SECOND
			/ FetchLimitEnforcer.REQUESTS_PER_SECOND;

	/** The duration within the fetch average has to be complied to. */
	private static final long COMPLIANCE_MINUTES = 5;

	private static final long MAX_REQUESTS_IN_COMPLIANCE = FetchLimitEnforcer.REQUESTS_PER_SECOND
			* DateUtils.SECONDS_PER_MINUTE
			* FetchLimitEnforcer.COMPLIANCE_MINUTES;

	/** Remember the most recent fetches to comply to fetch restrictions. */
	private final List<Long> recentFetches = new ArrayList<Long>();

	/** The logger */
	private final Logger logger = LoggerFactory.getLogger(FetchLimitEnforcer.class);


	/**
	 * Get the number of requests per second in the compliance interval.
	 * 
	 * @return Number of requests per second.
	 */
	public final synchronized double getWebRequestsPerSecond() {

		final long now = System.currentTimeMillis();

		/* Compute starting time of compliance interval */
		final long complianceStart = now - DateUtils.MILLIS_PER_MINUTE * FetchLimitEnforcer.COMPLIANCE_MINUTES;

		/*
		 * Iterate over recent fetches and count the ones within the compliance interval, delete the
		 * outdated
		 */
		final Iterator<Long> recentFetchesIterator = this.recentFetches.iterator();
		int count = 0;
		while (recentFetchesIterator.hasNext()) {
			final Long current = recentFetchesIterator.next();
			if (current < complianceStart) {
				recentFetchesIterator.remove();
			} else {
				count++;
			}
		}

		return count / (double) (FetchLimitEnforcer.COMPLIANCE_MINUTES * DateUtils.SECONDS_PER_MINUTE);
	}


	/**
	 * Block as long as it is necessary to comply with the request/second limit.
	 */
	public final synchronized void waitForTimeslot() {

		final long now = System.currentTimeMillis();
		/* Compute starting time of compliance interval */
		final long complianceStart = now - DateUtils.MILLIS_PER_MINUTE * FetchLimitEnforcer.COMPLIANCE_MINUTES;

		/*
		 * Iterate over recent fetches and count the ones in the compliance interval, delete the
		 * outdated
		 */
		final Iterator<Long> recentFetchesIterator = this.recentFetches.iterator();
		int count = 0;
		while (recentFetchesIterator.hasNext()) {
			final Long current = recentFetchesIterator.next();
			if (current < complianceStart) {
				recentFetchesIterator.remove();
			} else {
				count++;
			}
		}

		/*
		 * If there have been more requests than theoretically allowed in the compliance interval
		 * sleep as long as necessary.
		 */
		final long overheadRequests = count - FetchLimitEnforcer.MAX_REQUESTS_IN_COMPLIANCE;
		if (overheadRequests > 0) {
			try {
				final long sleeptime = overheadRequests * FetchLimitEnforcer.DURATION_PER_REQUEST;
				this.logger.debug("Sleeping for " + sleeptime);
				Thread.sleep(sleeptime);
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
		}

		/* Remember this request afer sleeping */
		this.recentFetches.add(System.currentTimeMillis());
	}

}
