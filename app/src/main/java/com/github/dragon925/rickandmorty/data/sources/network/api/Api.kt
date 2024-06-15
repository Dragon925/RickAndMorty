package com.github.dragon925.rickandmorty.data.sources.network.api

import com.github.dragon925.rickandmorty.data.sources.network.dto.CharacterDto
import com.github.dragon925.rickandmorty.data.sources.network.dto.Convertable
import com.github.dragon925.rickandmorty.data.sources.network.dto.EpisodeDto
import com.github.dragon925.rickandmorty.data.sources.network.dto.InfoWithResults
import com.github.dragon925.rickandmorty.data.sources.network.dto.LocationDto
import com.github.dragon925.rickandmorty.domain.models.Character
import com.github.dragon925.rickandmorty.domain.models.Episode
import com.github.dragon925.rickandmorty.domain.models.Location
import com.github.dragon925.rickandmorty.domain.models.Model
import retrofit2.Response

private typealias CharacterModel = Character
private typealias LocationModel = Location
private typealias EpisodeModel = Episode
sealed interface Api<T: Model, K: Convertable<T>> {

    suspend fun loadAll(
        page: Int? = null,
        filters: Map<String, String> = emptyMap()
    ): Response<InfoWithResults<K>>
    suspend fun loadById(id: Int): Response<K>
    suspend fun loadByIds(ids: String): Response<List<K>>

    data class Character(private val api: CharacterApi) : Api<CharacterModel, CharacterDto> {
        override suspend fun loadAll(
            page: Int?,
            filters: Map<String, String>
        ): Response<InfoWithResults<CharacterDto>> = api.loadAll(page, filters)

        override suspend fun loadById(id: Int): Response<CharacterDto> = api.loadById(id)

        override suspend fun loadByIds(ids: String): Response<List<CharacterDto>> = api.loadByIds(ids)
    }

    data class Location(private val api: LocationApi) : Api<LocationModel, LocationDto> {
        override suspend fun loadAll(
            page: Int?,
            filters: Map<String, String>
        ): Response<InfoWithResults<LocationDto>> = api.loadAll(page, filters)

        override suspend fun loadById(id: Int): Response<LocationDto> = api.loadById(id)

        override suspend fun loadByIds(ids: String): Response<List<LocationDto>> = api.loadByIds(ids)
    }

    data class Episode(private val api: EpisodeApi) : Api<EpisodeModel, EpisodeDto> {
        override suspend fun loadAll(
            page: Int?,
            filters: Map<String, String>
        ): Response<InfoWithResults<EpisodeDto>> = api.loadAll(page, filters)

        override suspend fun loadById(id: Int): Response<EpisodeDto> = api.loadById(id)

        override suspend fun loadByIds(ids: String): Response<List<EpisodeDto>> = api.loadByIds(ids)
    }
}