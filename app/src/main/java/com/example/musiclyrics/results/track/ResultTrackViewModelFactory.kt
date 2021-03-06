package com.example.musiclyrics.results.track

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.musiclyrics.network.properties.search.track.Track
import java.lang.IllegalArgumentException

class ResultTrackViewModelFactory (private val track: Track): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResultTrackViewModel::class.java)){
            return ResultTrackViewModel(track) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }


}