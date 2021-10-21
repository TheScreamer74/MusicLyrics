package com.example.musiclyrics.model.track

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MusicGenreList(
    @Json(name = "music_genre") val musicGenre: MusicGenre
) : Parcelable