package com.example.musiclyrics.model.track

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class Root(
    val message: Message
)

data class Message(
    val header: Header,
    val body: Body
)

@Parcelize
data class Body(
    val track_list: List<TrackList>
) : Parcelable

data class Header(
    val status_code: Long,
    val execute_time: Double,
    val available: Long
)

@Parcelize
data class TrackList(
    val track: Track
) : Parcelable
