package com.example.musiclyrics.search.track


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.musiclyrics.databinding.TrackItemBinding
import com.example.musiclyrics.network.properties.search.track.TrackList

class SearchTrackAdapter(private val onClickListener: SearchTrackAdapter.OnClickListener) : ListAdapter<TrackList, SearchTrackAdapter.TrackItemViewHolder>(DiffCallback) {

    class TrackItemViewHolder(private var binding: TrackItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind (track: TrackList){
            binding.track = track
            binding.executePendingBindings()
        }
    }


    companion object DiffCallback : DiffUtil.ItemCallback<TrackList>(){
        override fun areItemsTheSame(
            oldItem: TrackList,
            newItem: TrackList
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: TrackList,
            newItem: TrackList
        ): Boolean {
            return oldItem.track.trackId == newItem.track.trackId
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrackItemViewHolder {

        return TrackItemViewHolder(TrackItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: TrackItemViewHolder, position: Int) {
        val TrackList = getItem(position)
        holder.itemView.setOnClickListener{
            onClickListener.onClick(position)
        }
        holder.bind(TrackList)
    }

    class OnClickListener (val clickListener: (tackId: Int) -> Unit) {
        fun onClick(trackId:Int) = clickListener(trackId)
    }

}