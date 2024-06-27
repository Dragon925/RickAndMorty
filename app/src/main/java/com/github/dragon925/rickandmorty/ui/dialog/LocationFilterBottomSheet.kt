package com.github.dragon925.rickandmorty.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.github.dragon925.rickandmorty.databinding.DialogFilterLocationBinding
import com.github.dragon925.rickandmorty.domain.utils.Filters
import com.github.dragon925.rickandmorty.domain.utils.LocationFilter
import com.github.dragon925.rickandmorty.ui.utils.OnFilterApply
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class LocationFilterBottomSheet : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "LocationFilterBottomSheet"
        const val RESULT_KEY = "LocationFilterBottomSheet_Result"
    }

    private lateinit var binding: DialogFilterLocationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogFilterLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            arguments?.getString("${TAG}_${Filters.Location.TYPE.name}")?.let { type ->
                etType.setText(type)
            }
            arguments?.getString("${TAG}_${Filters.Location.DIMENSION.name}")?.let { dimension ->
                etDimension.setText(dimension)
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
            etType.text?.toString()?.takeIf { it.isNotBlank() }?.let {
                filter.putString("${RESULT_KEY}_${Filters.Location.TYPE.name}", it)
            }
            etDimension.text?.toString()?.takeIf { it.isNotBlank() }?.let {
                filter.putString("${RESULT_KEY}_${Filters.Location.DIMENSION.name}", it)
            }
        }
        return filter
    }

}