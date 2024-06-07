package com.github.dragon925.rickandmorty.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.dragon925.rickandmorty.R
import com.github.dragon925.rickandmorty.data.repository.CharacterRepositoryImpl
import com.github.dragon925.rickandmorty.data.repository.EpisodeRepositoryImpl
import com.github.dragon925.rickandmorty.databinding.FragmentEpisodeBinding
import com.github.dragon925.rickandmorty.domain.repository.EpisodeState
import com.github.dragon925.rickandmorty.domain.state.DataState
import com.github.dragon925.rickandmorty.ui.adapters.ItemListAdapter
import com.github.dragon925.rickandmorty.ui.viewmodels.CharacterShortItemsState
import com.github.dragon925.rickandmorty.ui.viewmodels.EpisodeViewModel
import com.github.dragon925.rickandmorty.ui.viewmodels.EpisodeWithCharacterItemsState

class EpisodeFragment : Fragment() {

    private lateinit var binding: FragmentEpisodeBinding
    private var episodeId: Long? = null

    private val viewModel: EpisodeViewModel by viewModels {
        episodeId = arguments?.getLong(EPISODE_ID, DEFAULT_ID)
        EpisodeViewModel.Factory(
            episodeId = episodeId ?: DEFAULT_ID,
            episodeRepository = EpisodeRepositoryImpl,
            characterRepository = CharacterRepositoryImpl
        )
    }

    private val adapter = ItemListAdapter(::openCharacter)

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

        binding.rvEpisodes.adapter = adapter
    }

    private fun updateUI(state: EpisodeWithCharacterItemsState) {
        updateEpisodeInfoUI(state.first)
        updateCharactersUI(state.second)
    }

    private fun updateEpisodeInfoUI(state: EpisodeState) {
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
            }
        }
    }

    private fun updateCharactersUI(state: CharacterShortItemsState) {
        when(state) {
            DataState.Loading -> binding.pbCharacters.visibility = View.VISIBLE
            is DataState.Error -> {
                binding.pbCharacters.visibility = View.GONE
                Toast.makeText(context, state.error, Toast.LENGTH_LONG).show()
            }
            is DataState.Loaded -> {
                binding.pbCharacters.visibility = View.GONE
                adapter.submitList(state.data)
            }
        }
    }

    private fun openCharacter(id: Long) {
        findNavController().navigate(
            R.id.action_episodeFragment_to_characterFragment,
            bundleOf(CharacterFragment.CHARACTER_ID to id)
        )
    }
}