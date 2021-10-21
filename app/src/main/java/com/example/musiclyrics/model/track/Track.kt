package com.example.musiclyrics.model.track

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize



@Parcelize
data class Track(
    @Json(name = "track_id") val trackId : Long,
    @Json(name = "track_name") val trackName: String,
    @Json(name = "track_rating") val trackRating: Long,
    @Json(name = "commontrack_id") val commontrackId: Long,
    val instrumental: Long,
    val explicit: Long,
    @Json(name = "has_lyrics") val hasLyrics: Long,
    @Json(name = "has_subtitles") val hasSubtitles: Long,
    @Json(name = "has_richsync") val hasRichsync: Long,
    @Json(name = "num_favourite") val numFavourite: Long,
    @Json(name = "album_id") val albumId: Long,
    @Json(name = "album_name") val albumName: String,
    @Json(name = "artist_id") val artistId: Long,
    @Json(name = "artist_name") val artistName: String,
    @Json(name = "track_share_url") val trackShareUrl: String,
    @Json(name = "track_edit_url") val trackEditUrl: String,
    val restricted: Long,
    @Json(name = "updated_time") val updatedTime: String,
    @Json(name = "primary_genres") val primaryGenres: PrimaryGenres
) : Parcelable