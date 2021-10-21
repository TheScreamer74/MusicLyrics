package com.example.musiclyrics.model.acrcloud

import android.content.Context
import com.acrcloud.rec.ACRCloudConfig
import com.example.musiclyrics.presentation.search.track.SearchTrackFragment

class AcrCloudInitializer(
        private val context: Context,
        private val listener: SearchTrackFragment
    ) {
    private val host: String = "identify-eu-west-1.acrcloud.com"
    private val accessKey: String = "94817597049cec272eeb38936f49a6a7"
    private val accessSecret: String = "fRME24iBkFoTr8HdopDRbFRzKvJcIdWWHK7Lzqbg"
    private val recorderConfigRate : Int = 8000
    private val recorderConfigChannels : Int = 1

        fun applyModifierOn(config: ACRCloudConfig) : ACRCloudConfig {
                config.acrcloudListener = listener
                config.context = context
                config.host = host
                config.accessKey = accessKey
                config.accessSecret = accessSecret
                config.recorderConfig.rate = recorderConfigRate
                config.recorderConfig.channels = recorderConfigChannels
            return config
        }
}
