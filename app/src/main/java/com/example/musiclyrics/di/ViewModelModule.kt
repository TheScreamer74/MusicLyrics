package com.example.musiclyrics.di

import androidx.lifecycle.ViewModelProvider
import com.example.musiclyrics.presentation.search.track.SearchTrackViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val viewModelModule = module {
    factory { (activity : androidx.fragment.app.Fragment) ->
        ViewModelProvider(activity).get(SearchTrackViewModel::class.java)
    }
}