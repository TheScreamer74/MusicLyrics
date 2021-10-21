package com.example.musiclyrics.model.result

import android.os.Parcelable
import com.example.musiclyrics.model.track.PrimaryGenres
import kotlinx.android.parcel.Parcelize
import com.squareup.moshi.Json

@Parcelize
data class Album (
    @Json(name = "album_id") val albumId: Long,
    @Json(name = "album_mbid") val albumMbid: String,
    @Json(name = "album_name") val albumName: String,
    @Json(name = "album_rating") val albumRating: Long,
    @Json(name = "album_track_count") val albumTrackCount: Long?,
    @Json(name = "album_release_date") val albumReleaseDate: String,
    @Json(name = "album_release_type") val albumReleaseType: String?,
    @Json(name = "artist_id") val artistId: Long,
    @Json(name = "artist_name") val artistName: String,
    @Json(name = "primary_genres") val primaryGenres: PrimaryGenres,
    @Json(name = "album_pline") val albumPline: String,
    @Json(name = "album_copyright") val albumCopyright: String,
    @Json(name = "album_label") val albumLabel: String,
    @Json(name = "updated_time") val updatedTime: String,
    @Json(name = "album_coverart_100x100") val albumCoverArt100x100: String? = "https://firebasestorage.googleapis.com/v0/b/musiclyrics-56250.appspot.com/o/Ellipse.png?alt=media&token=0072237a-d91f-4805-ab37-4394e2ef22b8"
) : Parcelable