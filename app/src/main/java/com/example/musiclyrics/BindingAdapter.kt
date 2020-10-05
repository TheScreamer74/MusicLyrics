package com.example.musiclyrics

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musiclyrics.network.properties.search.TrackList
import com.example.musiclyrics.search.track.SearchTrackAdapter


@BindingAdapter("listDataTrack")
fun bindRecyclerViewTrack(recyclerView: RecyclerView, data: List<TrackList>?){
    val adapter = recyclerView.adapter as SearchTrackAdapter
    adapter.submitList(data)
}