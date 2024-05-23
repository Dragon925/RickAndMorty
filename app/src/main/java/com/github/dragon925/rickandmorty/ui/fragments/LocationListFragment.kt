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
import com.github.dragon925.rickandmorty.data.repository.LocationRepositoryImpl
import com.github.dragon925.rickandmorty.databinding.FragmentLocationsListBinding
import com.github.dragon925.rickandmorty.domain.state.DataState
import com.github.dragon925.rickandmorty.ui.adapters.ItemListAdapter
import com.github.dragon925.rickandmorty.ui.models.LocationItem
import com.github.dragon925.rickandmorty.ui.viewmodels.LocationItemsState
import com.github.dragon925.rickandmorty.ui.viewmodels.LocationListViewModel

class LocationListFragment : Fragment() {

    private lateinit var binding: FragmentLocationsListBinding
    private val adapter = ItemListAdapter(this::openLocation)
    private val viewModel: LocationListViewModel by viewModels {
        LocationListViewModel.Factory(
            LocationRepositoryImpl
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvLocations.adapter = adapter

        viewModel.locations.observe(viewLifecycleOwner, ::updateUI)

        binding.srlLocations.setOnRefreshListener { viewModel.reload() }
    }

    private fun updateUI(state: LocationItemsState) {
        when(state) {
            is DataState.Loading -> Toast.makeText(
                context, resources.getString(R.string.loading), Toast.LENGTH_SHORT
            ).show()
            is DataState.Error -> {
                Toast.makeText(context, state.error, Toast.LENGTH_LONG).show()
                binding.srlLocations.isRefreshing = false
            }
            is DataState.Loaded -> {
                adapter.submitList(state.data)
                binding.srlLocations.isRefreshing = false
            }
        }
    }

    private fun openLocation(id: Long) {
        findNavController().navigate(
            R.id.action_episodes_to_episodeFragment,
            bundleOf(CharacterFragment.CHARACTER_ID to id)
        )
    }
}