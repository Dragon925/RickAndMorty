package com.github.dragon925.rickandmorty.data.utils

sealed interface Result<out S, out E> {
    data class Success<S>(val result: S) : Result<S, Nothing>

    data class Error<E>(val error: E) : Result<Nothing, E>
}