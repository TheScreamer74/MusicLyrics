package com.example.musiclyrics

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.acrcloud.rec.ACRCloudClient
import com.acrcloud.rec.ACRCloudConfig
import com.acrcloud.rec.utils.ACRCloudLogger
import com.example.musiclyrics.network.ACRCloudService


class MainActivity : AppCompatActivity(), ACRCloudService {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkPermission()
        initAcrCloud()
    }

    private fun initAcrCloud() {
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

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.RECORD_AUDIO
            ) != 0
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), 100)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mClient?.let {
            it.release()
            mClient = null
        }
    }

}