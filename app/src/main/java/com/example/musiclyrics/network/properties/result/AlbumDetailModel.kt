package com.example.musiclyrics.network.properties.result

import android.os.Parcelable
import com.squareup.moshi.Json
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
    @Json(name = "status_code") val statusCode: Long,
    @Json(name = "execute_time") val executeTime: Double
)

