package com.example.musiclyrics.network.properties.result

import android.os.Parcelable
import com.example.musiclyrics.network.properties.search.track.PrimaryGenres
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Album (
        val album_id: Long,
        val album_mbid: String,
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
        val album_coverart_100x100: String?
) : Parcelable