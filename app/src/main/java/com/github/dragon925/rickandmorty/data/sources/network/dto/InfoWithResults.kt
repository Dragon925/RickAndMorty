package com.github.dragon925.rickandmorty.data.sources.network.dto

import com.google.gson.annotations.SerializedName

data class InfoWithResults<T>(
    @SerializedName(INFO)    val info: Info,
    @SerializedName(RESULTS) val results: List<T>
) {
    companion object {
        const val INFO = "info"
        const val RESULTS = "results"
    }
}
