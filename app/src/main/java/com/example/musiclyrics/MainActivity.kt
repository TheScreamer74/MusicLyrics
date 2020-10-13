package com.example.musiclyrics

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.acrcloud.rec.ACRCloudClient
import com.acrcloud.rec.ACRCloudConfig
import com.acrcloud.rec.ACRCloudResult
import com.acrcloud.rec.IACRCloudListener
import com.acrcloud.rec.utils.ACRCloudLogger
import com.google.firebase.auth.FirebaseAuth
import org.json.JSONException
import org.json.JSONObject


class MainActivity : AppCompatActivity(), IACRCloudListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkPermission()
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.RECORD_AUDIO
            ) != 0
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), 100)
        }
    }
    private var mClient: ACRCloudClient? = null

    fun initAcrcloud() {
        val config = ACRCloudConfig()

        config.acrcloudListener = this
        config.context = this

        // Please create project in "http://console.acrcloud.cn/service/avr".
        // Please create project in "http://console.acrcloud.cn/service/avr".
        config.host = "identify-eu-west-1.acrcloud.com"
        config.accessKey = "94817597049cec272eeb38936f49a6a7"
        config.accessSecret = "fRME24iBkFoTr8HdopDRbFRzKvJcIdWWHK7Lzqbg"

        config.recorderConfig.rate = 8000
        config.recorderConfig.channels = 1

        mClient = ACRCloudClient()
        if (BuildConfig.DEBUG) {
            ACRCloudLogger.setLog(true)
        }
        mClient!!.initWithConfig(config)
    }

    fun startRecognition() {
        mClient?.let {
            if (it.startRecognize()) {
                print("Recognizing...")
            } else {
                print("Init error")
            }
        } ?: run {
            print("Client not ready")
        }
    }

    override fun onResult(acrResult: ACRCloudResult?) {
        acrResult?.let {
            Log.d("ACR", "acr cloud result received: ${it.result}")
            handleResult(it.result)
        }
    }

    override fun onVolumeChanged(vol: Double) {
        Log.d("ACR", "volume changed $vol")
    }

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
                    val title = tt.getString("title")
                    val artistt = tt.getJSONArray("artists")
                    val art = artistt[0] as JSONObject
                    val artist = art.getString("name")

                    res = "$title ($artist)"
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

    override fun onDestroy() {
        super.onDestroy()
        mClient?.let {
            it.release()
            mClient = null
        }
    }

}