package com.example.musiclyrics.search.track

import android.os.Bundle
import android.view.LayoutInflater
import android.view.SearchEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.musiclyrics.R
import com.example.musiclyrics.databinding.FragmentSearchTrackBinding


class SearchTrackFragment : Fragment() {

    companion object {
        fun newInstance() = SearchTrackFragment()
    }

    private lateinit var viewModel: SearchTrackViewModel

    private lateinit var binding: FragmentSearchTrackBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_search_track,
            container,
            false
        )


        viewModel = ViewModelProvider(this).get(SearchTrackViewModel::class.java)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        binding.searchView.setOnEditorActionListener { v, actionId, event ->

            if (actionId == EditorInfo.IME_ACTION_SEARCH){
                viewModel.Search(binding.searchView.text.toString())
                binding.imageView.visibility = View.GONE
                binding.trackList.visibility = View.VISIBLE
                true
            } else {
                false
            }

        }

        binding.trackList.adapter = SearchTrackAdapter(SearchTrackAdapter.OnClickListener {
            switchOnDetailedTrackFragment(it)
        })

        return binding.root
    }


    fun switchOnDetailedTrackFragment(id: Int) {
        this.findNavController().navigate(SearchTrackFragmentDirections.actionSearchTrackFragmentToResultTrackFragment(
            viewModel.Tracks.value!![id].track))
    }

}

