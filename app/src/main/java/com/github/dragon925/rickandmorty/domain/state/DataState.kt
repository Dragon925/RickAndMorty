package com.github.dragon925.rickandmorty.domain.state

sealed class DataState<out T, out E> {

    data object Loading : DataState<Nothing, Nothing>()

    data class Loaded<T>(val data: T) : DataState<T, Nothing>()

    data class Error<E>(val error: E) : DataState<Nothing, E>()
}