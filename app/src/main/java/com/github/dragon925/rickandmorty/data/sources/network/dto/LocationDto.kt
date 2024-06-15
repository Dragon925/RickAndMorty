package com.github.dragon925.rickandmorty.data.sources.network.dto

import com.github.dragon925.rickandmorty.domain.models.Location
import com.google.gson.annotations.SerializedName

data class LocationDto(
    @SerializedName(ID)        val id: Int,
    @SerializedName(NAME)      val name: String,
    @SerializedName(TYPE)      val type: String,
    @SerializedName(DIMENSION) val dimension: String,
    @SerializedName(RESIDENTS) val residents: List<String>,
    @SerializedName(URL)       val url: String,
    @SerializedName(CREATED)   val created: String
) : Convertable<Location> {
    companion object {
        const val ID = "id"
        const val NAME = "name"
        const val TYPE = "type"
        const val DIMENSION = "dimension"
        const val RESIDENTS = "residents"
        const val URL = "url"
        const val CREATED = "created"
    }

    override fun toModel() = Location(
        id = id.toLong(),
        name = name,
        type = type,
        dimension = dimension,
        url = url,
        created = created,
        residentIds = residents.map { url ->
            url.substringAfterLast('/', "-1").toLongOrNull() ?: -1
        }
    )
}
