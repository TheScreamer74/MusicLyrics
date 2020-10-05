package com.example.musiclyrics.search.track

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.musiclyrics.R
import com.example.musiclyrics.databinding.FragmentSearchTrackBinding
import com.example.musiclyrics.network.MusicXMatch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

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
        
        binding.searchText.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                viewModel.Search(binding.searchText.text.toString())
                true
            }
            else {
                false
            }
        }

        return binding.root
    }
}

