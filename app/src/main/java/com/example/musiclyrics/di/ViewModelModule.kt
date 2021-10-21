package com.example.musiclyrics.di

import androidx.lifecycle.ViewModelProvider
import com.example.musiclyrics.presentation.results.track.ResultTrackViewModel
import com.example.musiclyrics.presentation.results.track.ResultTrackViewModelFactory
import com.example.musiclyrics.presentation.search.track.SearchTrackViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinApiExtension
import org.koin.dsl.module

@OptIn(KoinApiExtension::class)
val viewModelModule = module {

    //TODO : Check arguments

    factory { (activity : androidx.fragment.app.Fragment) ->
        ViewModelProvider(
            activity,
            ResultTrackViewModelFactory(
                get()
            )
        ).get(ResultTrackViewModel::class.java)
    }

    factory { (activity : androidx.fragment.app.Fragment) ->
        ViewModelProvider(activity).get(SearchTrackViewModel::class.java)
    }

}