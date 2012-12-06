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
//import java.io.Serializable;
//
//import org.dom4j.Element;
//
//import net.dontdrinkandroot.lastfm.api.queries.AuthenticatedGetQuery;
//import net.dontdrinkandroot.lastfm.api.queries.UnauthenticatedGetQuery;
//import net.dontdrinkandroot.lastfm.api.queries.impl.AbstractAuthenticatedGetQuery;
//import net.dontdrinkandroot.lastfm.api.queries.impl.AbstractUnauthenticatedGetQuery;
//
public class Playlist {
//
//	/**
//	 * Add a track to a Last.fm user's playlist
//	 * @param playlistID The ID of the playlist - this is available in user.getPlaylists. (Required).
//	 * @param track The track name to add to the playlist. (Required).
//	 * @param artist The artist name that corresponds to the track to be added. (Required).
//	 * @return 
//	 */
//	public static AuthenticatedGetQuery<Serializable> addTrack(
//		final Object playlistID,
//		final Object track,
//		final Object artist
//	) {
//		//TODO: implement
//		if (1 == 1) throw new RuntimeException("Not implemented");
//		AuthenticatedGetQuery<Serializable> query
//			 = new AbstractAuthenticatedGetQuery<Serializable>("playlist.addTrack") {
//				@Override
//				public Serializable parse(final Element root) {
//					return null;
//				}
//		};
//		query.addParameter("playlistID", playlistID);
//		query.addParameter("track", track);
//		query.addParameter("artist", artist);
//		return query;
//	}
//
//
//	/**
//	 * Create a Last.fm playlist on behalf of a user
//	 * @param title Title for the playlist (Optional).
//	 * @param description Description for the playlist (Optional).
//	 * @return 
//	 */
//	public static AuthenticatedGetQuery<Serializable> create(
//		final Object title,
//		final Object description
//	) {
//		//TODO: implement
//		if (1 == 1) throw new RuntimeException("Not implemented");
//		AuthenticatedGetQuery<Serializable> query
//			 = new AbstractAuthenticatedGetQuery<Serializable>("playlist.create") {
//				@Override
//				public Serializable parse(final Element root) {
//					return null;
//				}
//		};
//		query.addParameter("title", title);
//		query.addParameter("description", description);
//		return query;
//	}
//
//
//	/**
//	 * Fetch XSPF playlists using a lastfm playlist url.
//	 * @param playlistURL A lastfm protocol playlist url ('lastfm://playlist/...') . See 'playlists' section for more information. (Required).
//	 * @return 
//	 */
//	public static UnauthenticatedGetQuery<Serializable> fetch(
//		final Object playlistURL
//	) {
//		//TODO: implement
//		if (1 == 1) throw new RuntimeException("Not implemented");
//		UnauthenticatedGetQuery<Serializable> query
//			 = new AbstractUnauthenticatedGetQuery<Serializable>("playlist.fetch") {
//				@Override
//				public Serializable parse(final Element root) {
//					return null;
//				}
//		};
//		query.addParameter("playlistURL", playlistURL);
//		return query;
//	}
//
//
}
