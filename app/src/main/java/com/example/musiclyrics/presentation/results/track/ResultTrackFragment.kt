package com.example.musiclyrics.presentation.results.track

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.musiclyrics.R
import com.example.musiclyrics.databinding.FragmentResultTrackBinding

class ResultTrackFragment : Fragment() {

    companion object {
        fun newInstance() = ResultTrackFragment()
    }

    private lateinit var viewModel: ResultTrackViewModel
    private lateinit var viewModelFactory: ResultTrackViewModelFactory

    private lateinit var binding: FragmentResultTrackBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_result_track,
            container,
            false
        )


        val args = arguments?.let { ResultTrackFragmentArgs.fromBundle(it) }
        viewModelFactory = ResultTrackViewModelFactory(args!!.track)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ResultTrackViewModel::class.java)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        rotateImg()
        return binding.root

    }

    fun rotateImg() {
        val rotation = AnimationUtils.loadAnimation(this.activity, R.anim.rotate)
        binding.albumImg.startAnimation(rotation)
    }
}
