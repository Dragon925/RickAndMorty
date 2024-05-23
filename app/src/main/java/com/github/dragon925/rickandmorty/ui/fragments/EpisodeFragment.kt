package com.github.dragon925.rickandmorty.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.dragon925.rickandmorty.R
import com.github.dragon925.rickandmorty.data.repository.EpisodeRepositoryImpl
import com.github.dragon925.rickandmorty.databinding.FragmentEpisodeBinding
import com.github.dragon925.rickandmorty.domain.repository.EpisodeState
import com.github.dragon925.rickandmorty.domain.state.DataState
import com.github.dragon925.rickandmorty.ui.viewmodels.EpisodeViewModel

class EpisodeFragment : Fragment() {

    private lateinit var binding: FragmentEpisodeBinding
    private var episodeId: Long? = null

    private val viewModel: EpisodeViewModel by viewModels {
        episodeId = arguments?.getLong(EPISODE_ID, DEFAULT_ID)
        EpisodeViewModel.Factory(
            episodeId = episodeId ?: DEFAULT_ID,
            EpisodeRepositoryImpl
        )
    }

    companion object {
        const val EPISODE_ID = "episode_id"
        const val DEFAULT_ID = -1L
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEpisodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.episodes.observe(viewLifecycleOwner, ::updateUI)

        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
    }

    private fun updateUI(state: EpisodeState) {
        when(state) {
            is DataState.Loading -> Toast.makeText(
                context, resources.getString(R.string.loading), Toast.LENGTH_SHORT
            ).show()
            is DataState.Error -> {
                Toast.makeText(context, state.error, Toast.LENGTH_LONG).show()
            }
            is DataState.Loaded -> with(binding) {
                val episode = state.data
                tvName.text = episode.name
                tvEpisode.text = episode.episode
                tvDate.text = episode.airDate
                // TODO list
            }
        }
    }
}