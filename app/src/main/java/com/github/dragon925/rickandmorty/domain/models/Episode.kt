package com.github.dragon925.rickandmorty.domain.models

data class Episode(
    val id: Long,
    val name: String,
    val airDate: String,
    val episode: String,
    val url: String,
    val created: String,
    val characterIds: List<Long>
)
