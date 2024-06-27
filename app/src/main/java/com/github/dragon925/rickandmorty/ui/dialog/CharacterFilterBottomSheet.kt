package com.github.dragon925.rickandmorty.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.github.dragon925.rickandmorty.databinding.DialogFilterCharacterBinding
import com.github.dragon925.rickandmorty.domain.utils.CharacterFilter
import com.github.dragon925.rickandmorty.domain.utils.Filters
import com.github.dragon925.rickandmorty.ui.utils.OnFilterApply
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CharacterFilterBottomSheet : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "CharacterFilterBottomSheet"
        const val RESULT_KEY = "CharacterFilterBottomSheet_Result"

        private const val GENDER_MALE = "male"
        private const val GENDER_FEMALE = "female"
        private const val GENDER_GENDERLESS = "genderless"
        private const val GENDER_UNKNOWN = "unknown"

        private const val STATUS_ALIVE = "alive"
        private const val STATUS_DEAD = "dead"
        private const val STATUS_UNKNOWN = "unknown"
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
            arguments?.getString("${TAG}_${Filters.Character.STATUS.name}")?.let { status ->
                when(status) {
                    STATUS_ALIVE -> btnGroupStatus.check(btnStatusAlive.id)
                    STATUS_DEAD -> btnGroupStatus.check(btnStatusDead.id)
                    STATUS_UNKNOWN -> btnGroupStatus.check(btnStatusUnknown.id)
                    else -> btnGroupStatus.clearChecked()
                }
            }
            arguments?.getString("${TAG}_${Filters.Character.GENDER.name}")?.let { gender ->
                when(gender) {
                    GENDER_MALE -> btnGroupGender.check(btnGenderMale.id)
                    GENDER_FEMALE -> btnGroupGender.check(btnGenderFemale.id)
                    GENDER_GENDERLESS -> btnGroupGender.check(btnGenderGenderless.id)
                    GENDER_UNKNOWN -> btnGroupGender.check(btnGenderUnknown.id)
                    else -> btnGroupGender.clearChecked()
                }
            }
            arguments?.getString("${TAG}_${Filters.Character.TYPE.name}")?.let { type ->
                etType.setText(type)
            }
            arguments?.getString("${TAG}_${Filters.Character.SPECIES.name}")?.let { species ->
                etSpecies.setText(species)
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
            when(btnGroupStatus.checkedButtonId) {
                btnStatusAlive.id -> filter.putString(
                    "${RESULT_KEY}_${Filters.Character.STATUS.name}",
                    STATUS_ALIVE
                )
                btnStatusDead.id -> filter.putString(
                    "${RESULT_KEY}_${Filters.Character.STATUS.name}",
                    STATUS_DEAD
                )
                btnStatusUnknown.id -> filter.putString(
                    "${RESULT_KEY}_${Filters.Character.STATUS.name}",
                    STATUS_UNKNOWN
                )
            }

            when(btnGroupGender.checkedButtonId) {
                btnGenderFemale.id -> filter.putString(
                    "${RESULT_KEY}_${Filters.Character.GENDER.name}", GENDER_FEMALE
                )
                btnGenderMale.id -> filter.putString(
                    "${RESULT_KEY}_${Filters.Character.GENDER.name}", GENDER_MALE
                )
                btnGenderGenderless.id -> filter.putString(
                    "${RESULT_KEY}_${Filters.Character.GENDER.name}", GENDER_GENDERLESS
                )
                btnGenderUnknown.id -> filter.putString(
                    "${RESULT_KEY}_${Filters.Character.GENDER.name}", GENDER_UNKNOWN
                )
            }
            etType.text?.toString()?.takeIf { it.isNotBlank() }?.let {
                filter.putString("${RESULT_KEY}_${Filters.Character.TYPE.name}", it)
            }
            etSpecies.text?.toString()?.takeIf { it.isNotBlank() }?.let {
                filter.putString("${RESULT_KEY}_${Filters.Character.SPECIES.name}", it)
            }
        }
        return filter
    }

}