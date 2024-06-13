package com.github.dragon925.rickandmorty.ui.fragments

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.github.dragon925.rickandmorty.R
import com.github.dragon925.rickandmorty.data.repository.CharacterRepositoryImpl
import com.github.dragon925.rickandmorty.data.repository.EpisodeRepositoryImpl
import com.github.dragon925.rickandmorty.databinding.FragmentCharacterBinding
import com.github.dragon925.rickandmorty.domain.models.CharacterStatus
import com.github.dragon925.rickandmorty.domain.models.Gender
import com.github.dragon925.rickandmorty.domain.repository.CharacterState
import com.github.dragon925.rickandmorty.domain.repository.EpisodesState
import com.github.dragon925.rickandmorty.domain.state.DataState
import com.github.dragon925.rickandmorty.domain.usecase.CharacterWithEpisodesState
import com.github.dragon925.rickandmorty.ui.adapters.ItemListAdapter
import com.github.dragon925.rickandmorty.ui.viewmodels.CharacterViewModel
import com.github.dragon925.rickandmorty.ui.viewmodels.CharacterWithEpisodeItemsState
import com.github.dragon925.rickandmorty.ui.viewmodels.EpisodeItemsState
import com.github.dragon925.rickandmorty.ui.viewmodels.EpisodeShortItemsState

class CharacterFragment : Fragment() {

    private lateinit var binding: FragmentCharacterBinding
    private var characterId: Long? = null

    private val viewModel: CharacterViewModel by viewModels {
        characterId = arguments?.getLong(CHARACTER_ID, DEFAULT_ID)
        CharacterViewModel.Factory(
            characterId = characterId ?: DEFAULT_ID,
            characterRepository = CharacterRepositoryImpl,
            episodeRepository = EpisodeRepositoryImpl
        )
    }

    private val adapter = ItemListAdapter(::openEpisode)

    companion object {
        const val CHARACTER_ID = "character_id"
        const val DEFAULT_ID = -1L
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        characterId = arguments?.getLong(CHARACTER_ID, -1L) ?: -1L
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.character.observe(viewLifecycleOwner, ::updateUI)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.rvEpisodes.adapter = adapter
    }

    private fun updateUI(state: CharacterWithEpisodeItemsState) {
        updateCharacterInfoUI(state.first)
        updateEpisodesUI(state.second)
    }

    private fun updateCharacterInfoUI(state: CharacterState) {
        when (state) {
            is DataState.Loading -> Toast.makeText(
                context, resources.getString(R.string.loading), Toast.LENGTH_SHORT
            ).show()
            is DataState.Error -> {
                Toast.makeText(context, state.error, Toast.LENGTH_LONG).show()
            }
            is DataState.Loaded -> with(binding) {
                val character = state.data
                val theme = requireContext().theme
                val color: Int = when(character.status) {
                    CharacterStatus.ALIVE -> resources.getColor(R.color.green_500, theme)
                    CharacterStatus.DEAD -> resources.getColor(R.color.red_500, theme)
                    CharacterStatus.UNKNOWN -> resources.getColor(R.color.grey_500, theme)
                }
                val icon: Int = when(character.gender) {
                    Gender.FEMALE -> R.drawable.ic_gender_female_14
                    Gender.MALE -> R.drawable.ic_gender_male_14
                    Gender.GENDERLESS -> 0
                    Gender.UNKNOWN -> 0
                }
                val status = when(character.status) {
                    CharacterStatus.ALIVE -> resources.getString(R.string.alive)
                    CharacterStatus.DEAD -> resources.getString(R.string.dead)
                    CharacterStatus.UNKNOWN -> resources.getString(R.string.unknown)
                }
                tvName.text = character.name
                tvState.text = status
                viewState.backgroundTintList = ColorStateList.valueOf(color)
                tvSpecies.text = character.species
                tvSpecies.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, icon, 0)
                tvType.text = character.type
                if (character.type.isBlank()) {
                    tvTypeLabel.visibility = View.GONE
                    tvType.visibility = View.GONE
                }
                tvOriginLocation.text = character.origin.name
                tvLastLocation.text = character.location.name
                if (character.image.isNotBlank()) {
                    Glide.with(requireContext())
                        .load(character.image)
                        .centerCrop()
                        .placeholder(R.drawable.rectangle)
                        .into(ivAvatar)
                }
            }
        }
    }

    private fun updateEpisodesUI(state: EpisodeShortItemsState) {
        when(state) {
            DataState.Loading -> binding.pbEpisodes.visibility = View.VISIBLE
            is DataState.Error -> {
                binding.pbEpisodes.visibility = View.GONE
                Toast.makeText(context, state.error, Toast.LENGTH_LONG).show()
            }
            is DataState.Loaded -> with(binding) {
                pbEpisodes.visibility = View.GONE
                adapter.submitList(state.data)
            }
        }
    }

    private fun openEpisode(id: Long) {
        findNavController().navigate(
            R.id.action_characterFragment_to_episodeFragment,
            bundleOf(EpisodeFragment.EPISODE_ID to id)
        )
    }
}