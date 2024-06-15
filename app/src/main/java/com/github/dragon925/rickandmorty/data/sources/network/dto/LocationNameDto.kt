package com.github.dragon925.rickandmorty.data.sources.network.dto

import com.github.dragon925.rickandmorty.domain.models.LocationName
import com.google.gson.annotations.SerializedName

data class LocationNameDto(
    @SerializedName(NAME) val name: String,
    @SerializedName(URL) val url: String
) : Convertable<LocationName> {
    companion object {
        const val NAME = "name"
        const val URL = "url"
    }

    override fun toModel() = LocationName(
        id = url.substringAfterLast("/", missingDelimiterValue = "")
            .ifBlank { "-1" }
            .toLongOrNull() ?: -1,
        name = name
    )
}
