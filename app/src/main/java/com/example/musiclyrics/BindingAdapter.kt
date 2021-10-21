package com.example.musiclyrics

import android.util.Log
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.example.musiclyrics.network.properties.search.track.TrackList
import com.example.musiclyrics.search.track.SearchTrackAdapter


@BindingAdapter("listDataTrack")
fun bindRecyclerViewTrack(recyclerView: RecyclerView, data: List<TrackList>?){
    val adapter = recyclerView.adapter as SearchTrackAdapter
    adapter.submitList(data)
}

@BindingAdapter("image")
fun ImageView.bindImage(image: String?) {
    image?.let { url ->
        val parsedImageUrl = url.toUri().buildUpon().scheme("https").build()
        this.load("$parsedImageUrl") {
            placeholder(R.drawable.ic_image_placeholder)
            scale(Scale.FILL)
            Log.d("API IMAGE", parsedImageUrl.toString())
        }
    }
}