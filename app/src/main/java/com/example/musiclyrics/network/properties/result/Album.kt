package com.example.musiclyrics.network.properties.result

import android.os.Parcelable
import com.example.musiclyrics.network.properties.search.track.PrimaryGenres
import kotlinx.android.parcel.Parcelize
import com.squareup.moshi.FromJson
import com.squareup.moshi.Json

@Parcelize
data class Album (
        val album_id: Long,
        @Json(name = "album_mbid") val albumMbid: String,
        val album_name: String,
        val album_rating: Long,
        val album_track_count: Long?,
        val album_release_date: String,
        val album_release_type: String?,
        val artist_id: Long,
        val artist_name: String,
        val primary_genres: PrimaryGenres,
        val album_pline: String,
        val album_copyright: String,
        val album_label: String,
        val updated_time: String,
        val album_coverart_100x100: String? = "https://firebasestorage.googleapis.com/v0/b/musiclyrics-56250.appspot.com/o/Ellipse.png?alt=media&token=0072237a-d91f-4805-ab37-4394e2ef22b8"
) : Parcelable