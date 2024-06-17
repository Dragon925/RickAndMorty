package com.github.dragon925.rickandmorty.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.dragon925.rickandmorty.databinding.DialogFilterLocationBinding
import com.github.dragon925.rickandmorty.domain.utils.Filters
import com.github.dragon925.rickandmorty.domain.utils.LocationFilter
import com.github.dragon925.rickandmorty.ui.utils.OnFilterApply
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class LocationFilterBottomSheet(
    private val onFilterApply: OnFilterApply<Filters.Location>
) : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "LocationFilterBottomSheet"
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
            btnCancel.setOnClickListener { dismiss() }
            btnApplyFilters.setOnClickListener {
                onFilterApply.apply(getFilter())
                dismiss()
            }
        }
    }

    private fun getFilter(): LocationFilter {
        val filter = LocationFilter()
        with(binding) {
            etType.text?.toString()?.takeIf { it.isNotBlank() }?.let { filter.type = it }
            etDimension.text?.toString()?.takeIf { it.isNotBlank() }?.let { filter.dimension = it }
        }
        return filter
    }

}