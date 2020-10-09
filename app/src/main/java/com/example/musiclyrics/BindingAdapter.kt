package com.example.musiclyrics

import android.app.Activity
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ViewAnimator
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musiclyrics.network.properties.search.track.TrackList
import com.example.musiclyrics.search.track.SearchTrackAdapter
import com.squareup.picasso.Picasso


@BindingAdapter("listDataTrack")
fun bindRecyclerViewTrack(recyclerView: RecyclerView, data: List<TrackList>?){
    val adapter = recyclerView.adapter as SearchTrackAdapter
    adapter.submitList(data)
}

@BindingAdapter("image")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    Picasso.get()
        .load(imgUrl)
        .into(imgView)
}