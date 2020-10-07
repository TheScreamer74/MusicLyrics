package com.example.musiclyrics.search.track

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musiclyrics.network.MusicXMatch
<<<<<<< HEAD
import com.example.musiclyrics.network.properties.search.track.TrackList
=======
import com.example.musiclyrics.network.properties.search.TrackList
import com.firebase.ui.auth.AuthUI
>>>>>>> ft/logIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SearchTrackViewModel : ViewModel() {

    val API_KEY = "fb20e5b416b5d8f3bb484102abca1638"

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _Tracks = MutableLiveData<List<TrackList>>()
    val Tracks : LiveData<List<TrackList>>
        get() = _Tracks

    fun Search(text: String) {
        coroutineScope.launch {
            var getTrackListDeferred = MusicXMatch.retrofitService.searchAny(text, API_KEY)
            try {
                var result = getTrackListDeferred.await()

                _Tracks.value = result.message.body.track_list

            }
            catch(t: Throwable){

                Log.i("LocationListViewModel", t.message ?: "rien")
            }
        }
    }

    fun Disconnect(it: View): Boolean {
        AuthUI.getInstance()
            .signOut(it.context)
            .addOnCompleteListener{}

        return true
    }

}