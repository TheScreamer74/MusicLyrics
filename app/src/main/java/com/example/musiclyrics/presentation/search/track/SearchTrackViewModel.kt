package com.example.musiclyrics.presentation.search.track


import android.util.Log
import android.view.View
import androidx.lifecycle.*
import com.example.musiclyrics.API_KEY
import com.example.musiclyrics.network.MusicXMatch
import com.example.musiclyrics.model.track.Track
import com.example.musiclyrics.model.track.TrackList
import com.example.musiclyrics.presentation.ressources.ACRCloudViewModel
import com.firebase.ui.auth.AuthUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinApiExtension


@KoinApiExtension
class SearchTrackViewModel: ACRCloudViewModel() {

    private val _track = MutableLiveData<Track>()
    val track: LiveData<Track>
        get() = _track

    private val _tracks = MutableLiveData<List<TrackList>>()
    val tracks: LiveData<List<TrackList>>
        get() = _tracks


    //get a track from musicxmatch using any keyword
    fun search(text: String) {
        viewModelScope.launch {
            val getTrackListDeferred = MusicXMatch.retrofitService.searchAny(text, API_KEY)
            try {
                val result = withContext(Dispatchers.IO) { getTrackListDeferred.await() }
                _tracks.value =
                    withContext(Dispatchers.Default) { result.message.body.track_list.sortedBy { it.track.trackRating } }!!
            } catch (t: Throwable) {
                Log.i("LocationListViewModel", t.message ?: "rien")
            }
        }
    }

    //get a track from musixmatch using isrc
    override fun getTrack(isrc: String){
        viewModelScope.launch {
            val getTrackDeferred = MusicXMatch.retrofitService.getTrack(isrc, API_KEY)
            try{
                val result = withContext(Dispatchers.IO) { getTrackDeferred.await() }
                _track.value =
                    withContext(Dispatchers.Default) { result.message.body.track }!!
                callback.onEventCompleted()
            } catch (t: Throwable) {
                callback.onEventFailed()
                Log.i("LocationListViewModel", t.message ?: "rien")
            }
        }
    }

    fun disconnect(it: View): Boolean {
        AuthUI.getInstance()
            .signOut(it.context)
            .addOnCompleteListener {}

        return true
    }
}