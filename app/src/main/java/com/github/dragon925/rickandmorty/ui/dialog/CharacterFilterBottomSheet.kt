package com.github.dragon925.rickandmorty.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.dragon925.rickandmorty.databinding.DialogFilterCharacterBinding
import com.github.dragon925.rickandmorty.domain.utils.CharacterFilter
import com.github.dragon925.rickandmorty.domain.utils.Filters
import com.github.dragon925.rickandmorty.ui.utils.OnFilterApply
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CharacterFilterBottomSheet(
    private val onFilterApply: OnFilterApply<Filters.Character>
) : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "CharacterFilterBottomSheet"
    }

    private lateinit var binding: DialogFilterCharacterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogFilterCharacterBinding.inflate(inflater, container, false)
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

    private fun getFilter(): CharacterFilter {
        val filter = CharacterFilter()
        with(binding) {
            when(btnGroupStatus.checkedButtonId) {
                btnStatusAlive.id -> filter.status = "alive"
                btnStatusDead.id -> filter.status = "dead"
                btnStatusUnknown.id -> filter.status = "unknown"
            }

            when(btnGroupGender.checkedButtonId) {
                btnGenderFemale.id -> filter.gender = "female"
                btnGenderMale.id -> filter.gender = "male"
                btnGenderGenderless.id -> filter.gender = "genderless"
                btnGenderUnknown.id -> filter.gender = "unknown"
            }
            etType.text?.toString()?.takeIf { it.isNotBlank() }?.let { filter.type = it }
            etSpecies.text?.toString()?.takeIf { it.isNotBlank() }?.let { filter.species = it }
        }
        return filter
    }

}