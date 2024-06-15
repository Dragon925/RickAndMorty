package com.github.dragon925.rickandmorty.domain.models

data class Episode(
    override val id: Long,
    override val name: String,
    val airDate: String,
    val episode: String,
    val url: String,
    val created: String,
    val characterIds: List<Long>
) : Model
