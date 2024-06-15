package com.github.dragon925.rickandmorty.data.sources.network.dto

import com.github.dragon925.rickandmorty.domain.models.Character
import com.github.dragon925.rickandmorty.domain.models.CharacterStatus
import com.github.dragon925.rickandmorty.domain.models.Gender
import com.google.gson.annotations.SerializedName

data class CharacterDto(
    @SerializedName(ID)       val id: Int,
    @SerializedName(NAME)     val name: String,
    @SerializedName(STATUS)   val status: String,
    @SerializedName(SPECIES)  val species: String,
    @SerializedName(TYPE)     val type: String,
    @SerializedName(GENDER)   val gender: String,
    @SerializedName(ORIGIN)   val origin: LocationNameDto,
    @SerializedName(LOCATION) val location: LocationNameDto,
    @SerializedName(IMAGE)    val image: String,
    @SerializedName(EPISODE)  val episode: List<String>,
    @SerializedName(URL)      val url: String,
    @SerializedName(CREATED)  val created: String
) : Convertable<Character> {
    companion object {
        const val ID = "id"
        const val NAME = "name"
        const val STATUS = "status"
        const val SPECIES = "species"
        const val TYPE = "type"
        const val GENDER = "gender"
        const val ORIGIN = "origin"
        const val LOCATION = "location"
        const val IMAGE = "image"
        const val EPISODE = "episode"
        const val URL = "url"
        const val CREATED = "created"
    }

    override fun toModel() = Character(
        id = id.toLong(),
        name = name,
        status = when(status.lowercase()) {
            "alive" -> CharacterStatus.ALIVE
            "dead" -> CharacterStatus.DEAD
            else -> CharacterStatus.UNKNOWN
        },
        species = species,
        type = type,
        gender = when(gender.lowercase()) {
            "female" -> Gender.FEMALE
            "male" -> Gender.MALE
            "genderless" -> Gender.GENDERLESS
            else -> Gender.UNKNOWN
        },
        origin = origin.toModel(),
        location = location.toModel(),
        image = image,
        url = url,
        created = created,
        episodIds = episode.map { url ->
            url.substringAfterLast('/', "-1").toLongOrNull() ?: -1
        }
    )
}
