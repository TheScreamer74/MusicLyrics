package com.example.musiclyrics

import android.app.Application
import com.example.musiclyrics.di.arcCloudModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class MusicLyricsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MusicLyricsApplication)
            modules(arcCloudModule)
        }
    }


}