package com.example.musiclyrics.presentation.search.track

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.acrcloud.rec.ACRCloudResult
import com.acrcloud.rec.IACRCloudListener
import com.example.musiclyrics.R
import com.example.musiclyrics.databinding.FragmentSearchTrackBinding
import com.example.musiclyrics.network.MusicXMatchListener
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinApiExtension
import org.koin.core.parameter.parametersOf


@KoinApiExtension
class SearchTrackFragment : Fragment(), IACRCloudListener, MusicXMatchListener {

    companion object {
        fun newInstance() = SearchTrackFragment()

        private const val TAG = "SearchTrackFragment"

        fun View.hideKeyboard() {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(windowToken, 0)
        }
    }

    private val viewModel: SearchTrackViewModel by inject { parametersOf(this) }

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

        checkPermissions()

        viewModel.callback = this

        viewModel.listener = this

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        binding.trackList.visibility = View.GONE

        binding.searchView.setOnFocusChangeListener { v, hasFocus ->
            if(v == binding.searchView && hasFocus ) {
                binding.imageView.visibility = View.GONE
                binding.trackList.visibility = View.VISIBLE
                binding.searchButton.visibility = View.GONE
                binding.disconnectButton.visibility = View.GONE
                binding.loaderBar.visibility = View.INVISIBLE
            }

        }
        binding.searchView.setOnEditorActionListener { v, actionId, event ->

            if (actionId == EditorInfo.IME_ACTION_SEARCH){
                viewModel.search(binding.searchView.text.toString())
                binding.imageView.visibility = View.GONE
                binding.trackList.visibility = View.VISIBLE
                binding.searchButton.visibility = View.GONE
                binding.disconnectButton.visibility = View.GONE
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
            it.visibility = View.INVISIBLE
            it.isEnabled = false
            viewModel.startRecognition(this)
        }

        return binding.root
    }


    private fun switchOnDetailedTrackFragment(id: Int) {
        this.findNavController().navigate(SearchTrackFragmentDirections.actionSearchTrackFragmentToResultTrackFragment(
            viewModel.tracks.value!![id].track))
    }

    private fun checkPermissions(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) { // 23
            // Check if we have record
            val sendSmsPermission = ActivityCompat.checkSelfPermission(this.requireContext(),
                Manifest.permission.RECORD_AUDIO)

            if (sendSmsPermission != PackageManager.PERMISSION_GRANTED) {
                // If don't have permission so prompt the user.
                this.requestPermissions(
                    arrayOf(Manifest.permission.RECORD_AUDIO),
                    100
                )
                return
            }
        }
    }

    override fun onResult(p0: ACRCloudResult?) {
        viewModel.handleResult(p0!!.result)
    }

    override fun onVolumeChanged(p0: Double) {
        Log.d(TAG, "onVolumeChanged() called with: p0 = $p0")
    }

    override fun onEventCompleted() {
        binding.searchButton.visibility = View.VISIBLE
        binding.searchButton.isEnabled = true
        findNavController().navigate(SearchTrackFragmentDirections.actionSearchTrackFragmentToResultTrackFragment(viewModel.track.value!!))
    }

    override fun onEventFailed() {
        binding.searchButton.visibility = View.VISIBLE
        binding.searchButton.isEnabled = true
        val myToastError = Toast.makeText(this.requireContext(), "Désolé nous n'avons pas pu reconnaître votre morceau 🙁", Toast.LENGTH_SHORT)
        myToastError.show()
        Log.i("MusicXMactch", "Retrieve failed")
    }

    override fun onResume() {
        super.onResume()
        binding.searchView.text?.clear()
    }
}

