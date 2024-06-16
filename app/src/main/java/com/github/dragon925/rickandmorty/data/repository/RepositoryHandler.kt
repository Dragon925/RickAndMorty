package com.github.dragon925.rickandmorty.data.repository

import com.github.dragon925.rickandmorty.data.sources.RemoteSource
import com.github.dragon925.rickandmorty.data.sources.SourcesHandler
import com.github.dragon925.rickandmorty.data.sources.network.api.Api
import com.github.dragon925.rickandmorty.domain.repository.CharacterRepository
import com.github.dragon925.rickandmorty.domain.repository.EpisodeRepository
import com.github.dragon925.rickandmorty.domain.repository.LocationRepository
import retrofit2.Retrofit

object RepositoryHandler {

    private lateinit var characterRepository: CharacterRepository
    private lateinit var locationRepository: LocationRepository
    private lateinit var episodeRepository: EpisodeRepository

    fun getCharacterRepository(api: Api.Character): CharacterRepository {
        if (!this::characterRepository.isInitialized) {
            characterRepository = CharacterRepositoryImpl(
                SourcesHandler(
                    RemoteSource(api)
                )
            )
        }
        return characterRepository
    }

    fun getLocationRepository(api: Api.Location): LocationRepository {
        if (!this::locationRepository.isInitialized) {
            locationRepository = LocationRepositoryImpl(
                SourcesHandler(
                    RemoteSource(api)
                )
            )
        }
        return locationRepository
    }

    fun getEpisodeRepository(api: Api.Episode): EpisodeRepository {
        if (!this::episodeRepository.isInitialized) {
            episodeRepository = EpisodeRepositoryImpl(
                SourcesHandler(
                    RemoteSource(api)
                )
            )
        }
        return episodeRepository
    }
}