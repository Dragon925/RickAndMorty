package com.github.dragon925.rickandmorty.ui.models

sealed interface Item {
    val id: Long
    val name: String
}