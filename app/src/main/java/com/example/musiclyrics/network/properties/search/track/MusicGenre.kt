package com.example.musiclyrics.network.properties.search.track

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MusicGenre(
    val music_genre_id: Long,
    val music_genre_parent_id: Long,
    val music_genre_name: String,
    val music_genre_name_extended: String,
    val music_genre_vanity: String
) : Parcelable