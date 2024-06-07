package com.github.dragon925.rickandmorty.ui.models

data class EpisodeShortItem(
    override val id: Long,
    override val name: String,
    val airDate: String,
    val episode: String,
) : Item
