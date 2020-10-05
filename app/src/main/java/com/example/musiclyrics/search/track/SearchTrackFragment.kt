package com.example.musiclyrics.search.track

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.CursorAdapter
import android.widget.SimpleCursorAdapter
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.musiclyrics.R
import com.example.musiclyrics.databinding.FragmentSearchTrackBinding
import kotlinx.android.synthetic.main.fragment_search_track.*


class SearchTrackFragment : Fragment() {

    companion object {
        fun newInstance() = SearchTrackFragment()
    }

    private lateinit var viewModel: SearchTrackViewModel

    private lateinit var binding: FragmentSearchTrackBinding

    private lateinit var mAdapter: SimpleCursorAdapter

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

        val from = arrayOf("cityName")
        val to = intArrayOf(android.R.id.text1)
        mAdapter = SimpleCursorAdapter(
            activity,
            android.R.layout.simple_list_item_1,
            null,
            from,
            to,
            CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
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

        binding.trackList.adapter = SearchTrackAdapter()


        return binding.root
    }


}

