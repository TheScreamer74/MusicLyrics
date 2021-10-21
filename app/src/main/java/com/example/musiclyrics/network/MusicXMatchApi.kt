package com.example.musiclyrics.network

import com.example.musiclyrics.model.track.Root
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

sealed interface MusicXMatchApi {
    interface SearchTrack {
        companion object {
            const val SEARCH_TRACK_URL = "track.search"
        }

        @GET(SEARCH_TRACK_URL)
        suspend fun getTrackNamed(@Query("q_track") queryTrack: String, @Query("apikey") apiKey:String): Response<Root>
    }
    interface GetTrackFromISRC {
        companion object {
            const val GET_TRACK_URL = "track.get"
        }

        @GET(GET_TRACK_URL)
        suspend fun getTrackWithISRC(@Query("track_isrc") isrcId: String, @Query("apikey") apiKey:String): Response<com.example.musiclyrics.model.get.Root>
    }
    interface GetAlbum {
        companion object {
            const val GET_ALBUM_URL = "album.get"
        }
        @GET(GET_ALBUM_URL)
        suspend fun getAlbumWithId(@Query("album_id") albumId: Long, @Query("apikey") apiKey: String): Response<com.example.musiclyrics.model.result.Root>
    }
    interface GetLyrics {
        companion object {
            const val GET_LYRICS_URL = "track.lyrics.get"
        }

        @GET(GET_LYRICS_URL)
        suspend fun getLyricsWithTrackId(@Query("track_id") trackId : Long, @Query("apikey") apiKey:String): Response<com.example.musiclyrics.model.lyrics.Root>
    }
}