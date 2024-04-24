package com.github.dragon925.rickandmorty.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.dragon925.rickandmorty.databinding.FragmentLocationsListBinding
import com.github.dragon925.rickandmorty.ui.adapters.ItemListAdapter
import com.github.dragon925.rickandmorty.ui.models.LocationItem

class LocationListFragment : Fragment() {

    private lateinit var binding: FragmentLocationsListBinding
    private val adapter = ItemListAdapter()

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

        adapter.submitList(listOf(
            LocationItem(
                0L,
                "Earth",
                "Planet",
                "Dimension C-137",
                27
            )
        ))
    }
}