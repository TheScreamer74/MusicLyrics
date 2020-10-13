package com.example.musiclyrics.search.track

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.acrcloud.rec.ACRCloudResult
import com.acrcloud.rec.IACRCloudListener
import com.example.musiclyrics.R
import com.example.musiclyrics.databinding.FragmentSearchTrackBinding


class SearchTrackFragment : Fragment(), IACRCloudListener {

    companion object {
        fun newInstance() = SearchTrackFragment()

        private const val TAG = "SearchTrackFragment"

        fun View.hideKeyboard() {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(windowToken, 0)
        }
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


        checkPermission()

        viewModel = ViewModelProvider(this).get(SearchTrackViewModel::class.java)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        binding.searchView.setOnEditorActionListener { v, actionId, event ->

            if (actionId == EditorInfo.IME_ACTION_SEARCH){
                viewModel.search(binding.searchView.text.toString())
                binding.imageView.visibility = View.GONE
                binding.trackList.visibility = View.VISIBLE
                v.hideKeyboard()
                true
            } else {
                false
            }

        }

        binding.trackList.adapter = SearchTrackAdapter(SearchTrackAdapter.OnClickListener {
            switchOnDetailedTrackFragment(it)
        })

        binding.disconnectButton.setOnClickListener {

            if (viewModel.disconnect(it))
                findNavController().navigate(SearchTrackFragmentDirections.actionSearchTrackFragmentToLogIn())
        }

        binding.searchButton.setOnClickListener {
            viewModel.startRecognition(this)

        }

        return binding.root
    }


    private fun switchOnDetailedTrackFragment(id: Int) {
        this.findNavController().navigate(SearchTrackFragmentDirections.actionSearchTrackFragmentToResultTrackFragment(
            viewModel.tracks.value!![id].track))
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this.requireContext(), Manifest.permission.RECORD_AUDIO) != 0) {
                ActivityCompat.requestPermissions(this.requireActivity(), arrayOf(Manifest.permission.RECORD_AUDIO), 100)
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100) {
            checkPermission()
        }
    }

    override fun onResult(p0: ACRCloudResult?) {
        viewModel.handleResult(p0!!.result)
        Log.d(TAG, "onResult() called with: p0 = $p0")
        Log.d(TAG, "onResult() called with: p0 = ${viewModel.track.value}")
    }

    override fun onVolumeChanged(p0: Double) {
        Log.d(TAG, "onVolumeChanged() called with: p0 = $p0")
    }


}

