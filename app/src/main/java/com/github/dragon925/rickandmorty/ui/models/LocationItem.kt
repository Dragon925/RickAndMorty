package com.github.dragon925.rickandmorty.ui.models

data class LocationItem(
    override val id: Long,
    override val name: String,
    val type: String,
    val dimension: String,
    val residents: Int
) : Item
