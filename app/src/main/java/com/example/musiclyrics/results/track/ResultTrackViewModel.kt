package com.example.musiclyrics.results.track

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musiclyrics.API_KEY
import com.example.musiclyrics.network.MusicXMatch
import com.example.musiclyrics.network.properties.lyrics.Lyrics
import com.example.musiclyrics.network.properties.result.Album
import com.example.musiclyrics.network.properties.search.track.Track
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ResultTrackViewModel(track: Track) : ViewModel() {

    private val _track = MutableLiveData<Track>()
    val track : LiveData<Track>
        get() = _track

    private val _album = MutableLiveData<Album>()
    val album : LiveData<Album>
        get() = _album

    private val _lyrics = MutableLiveData<Lyrics>()
    val lyrics : LiveData<Lyrics>
        get() = _lyrics

    init {
        _track.value = track
        loadAlbumImage()
        loadLyrics()
    }

    private fun loadAlbumImage(){
        viewModelScope.launch {
            val getAlbumDeferred = MusicXMatch.retrofitService.getImageAlbum(track.value!!.albumId,
                API_KEY)
            try {
                val result = getAlbumDeferred.await()
                _album.value = result.message.body.album

            } catch (t: Throwable){
                Log.i("LocationListViewModel", t.message ?: "rien")
            }
        }
    }

    private fun loadLyrics(){
        viewModelScope.launch {
            val getLyricsDeferred = MusicXMatch.retrofitService.getLyrics(track.value!!.trackId, API_KEY)
            try {
                val result = getLyricsDeferred.await()
                if (result.message.body.lyrics.lyricsBody == "") {
                    result.message.body.lyrics.lyricsBody = "Oops il semblerait qu'il n'y ait pas de paroles pour cette chanson"
                }
                _lyrics.value = result.message.body.lyrics
            }
            catch (t: Throwable){
                Log.i("LocationListViewModel", t.message ?: "rien")
            }
        }
    }


}