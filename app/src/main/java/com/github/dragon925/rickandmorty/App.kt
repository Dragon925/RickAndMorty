package com.github.dragon925.rickandmorty

import android.app.Application
import com.github.dragon925.rickandmorty.data.sources.network.RetrofitClient
import com.github.dragon925.rickandmorty.data.sources.network.api.Api
import retrofit2.Retrofit

class App : Application() {

    private val retrofit: Retrofit by lazy { RetrofitClient.getClient() }

    val characterApi: Api.Character by lazy { Api.Character(RetrofitClient.getCharacterApi(retrofit)) }
    val locationApi: Api.Location by lazy { Api.Location(RetrofitClient.getLocationApi(retrofit)) }
    val episodeApi: Api.Episode by lazy { Api.Episode(RetrofitClient.getEpisodeApi(retrofit)) }
}