package com.github.dragon925.rickandmorty.domain.models

data class Location(
    val id: Long,
    val name: String,
    val type: String,
    val dimension: String,
    val url: String,
    val created: String,
    val residentIds: List<Long>
)
