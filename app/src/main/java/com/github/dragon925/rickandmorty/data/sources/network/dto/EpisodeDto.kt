package com.github.dragon925.rickandmorty.data.sources.network.dto

import com.github.dragon925.rickandmorty.domain.models.Episode
import com.google.gson.annotations.SerializedName

data class EpisodeDto(
    @SerializedName(ID)         val id: Int,
    @SerializedName(NAME)       val name: String,
    @SerializedName(AIR_DATE)   val airDate: String,
    @SerializedName(EPISODE)    val episode: String,
    @SerializedName(CHARACTERS) val characters: List<String>,
    @SerializedName(URL)        val url: String,
    @SerializedName(CREATED)    val created: String
) : Convertable<Episode> {
    companion object {
        const val ID = "id"
        const val NAME = "name"
        const val AIR_DATE = "air_date"
        const val EPISODE = "episode"
        const val CHARACTERS = "characters"
        const val URL = "url"
        const val CREATED = "created"
    }

    override fun toModel() = Episode(
        id = id.toLong(),
        name = name,
        airDate = airDate,
        episode = episode,
        url = url,
        created = created,
        characterIds = characters.map { url ->
            url.substringAfterLast('/', "-1").toLongOrNull() ?: -1
        }
    )
}
