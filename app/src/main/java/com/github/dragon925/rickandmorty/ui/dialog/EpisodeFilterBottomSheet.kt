package com.github.dragon925.rickandmorty.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.dragon925.rickandmorty.databinding.DialogFilterEpisodeBinding
import com.github.dragon925.rickandmorty.domain.utils.EpisodeFilter
import com.github.dragon925.rickandmorty.domain.utils.Filters
import com.github.dragon925.rickandmorty.ui.utils.OnFilterApply
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class EpisodeFilterBottomSheet(
    private val onFilterApply: OnFilterApply<Filters.Episode>
) : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "EpisodeFilterBottomSheet"
    }

    private lateinit var binding: DialogFilterEpisodeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogFilterEpisodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            btnCancel.setOnClickListener { dismiss() }
            btnApplyFilters.setOnClickListener {
                onFilterApply.apply(getFilter())
                dismiss()
            }
        }
    }

    private fun getFilter(): EpisodeFilter {
        val filter = EpisodeFilter()
        with(binding) {
            etEpisode.text?.toString()?.takeIf { it.isNotBlank() }?.let { filter.episode = it }
        }
        return filter
    }

}