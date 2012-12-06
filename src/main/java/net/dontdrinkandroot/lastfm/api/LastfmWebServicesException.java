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
package net.dontdrinkandroot.lastfm.api;

/* STATUS CODES:
 * 2 : Invalid service -This service does not exist
 * 3 : Invalid Method - No method with that name in this package
 * 4 : Authentication Failed - You do not have permissions to access the service
 * 4 : Invalid authentication token supplied
 * 5 : Invalid format - This service doesn't exist in that format
 * 6 : Invalid parameters - Your request is missing a required parameter
 * 7 : Invalid resource specified
 * 8 : There was an error granting the request token. Please try again later
 * 9 : Invalid session key - Please re-authenticate
 * 10 : Invalid API key - You must be granted a valid key by last.fm
 * 11 : Service Offline - This service is temporarily offline. Try again later.
 * 12 : Subscription Error - The user needs to be subscribed in order to do that
 * 12 : Subscribers Only - This station is only available to paid last.fm subscribers
 * 13 : Invalid method signature supplied
 * 14 : This token has not been authorized
 * 15 : This token has expired
 * 18 : This user has no free radio plays left. Subscription required.
 * 20 : Not Enough Content - There is not enough content to play this station
 * 21 : Not Enough Members - This group does not have enough members for radio
 * 22 : Not Enough Fans - This artist does not have enough fans for for radio
 * 23 : Not Enough Neighbours - There are not enough neighbours for radio
 * 100 : Unmarshalling document failed
 * 101 : Opening connection failed
 * 102 : Writing post data failed
 * 103 : Unsupported protocol
 * 104 : Stream was null
 * 105 : Cache error
 * 106 : Too many requests
 * 107 : Reading document failed
 * 108 : No more pages
 * */

/**
 * @author Philip Sorst
 */
public class LastfmWebServicesException extends Exception {

	private static final long serialVersionUID = 1L;

	public static final int INVALID_TOKEN = 4;

	public static final int INVALID_PARAMETERS = 6;

	public static final int INVALID_PLAYLIST_ID = 7;

	public static final int INVALID_SESSIONKEY = 9;

	public static final int INVALID_METHOD_SIGNATURE = 13;

	public static final int UNMARSHALLING_FAILED = 100;

	public static final int OPENING_CONNECTION_FAILED = 101;

	public static final int WRITING_POST_DATA_FAILED = 102;

	public static final int UNSUPPORTED_PROTOCOL = 103;

	public static final int STREAM_WAS_NULL = 104;

	public static final int CACHE_ERROR = 105;

	public static final int TOO_MANY_REQUESTS = 106;

	public static final int READING_DOC_FAILED = 107;

	public static final int NO_MORE_PAGES = 108;

	private int code;


	public LastfmWebServicesException(final int code, final String reason) {

		super(reason);
		this.code = code;
	}


	public LastfmWebServicesException(final int code, final String reason, final Throwable t) {

		super(reason, t);
		this.code = code;
	}


	public final int getCode() {

		return this.code;
	}


	public final void setCode(final int code) {

		this.code = code;
	}

}
