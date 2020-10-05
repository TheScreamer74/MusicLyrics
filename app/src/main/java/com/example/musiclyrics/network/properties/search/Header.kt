package com.example.musiclyrics.network.properties.search

data class Root(
    val message: Message
)

data class Message(
    val header: Header,
    val body: Body
)

data class Header(
    val status_code: Long,
    val execute_time: Double,
    val available: Long
)