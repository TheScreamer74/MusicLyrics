package com.example.musiclyrics.model.get

import com.example.musiclyrics.model.lyrics.Header
import com.example.musiclyrics.model.track.Track

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