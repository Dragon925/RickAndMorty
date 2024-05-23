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
import com.github.dragon925.rickandmorty.data.repository.LocationRepositoryImpl
import com.github.dragon925.rickandmorty.databinding.FragmentLocationBinding
import com.github.dragon925.rickandmorty.domain.repository.LocationState
import com.github.dragon925.rickandmorty.domain.state.DataState
import com.github.dragon925.rickandmorty.ui.viewmodels.LocationViewModel

class LocationFragment : Fragment() {

    private lateinit var binding: FragmentLocationBinding
    private var locationId: Long? = null

    private val viewModel: LocationViewModel by viewModels {
        locationId = arguments?.getLong(LOCATION_ID, DEFAULT_ID)
        LocationViewModel.Factory(
            locationId = locationId ?: DEFAULT_ID,
            locationRepository = LocationRepositoryImpl
        )
    }

    companion object {
        const val LOCATION_ID = "location_id"
        const val DEFAULT_ID = -1L
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.location.observe(viewLifecycleOwner, ::updateUI)

        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
    }

    private fun updateUI(state: LocationState) {
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
                tvType.text = episode.type
                tvDimension.text = episode.dimension
                //TODO list
            }
        }
    }
}