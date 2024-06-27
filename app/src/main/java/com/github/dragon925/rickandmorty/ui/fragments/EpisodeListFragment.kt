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
import com.github.dragon925.rickandmorty.databinding.FragmentEpisodeListBinding
import com.github.dragon925.rickandmorty.domain.state.DataState
import com.github.dragon925.rickandmorty.domain.utils.EpisodeFilter
import com.github.dragon925.rickandmorty.domain.utils.FilterBuilder
import com.github.dragon925.rickandmorty.domain.utils.Filters
import com.github.dragon925.rickandmorty.ui.adapters.ItemListAdapter
import com.github.dragon925.rickandmorty.ui.dialog.EpisodeFilterBottomSheet
import com.github.dragon925.rickandmorty.ui.models.EpisodeItem
import com.github.dragon925.rickandmorty.ui.utils.ListScrollListener
import com.github.dragon925.rickandmorty.ui.viewmodels.EpisodeItemsState
import com.github.dragon925.rickandmorty.ui.viewmodels.EpisodeListViewModel

class EpisodeListFragment : Fragment() {

    private lateinit var binding: FragmentEpisodeListBinding
    private val adapter = ItemListAdapter(this::openEpisode)
    private val viewModel: EpisodeListViewModel by viewModels {
        EpisodeListViewModel.Factory(
            RepositoryHandler.getEpisodeRepository(
                (requireActivity().application as App).episodeApi
            )
        )
    }

    private var filter = EpisodeFilter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(EpisodeFilterBottomSheet.RESULT_KEY) { key, bundle ->
            val filter = EpisodeFilter()
            bundle.getString("${key}_${Filters.Episode.NAME.name}")?.let {
                filter.name = it
            }
            bundle.getString("${key}_${Filters.Episode.EPISODE.name}")?.let {
                filter.episode = it
            }
            // TODO send filter
            this.filter = filter
            Log.d("EpisodeListFragment-filters", "${filter.build()}")
        }
    }

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
        binding.rvEpisodes.addOnScrollListener(ListScrollListener { viewModel.loadNextPage() })

        viewModel.episodes.observe(viewLifecycleOwner, ::updateUI)

        binding.srlEpisodes.setOnRefreshListener { viewModel.reload() }

        binding.ibFilter.setOnClickListener {
            val bottomSheet = EpisodeFilterBottomSheet()
            val tag = EpisodeFilterBottomSheet.TAG
            val bundle = bundleOf()
            filter.build().forEach { key, value ->
                bundle.putString("${tag}_${key.name}", value)
            }
            bottomSheet.arguments = bundle
            bottomSheet.show(parentFragmentManager, tag)
        }
    }

    private fun updateUI(state: EpisodeItemsState) {
        when(state) {
            is DataState.Loading -> Toast.makeText(
                context, resources.getString(R.string.loading), Toast.LENGTH_SHORT
            ).show()
            is DataState.Error -> {
                Toast.makeText(context, state.error.toString(), Toast.LENGTH_LONG).show()
                binding.srlEpisodes.isRefreshing = false
            }
            is DataState.Loaded -> {
                adapter.submitList(state.data)
                binding.srlEpisodes.isRefreshing = false
            }
        }
    }

    private fun openEpisode(id: Long) {
        findNavController().navigate(
            R.id.action_episodes_to_episodeFragment,
            bundleOf(EpisodeFragment.EPISODE_ID to id)
        )
    }
}