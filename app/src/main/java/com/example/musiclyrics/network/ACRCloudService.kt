package com.example.musiclyrics.network

import android.util.Log
import com.acrcloud.rec.ACRCloudResult
import com.acrcloud.rec.IACRCloudListener
import org.json.JSONException
import org.json.JSONObject

interface ACRCloudService : IACRCloudListener {

    var result: String


    fun startRecognition() {
        com.example.musiclyrics.mClient?.let {
            if (it.startRecognize()) {
                print("Recognizing...")
            } else {
                print("Init error")
            }
        } ?: run {
            print("Client not ready")
        }
    }

    override fun onVolumeChanged(vol: Double) {
        Log.d("ACR", "volume changed $vol")
    }

    private fun handleResult(acrResult: String) {
        var res = ""
        try {
            val json = JSONObject(acrResult)
            result = acrResult
            val status: JSONObject = json.getJSONObject("status")
            val code = status.getInt("code")
            if (code == 0) {
                val metadata: JSONObject = json.getJSONObject("metadata")
                if (metadata.has("music")) {
                    val musics = metadata.getJSONArray("music")
                    val tt = musics[0] as JSONObject
                    val title = tt.getString("title")
                    val artistt = tt.getJSONArray("artists")
                    val art = artistt[0] as JSONObject
                    val artist = art.getString("name")

                    res = tt.getJSONObject("external_ids").getString("isrc")
                }
            } else {
                // TODO: Handle error
                res = acrResult
            }
        } catch (e: JSONException) {
            res = "Error parsing metadata"
            Log.e("ACR", "JSONException", e)
        }
        print(res)

    }


    override fun onResult(acrResult: ACRCloudResult?) {
        acrResult?.let {
            Log.d("ACR", "acr cloud result received: ${it.result}")
            handleResult(it.result)
        }
    }
}