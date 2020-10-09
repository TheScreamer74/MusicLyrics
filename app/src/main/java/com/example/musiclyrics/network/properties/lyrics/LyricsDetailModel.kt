package com.example.musiclyrics.network.properties.lyrics


data class Root(
    val message: Message
)

data class Message(
    val header: Header,
    val body: Body
)

data class Header(
    val status_code: Long,
    val execute_time: Double
)

data class Body(
    val lyrics: Lyrics
)

data class Lyrics(
    val lyrics_id: Long,
    val explicit: Long,
    var lyrics_body: String,
    val script_tracking_url: String,
    val pixel_tracking_url: String,
    val lyrics_copyright: String,
    val updated_time: String
)