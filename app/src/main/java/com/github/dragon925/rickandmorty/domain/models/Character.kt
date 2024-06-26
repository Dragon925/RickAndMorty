package com.github.dragon925.rickandmorty.domain.models

data class Character(
    override val id: Long,
    override val name: String,
    val status: CharacterStatus,
    val species: String,
    val type: String,
    val gender: Gender,
    val origin: LocationName,
    val location: LocationName,
    val image: String,
    val url: String,
    val created: String,
    val episodIds: List<Long>
) : Model