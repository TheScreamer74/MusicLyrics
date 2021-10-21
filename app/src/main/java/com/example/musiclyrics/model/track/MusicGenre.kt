package com.example.musiclyrics.model.track

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MusicGenre(
    @Json(name = "music_genre_id") val musicGenreId: Long,
    @Json(name = "music_genre_parent_id") val musicGenreParentId: Long,
    @Json(name = "music_genre_name") val musicGenreName: String,
    @Json(name = "music_genre_name_extended") val musicGenreNameExtended: String,
    @Json(name = "music_genre_vanity") val musicGenreVanity: String
) : Parcelable