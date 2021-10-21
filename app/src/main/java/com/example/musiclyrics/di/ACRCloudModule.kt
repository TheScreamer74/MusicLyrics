package com.example.musiclyrics.di

import com.acrcloud.rec.ACRCloudClient
import com.acrcloud.rec.ACRCloudConfig
import com.example.musiclyrics.model.acrcloud.AcrCloudInitializer
import com.example.musiclyrics.presentation.search.track.SearchTrackFragment
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val arcCloudModule = module {
    single { ACRCloudClient() }
    single { ACRCloudConfig() }
    single { (listener: SearchTrackFragment) ->
        AcrCloudInitializer(
            androidContext(),
            listener
        )
    }

}