package com.github.dragon925.rickandmorty.domain.models

data class Page<T>(
    val total: Int,
    val current: Int,
    val hasNext: Boolean,
    val hasPrev: Boolean,
    val list: List<T> = emptyList()
)
