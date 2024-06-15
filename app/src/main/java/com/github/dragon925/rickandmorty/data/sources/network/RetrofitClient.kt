package com.github.dragon925.rickandmorty.data.sources.network

import com.github.dragon925.rickandmorty.data.sources.network.api.CharacterApi
import com.github.dragon925.rickandmorty.data.sources.network.api.EpisodeApi
import com.github.dragon925.rickandmorty.data.sources.network.api.LocationApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL ="https://rickandmortyapi.com/api/"

    fun getClient(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getCharacterApi(retrofit: Retrofit) = retrofit.create(CharacterApi::class.java)

    fun getLocationApi(retrofit: Retrofit) = retrofit.create(LocationApi::class.java)

    fun getEpisodeApi(retrofit: Retrofit) = retrofit.create(EpisodeApi::class.java)

}