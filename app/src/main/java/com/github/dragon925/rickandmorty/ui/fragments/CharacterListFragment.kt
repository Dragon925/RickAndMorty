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
import com.github.dragon925.rickandmorty.databinding.FragmentCharacterListBinding
import com.github.dragon925.rickandmorty.domain.state.DataState
import com.github.dragon925.rickandmorty.ui.adapters.ItemListAdapter
import com.github.dragon925.rickandmorty.ui.models.CharacterItem
import com.github.dragon925.rickandmorty.ui.viewmodels.CharacterItemsState
import com.github.dragon925.rickandmorty.ui.viewmodels.CharacterListViewModel

class CharacterListFragment : Fragment() {

    private lateinit var binding: FragmentCharacterListBinding
    private val adapter = ItemListAdapter(::openCharacter)
    private val viewModel: CharacterListViewModel by viewModels {
        CharacterListViewModel.Factory(
            CharacterRepositoryImpl
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvCharacters.adapter = adapter

        viewModel.characters.observe(viewLifecycleOwner, ::updateUI)

        binding.srlCharacters.setOnRefreshListener { viewModel.reload() }
    }

    private fun updateUI(state: CharacterItemsState) {
        when(state) {
            is DataState.Loading -> Toast.makeText(
                context, resources.getString(R.string.loading), Toast.LENGTH_SHORT
            ).show()
            is DataState.Error -> {
                Toast.makeText(context, state.error.toString(), Toast.LENGTH_LONG).show()
                binding.srlCharacters.isRefreshing = false
            }
            is DataState.Loaded -> {
                adapter.submitList(state.data)
                binding.srlCharacters.isRefreshing = false
            }
        }
    }

    private fun openCharacter(id: Long) {
        findNavController().navigate(
            R.id.action_characters_to_characterFragment,
            bundleOf(CharacterFragment.CHARACTER_ID to id)
        )
    }
}