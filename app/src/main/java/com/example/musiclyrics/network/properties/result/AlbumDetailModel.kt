package com.example.musiclyrics.network.properties.result

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
    val album: Album
) : Parcelable

data class Header(
    val status_code: Long,
    val execute_time: Double
)

