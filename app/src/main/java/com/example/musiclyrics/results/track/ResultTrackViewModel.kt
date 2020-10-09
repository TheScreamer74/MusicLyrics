package com.example.musiclyrics.results.track

import android.util.Log
import android.view.animation.AnimationUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musiclyrics.MainActivity
import com.example.musiclyrics.R
import com.example.musiclyrics.network.MusicXMatch
import com.example.musiclyrics.network.properties.lyrics.Lyrics
import com.example.musiclyrics.network.properties.result.Album
import com.example.musiclyrics.network.properties.search.track.Track
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ResultTrackViewModel(track: Track) : ViewModel() {

    val API_KEY = "fb20e5b416b5d8f3bb484102abca1638"

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _Track = MutableLiveData<Track>()
    val Track : LiveData<Track>
        get() = _Track

    private val _Album = MutableLiveData<Album>()
    val Album : LiveData<Album>
        get() = _Album

    private val _Lyrics = MutableLiveData<Lyrics>()
    val Lyrics : LiveData<Lyrics>
        get() = _Lyrics

    init {
        _Track.value = track
        LoadAlbumImage()
        LoadLyrics()
    }

    fun LoadAlbumImage(){
        coroutineScope.launch {
            val getAlbumDeferred = MusicXMatch.retrofitService.getImageAlbum(Track.value!!.album_id,API_KEY)
            try {
                val result = getAlbumDeferred.await()
                _Album.value = result.message.body.album

            } catch (t: Throwable){
                Log.i("LocationListViewModel", t.message ?: "rien")
            }
        }
    }

    fun LoadLyrics(){
        coroutineScope.launch {
            val getLyricsDeferred = MusicXMatch.retrofitService.getLyrics(Track.value!!.track_id, API_KEY)
            try {
                val result = getLyricsDeferred.await()
                if (result.message.body.lyrics.lyrics_body == "") {
                    result.message.body.lyrics.lyrics_body = "Oops il semblerait qu'il n'y ait pas de paroles pour cette chanson"
                }
                _Lyrics.value = result.message.body.lyrics
            }
            catch (t: Throwable){
                Log.i("LocationListViewModel", t.message ?: "rien")
            }
        }
    }


}