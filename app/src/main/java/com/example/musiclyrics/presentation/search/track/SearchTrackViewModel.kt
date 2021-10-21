package com.example.musiclyrics.presentation.search.track


import android.util.Log
import android.view.View
import androidx.lifecycle.*
import com.example.musiclyrics.API_KEY
import com.example.musiclyrics.model.track.Track
import com.example.musiclyrics.model.track.TrackList
import com.example.musiclyrics.network.MusicXMatchApi
import com.example.musiclyrics.presentation.ressources.ACRCloudViewModel
import com.firebase.ui.auth.AuthUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.inject


@KoinApiExtension
class SearchTrackViewModel: ACRCloudViewModel() {

    //TODO : check if i can regroup _api calls
    private val _api : MusicXMatchApi.SearchTrack by inject()
    private val _api2: MusicXMatchApi.GetTrackFromISRC by inject()

    private val _track = MutableLiveData<Track>()
    val track: LiveData<Track>
        get() = _track

    private val _tracks = MutableLiveData<List<TrackList>>()
    val tracks: LiveData<List<TrackList>>
        get() = _tracks


    //get a track from musicxmatch using any keyword
    fun search(text: String) {
    viewModelScope.launch {
        val getTrackList = _api.getTrackNamed(text, API_KEY)
        try {
            val result = withContext(Dispatchers.IO) { getTrackList }
            Log.d("API_CALL", result.toString())
            _tracks.value = withContext(Dispatchers.Default) { result.body()?.message?.body?.track_list }!!
        } catch (t: Throwable) {
            Log.i("ON TRACK LIST GET", t.message ?: "rien")
        }
    }
    }
    //get a track from musixmatch using isrc
    override fun getTrack(isrc: String){
        viewModelScope.launch {
            val getTrack = _api2.getTrackWithISRC(isrc, API_KEY)
            try {
                val result = withContext(Dispatchers.IO) { getTrack }
                Log.d("API_CALL", result.toString())
                _track.value = withContext(Dispatchers.Default) { result.body()?.message?.body?.track }!!
                callback.onEventCompleted()
            } catch (t: Throwable) {
                callback.onEventFailed()
                Log.i("ON TRACK ISRC GET", t.message ?: "rien")
            }
        }
        /*
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
        }*/
    }

    fun disconnect(it: View): Boolean {
        AuthUI.getInstance()
            .signOut(it.context)
            .addOnCompleteListener {}

        return true
    }
}