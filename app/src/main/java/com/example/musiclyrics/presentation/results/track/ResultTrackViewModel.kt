package com.example.musiclyrics.presentation.results.track

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musiclyrics.API_KEY
import com.example.musiclyrics.model.lyrics.Lyrics
import com.example.musiclyrics.model.result.Album
import com.example.musiclyrics.model.track.Track
import com.example.musiclyrics.network.MusicXMatchApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@KoinApiExtension
class ResultTrackViewModel(track: Track) : ViewModel(), KoinComponent {

    private val _api: MusicXMatchApi.GetLyrics by inject()
    private val _api2: MusicXMatchApi.GetAlbum by inject()

    private val _track = MutableLiveData<Track>()
    val track: LiveData<Track>
        get() = _track

    private val _album = MutableLiveData<Album>()
    val album: LiveData<Album>
        get() = _album

    private val _lyrics = MutableLiveData<Lyrics>()
    val lyrics: LiveData<Lyrics>
        get() = _lyrics

    init {
        _track.value = track
        loadAlbumImage()
        if (track.hasLyrics != 0.toLong()) {
            loadLyrics()
        } else {
            _lyrics.value = Lyrics(
                0,
                0,
                "Oops il semblerait qu'il n'y ait pas de paroles pour cette chanson",
                "",
                "",
                "",
                ""
            )
        }
    }

    private fun loadAlbumImage() {
        viewModelScope.launch {
            val getAlbum = _api2.getAlbumWithId(track.value!!.albumId, API_KEY)
            try {
                val result = withContext(Dispatchers.IO) { getAlbum }
                Log.d("API_CALL", result.toString())
                _album.value = withContext(Dispatchers.Default) {
                        result.body()?.run {
                            message.body.album
                        }
                    }!!
            } catch (t: Throwable) {
                Log.i("ON ALBUM GET", t.message ?: "rien")
            }
        }
    }
    private fun loadLyrics() {
        viewModelScope.launch {
            val getLyrics = _api.getLyricsWithTrackId(track.value!!.trackId, API_KEY)
            try {
                val result = withContext(Dispatchers.IO) { getLyrics }
                Log.d("API_CALL", result.toString())
                result.body()?.run {
                    if (this.message.body.lyrics.lyricsBody.isBlank()) {
                        this.message.body.lyrics.lyricsBody = "Oops il semblerait qu'il n'y ait pas de paroles pour cette chanson"
                    }
                    _lyrics.value =
                        withContext(Dispatchers.Default) { this@run.message.body.lyrics }!!
                }
            } catch (t: Throwable) {
                Log.i("ON LYRICS GET", t.message ?: "rien")
            }
        }
    }
}