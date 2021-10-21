package com.example.musiclyrics.presentation.results.track

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.musiclyrics.R
import com.example.musiclyrics.databinding.FragmentResultTrackBinding
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinApiExtension
import org.koin.core.parameter.parametersOf

@KoinApiExtension
class ResultTrackFragment : Fragment() {

    companion object {
        fun newInstance() = ResultTrackFragment()
    }

    private val args: ResultTrackFragmentArgs by navArgs()
    private val viewModel: ResultTrackViewModel by inject { parametersOf(this, args.track) }

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
