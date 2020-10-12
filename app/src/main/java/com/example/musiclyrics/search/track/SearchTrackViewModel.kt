package com.example.musiclyrics.search.track

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acrcloud.rec.ACRCloudClient
import com.acrcloud.rec.IACRCloudListener
import com.example.musiclyrics.API_KEY
import com.example.musiclyrics.network.ACRCloudService
import com.example.musiclyrics.network.MusicXMatch
import com.example.musiclyrics.network.properties.search.track.TrackList
import com.firebase.ui.auth.AuthUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.wait

class SearchTrackViewModel : ViewModel(), ACRCloudService {

    private val _tracks = MutableLiveData<List<TrackList>>()
    val tracks: LiveData<List<TrackList>>
        get() = _tracks

    fun search(text: String) {
        viewModelScope.launch {
            var getTrackListDeferred = MusicXMatch.retrofitService.searchAny(text, API_KEY)
            try {
                var result = withContext(Dispatchers.IO) { getTrackListDeferred.await() }
                _tracks.value =
                    withContext(Dispatchers.Default) { result.message.body.track_list.sortedBy { it.track.trackRating } }
            } catch (t: Throwable) {
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

    fun runDetection(){
        println(result)
    }
}