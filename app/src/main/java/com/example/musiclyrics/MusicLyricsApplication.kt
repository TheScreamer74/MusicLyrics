package com.example.musiclyrics

import android.app.Application
import com.example.musiclyrics.di.arcCloudModule
import com.example.musiclyrics.di.clientModule
import com.example.musiclyrics.di.networkModule
import com.example.musiclyrics.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class MusicLyricsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MusicLyricsApplication)
            modules(arcCloudModule, networkModule, clientModule, viewModelModule)
        }
    }


}