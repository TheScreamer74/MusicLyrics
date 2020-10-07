package com.example.musiclyrics.network.properties.search

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PrimaryGenres(
    val music_genre_list: List<MusicGenreList>
) : Parcelable