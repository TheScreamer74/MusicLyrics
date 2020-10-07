package com.example.musiclyrics.network.properties.search.track

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MusicGenreList(
    val music_genre: MusicGenre
) : Parcelable