package com.example.musiclyrics.search.track


import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.*
import com.acrcloud.rec.ACRCloudClient
import com.acrcloud.rec.ACRCloudConfig
import com.acrcloud.rec.IACRCloudListener
import com.example.musiclyrics.API_KEY
import com.example.musiclyrics.network.MusicXMatch
import com.example.musiclyrics.network.MusicXMatchListener
import com.example.musiclyrics.network.properties.search.track.Track
import com.example.musiclyrics.network.properties.search.track.TrackList
import com.firebase.ui.auth.AuthUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject


class SearchTrackViewModel(application: Application) : AndroidViewModel(application) {

    private var mClient: ACRCloudClient = ACRCloudClient()

    lateinit var callback: MusicXMatchListener

    private val _track = MutableLiveData<Track>()
    val track: LiveData<Track>
        get() = _track

    private val _tracks = MutableLiveData<List<TrackList>>()
    val tracks: LiveData<List<TrackList>>
        get() = _tracks


    //get a track from musicxmatch using any keyword
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


    //get a track from musicxmatch using isrc code
    fun getTrack(isrc: String){
        viewModelScope.launch {
            var getTrackDeferred = MusicXMatch.retrofitService.getTrack(isrc, API_KEY)
            try{
                var result = withContext(Dispatchers.IO) { getTrackDeferred.await() }
                _track.value =
                    withContext(Dispatchers.Default) { result.message.body.track }
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


    //Begin the recognition of the music

    fun startRecognition(searchTrackFragment: IACRCloudListener) {
        mClient.apply {
            this.initWithConfig(ACRCloudConfig().apply{
                this.acrcloudListener = searchTrackFragment
                this.context = this@SearchTrackViewModel.getApplication()
                this.host = "identify-eu-west-1.acrcloud.com"
                this.accessKey = "94817597049cec272eeb38936f49a6a7"
                this.accessSecret = "fRME24iBkFoTr8HdopDRbFRzKvJcIdWWHK7Lzqbg"
                this.recorderConfig.rate = 8000
                this.recorderConfig.channels = 1
            })
        }.let {
            if (it.startRecognize()) {
                Log.i("Recognition", "Started")
            } else {
                Log.i("Recognition", "Failed")
            }
        }
    }

    //When the recognition is successful retrieve information to perform a research
    fun handleResult(acrResult: String){
            var res = ""
            try {
                val json = JSONObject(acrResult)
                val status: JSONObject = json.getJSONObject("status")
                val code = status.getInt("code")
                if (code == 0) {
                    val metadata: JSONObject = json.getJSONObject("metadata")
                    if (metadata.has("music")) {
                        val musics = metadata.getJSONArray("music")
                        val tt = musics[0] as JSONObject
                        if (tt.has("external_ids")) {
                            val externalIds = tt.getJSONObject("external_ids")
                            res = externalIds.getString("isrc")
                        }
                    }
                } else {
                    callback.onEventFailed()
                    return
                }
            } catch (e: JSONException) {
                res = "Error parsing metadata"
                Log.e("ACR", "JSONException", e)
            }
        getTrack(res)
        return
    }
}