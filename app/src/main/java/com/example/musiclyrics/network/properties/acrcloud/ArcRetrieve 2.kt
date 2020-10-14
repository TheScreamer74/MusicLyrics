package com.example.musiclyrics.network.properties.acrcloud

data class ArcRetrieve(
    val metadata: Metadata
)

data class Metadata (
    val music: Music
)

data class Music(
    val external_ids: ExternalsIds
)

data class ExternalsIds(
    val isrc: String
)