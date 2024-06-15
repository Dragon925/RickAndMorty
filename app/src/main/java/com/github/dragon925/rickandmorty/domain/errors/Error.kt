package com.github.dragon925.rickandmorty.domain.errors

sealed interface Error {

    enum class Network : Error {
        NO_CONNECTION,
        NOT_FOUND,
        INCORRECT_REQUEST,
        SERVER_FAILURE,
        UNKNOWN
    }
}