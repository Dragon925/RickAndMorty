package com.github.dragon925.rickandmorty.data.sources.network.dto

import com.google.gson.annotations.SerializedName

data class Results<T>(
    @SerializedName(RESULTS) val results: List<T>
) {
    companion object {
        const val RESULTS = "results"
    }
}
