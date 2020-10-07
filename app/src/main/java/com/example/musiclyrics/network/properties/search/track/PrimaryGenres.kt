package com.example.musiclyrics.network.properties.search.track

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PrimaryGenres(
    val music_genre_list: List<MusicGenreList>
) : Parcelable