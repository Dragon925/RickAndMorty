package com.github.dragon925.rickandmorty.ui.models

import com.github.dragon925.rickandmorty.domain.CharacterStatus
import com.github.dragon925.rickandmorty.domain.Gender

data class CharacterItem(
    override val id: Long,
    override val name: String,
    val status: CharacterStatus,
    val species: String,
    val gender: Gender,
    val origin: String,
    val location: String,
    val image: String,
    val episodes: Int
) : Item