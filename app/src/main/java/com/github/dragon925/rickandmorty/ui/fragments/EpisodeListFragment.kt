package com.github.dragon925.rickandmorty.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.dragon925.rickandmorty.databinding.FragmentEpisodeListBinding
import com.github.dragon925.rickandmorty.ui.adapters.ItemListAdapter
import com.github.dragon925.rickandmorty.ui.models.EpisodeItem

class EpisodeListFragment : Fragment() {

    private lateinit var binding: FragmentEpisodeListBinding
    private val adapter = ItemListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEpisodeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvEpisodes.adapter = adapter

        adapter.submitList(listOf(
            EpisodeItem(
                0L,
                "Pilot",
                "December 2, 2013",
                "S01E01",
                19
            ),
            EpisodeItem(
                1L,
                "Rest and Ricklaxation",
                "August 27, 2017",
                "S03E06",
                19
            )
        ))
    }
}