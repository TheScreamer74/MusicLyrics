package com.example.musiclyrics.network.properties.search.track

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PrimaryGenres(
    @Json(name = "music_genre_list") val musicGenreList: List<MusicGenreList>
) : Parcelable