package com.example.musiclyrics.network.properties.lyrics

import com.squareup.moshi.Json


data class Root(
    val message: Message
)

data class Message(
    val header: Header,
    val body: Body
)

data class Header(
    @Json(name = "status_code") val status_code: Long,
    @Json(name = "execute_time") val execute_time: Double
)

data class Body(
    val lyrics: Lyrics
)

data class Lyrics(
    @Json(name = "lyrics_id") val lyricsId: Long,
    val explicit: Long,
    @Json(name = "lyrics_body") var lyricsBody: String,
    @Json(name = "script_tracking_url") val scriptTrackingUrl: String,
    @Json(name = "pixel_tracking_url") val pixelTrackingUrl: String,
    @Json(name = "lyrics_copyright") val lyricsCopyright: String,
    @Json(name = "updated_time") val updatedTime: String
)