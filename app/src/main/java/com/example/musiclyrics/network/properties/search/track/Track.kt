package com.example.musiclyrics.network.properties.search.track

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize



@Parcelize
data class Track(
    val track_id: Long,
    val track_name: String,
    val track_rating: Long,
    val commontrack_id: Long,
    val instrumental: Long,
    val explicit: Long,
    val has_lyrics: Long,
    val has_subtitles: Long,
    val has_richsync: Long,
    val num_favourite: Long,
    val album_id: Long,
    val album_name: String,
    val artist_id: Long,
    val artist_name: String,
    val track_share_url: String,
    val track_edit_url: String,
    val restricted: Long,
    val updated_time: String,
    val primary_genres: PrimaryGenres
) : Parcelable