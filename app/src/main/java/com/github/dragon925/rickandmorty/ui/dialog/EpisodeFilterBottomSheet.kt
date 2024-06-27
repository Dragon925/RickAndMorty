package com.github.dragon925.rickandmorty.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.github.dragon925.rickandmorty.databinding.DialogFilterEpisodeBinding
import com.github.dragon925.rickandmorty.domain.utils.EpisodeFilter
import com.github.dragon925.rickandmorty.domain.utils.Filters
import com.github.dragon925.rickandmorty.ui.utils.OnFilterApply
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class EpisodeFilterBottomSheet : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "EpisodeFilterBottomSheet"
        const val RESULT_KEY = "EpisodeFilterBottomSheet_Result"
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
            arguments?.getString("${TAG}_${Filters.Episode.EPISODE.name}")?.let { episode ->
                etEpisode.setText(episode)
            }
            btnCancel.setOnClickListener { dismiss() }
            btnApplyFilters.setOnClickListener {
                setFragmentResult(RESULT_KEY, getFilterBundle())
                dismiss()
            }
        }
    }

    private fun getFilterBundle(): Bundle {
        val filter = bundleOf()
        with(binding) {
            etEpisode.text?.toString()?.takeIf { it.isNotBlank() }?.let {
                filter.putString("${RESULT_KEY}_${Filters.Episode.EPISODE.name}", it)
            }
        }
        return filter
    }

}