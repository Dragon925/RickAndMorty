package com.github.dragon925.rickandmorty.domain.models

data class Location(
    override val id: Long,
    override val name: String,
    val type: String,
    val dimension: String,
    val url: String,
    val created: String,
    val residentIds: List<Long>
) : Model
