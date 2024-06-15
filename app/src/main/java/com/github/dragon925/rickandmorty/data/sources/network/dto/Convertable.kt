package com.github.dragon925.rickandmorty.data.sources.network.dto

interface Convertable<T> {
    fun toModel(): T
}