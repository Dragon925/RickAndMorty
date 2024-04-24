package com.github.dragon925.rickandmorty.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.dragon925.rickandmorty.databinding.FragmentCharacterListBinding
import com.github.dragon925.rickandmorty.domain.CharacterStatus
import com.github.dragon925.rickandmorty.domain.Gender
import com.github.dragon925.rickandmorty.ui.adapters.ItemListAdapter
import com.github.dragon925.rickandmorty.ui.models.CharacterItem

class CharacterListFragment : Fragment() {

    private lateinit var binding: FragmentCharacterListBinding
    private val adapter = ItemListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvCharacters.adapter = adapter

        adapter.submitList(listOf(
            CharacterItem(
                0L,
                "Pro trunk people marriage guy",
                CharacterStatus.ALIVE,
                "Human",
                Gender.MALE,
                "Interdimensional Cable",
                "Interdimensional Cable",
                "",
                1
            ),
            CharacterItem(
                1L,
                "Jacqueline",
                CharacterStatus.ALIVE,
                "Human",
                Gender.FEMALE,
                "Earth (Replacement Dimension)",
                "Earth (Replacement Dimension)",
                "",
                1
            ),
        ))
    }
}