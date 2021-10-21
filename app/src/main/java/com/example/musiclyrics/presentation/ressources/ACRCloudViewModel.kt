package com.example.musiclyrics.presentation.ressources

import android.util.Log
import androidx.lifecycle.ViewModel
import com.acrcloud.rec.ACRCloudClient
import com.acrcloud.rec.ACRCloudConfig
import com.acrcloud.rec.IACRCloudListener
import com.example.musiclyrics.model.acrcloud.AcrCloudInitializer
import com.example.musiclyrics.network.MusicXMatchListener
import org.json.JSONException
import org.json.JSONObject
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

@KoinApiExtension
abstract class ACRCloudViewModel : ViewModel(), KoinComponent {

    lateinit var callback: MusicXMatchListener
    lateinit var listener: IACRCloudListener

    private val mClient: ACRCloudClient by inject()
    private val mConfig: ACRCloudConfig by inject()
    private val acrCloudInitializer: AcrCloudInitializer by inject { parametersOf(listener) }

    //Begin the recognition of the music
    fun startRecognition(searchTrackFragment: IACRCloudListener) {
        mClient.apply {
            this.initWithConfig(acrCloudInitializer.applyModifierOn(mConfig))
        }.let{
            if (it.startRecognize()) {
                Log.i("Recognition", "Started")
            } else {
                Log.i("Recognition", "Failed")
            }
        }
    }

    //When the recognition is successful retrieve information to perform a research
    fun handleResult(acrResult: String) {
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

    //get a track from musicxmatch using isrc code
    abstract fun getTrack(isrc: String)
}

