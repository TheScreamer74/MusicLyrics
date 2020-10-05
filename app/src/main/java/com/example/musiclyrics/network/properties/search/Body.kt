package com.example.musiclyrics.network.properties.search


data class Body(
    val track_list: List<TrackList>
)

data class TrackList(
    val track: Track
)

data class Track(
    val track_id: Long,
    val track_name: String,
    val track_name_translation_list: List<Any>,
    val track_rating: Long,
    val commontrack_id: Long,
    val instrumental: Long,
    val explicit: Long,
    val has_lyrics: Long,
    val has_subtitles: Long,
    val has_richsync: Long,
    val num_favourite: Long,
    val album_id: Long,
    val album_name: String,
    val artist_id: Long,
    val artist_name: String,
    val track_share_url: String,
    val track_edit_url: String,
    val restricted: Long,
    val updated_time: String,
    val primary_genres: PrimaryGenres
)

data class PrimaryGenres(
    val music_genre_list: List<MusicGenreList>
)

data class MusicGenreList(
    val music_genre: MusicGenre
)

data class MusicGenre(
    val music_genre_id: Long,
    val music_genre_parent_id: Long,
    val music_genre_name: String,
    val music_genre_name_extended: String,
    val music_genre_vanity: String
)