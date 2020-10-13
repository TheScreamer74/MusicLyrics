package com.example.musiclyrics.network.properties.get

import com.example.musiclyrics.network.properties.lyrics.Header
import com.example.musiclyrics.network.properties.search.track.Track

data class Root(
    val message: Message
)

data class Message(
    val header: Header,
    val body: Body
)

data class Body(
    val track: Track
)