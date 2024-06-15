package com.github.dragon925.rickandmorty.domain.models

sealed interface Model {
    val id: Long
    val name: String
}