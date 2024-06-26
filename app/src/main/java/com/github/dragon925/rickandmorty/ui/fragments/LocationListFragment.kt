package com.github.dragon925.rickandmorty.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.dragon925.rickandmorty.App
import com.github.dragon925.rickandmorty.R
import com.github.dragon925.rickandmorty.data.repository.RepositoryHandler
import com.github.dragon925.rickandmorty.databinding.FragmentLocationsListBinding
import com.github.dragon925.rickandmorty.domain.state.DataState
import com.github.dragon925.rickandmorty.domain.utils.FilterBuilder
import com.github.dragon925.rickandmorty.domain.utils.Filters
import com.github.dragon925.rickandmorty.domain.utils.LocationFilter
import com.github.dragon925.rickandmorty.ui.adapters.ItemListAdapter
import com.github.dragon925.rickandmorty.ui.dialog.LocationFilterBottomSheet
import com.github.dragon925.rickandmorty.ui.models.LocationItem
import com.github.dragon925.rickandmorty.ui.utils.ListScrollListener
import com.github.dragon925.rickandmorty.ui.viewmodels.LocationItemsState
import com.github.dragon925.rickandmorty.ui.viewmodels.LocationListViewModel

class LocationListFragment : Fragment() {

    private lateinit var binding: FragmentLocationsListBinding
    private val adapter = ItemListAdapter(this::openLocation)
    private val viewModel: LocationListViewModel by viewModels {
        LocationListViewModel.Factory(
            RepositoryHandler.getLocationRepository(
                (requireActivity().application as App).locationApi
            )
        )
    }

    private var filter = LocationFilter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(LocationFilterBottomSheet.RESULT_KEY) { key, bundle ->
            val filter = LocationFilter()
            bundle.getString("${key}_${Filters.Location.NAME.name}")?.let {
                filter.name = it
            }
            bundle.getString("${key}_${Filters.Location.TYPE.name}")?.let {
                filter.type = it
            }
            bundle.getString("${key}_${Filters.Location.DIMENSION.name}")?.let {
                filter.dimension = it
            }
            // TODO send filter
            this.filter = filter
            Log.d("LocationListFragment-filters", "${filter.build()}")
        }
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
        binding.rvLocations.addOnScrollListener(ListScrollListener { viewModel.loadNextPage() })

        viewModel.locations.observe(viewLifecycleOwner, ::updateUI)

        binding.srlLocations.setOnRefreshListener { viewModel.reload() }

        binding.ibFilter.setOnClickListener {
            val bottomSheet = LocationFilterBottomSheet()
            val tag = LocationFilterBottomSheet.TAG
            val bundle = bundleOf()
            filter.build().forEach { key, value ->
                bundle.putString("${tag}_${key.name}", value)
            }
            bottomSheet.arguments = bundle
            bottomSheet.show(parentFragmentManager, tag)
        }
    }

    private fun updateUI(state: LocationItemsState) {
        when(state) {
            is DataState.Loading -> Toast.makeText(
                context, resources.getString(R.string.loading), Toast.LENGTH_SHORT
            ).show()
            is DataState.Error -> {
                Toast.makeText(context, state.error.toString(), Toast.LENGTH_LONG).show()
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
            R.id.action_locations_to_locationFragment,
            bundleOf(LocationFragment.LOCATION_ID to id)
        )
    }
}