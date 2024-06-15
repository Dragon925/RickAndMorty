package com.github.dragon925.rickandmorty.data.sources.network.api

import com.github.dragon925.rickandmorty.data.sources.network.dto.CharacterDto
import com.github.dragon925.rickandmorty.data.sources.network.dto.InfoWithResults
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface CharacterApi {

    companion object {
        const val ENDPOINT = "character"
        const val ID = "id"
        const val IDS = "ids"
        const val PAGE = "page"
    }

    @GET("$ENDPOINT")
    suspend fun loadAll(
        @Query(PAGE) page: Int? = null,
        @QueryMap filters: Map<String, String> = emptyMap()
    ): Response<InfoWithResults<CharacterDto>>

    @GET("$ENDPOINT/{$ID}")
    suspend fun loadById(@Path(ID) id: Int): Response<CharacterDto>

    @GET("$ENDPOINT/{$IDS}")
    suspend fun loadByIds(@Path(IDS) ids: String): Response<List<CharacterDto>>
}