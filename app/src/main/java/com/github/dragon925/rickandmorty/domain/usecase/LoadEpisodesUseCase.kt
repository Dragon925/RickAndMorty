package com.github.dragon925.rickandmorty.domain.usecase

import com.github.dragon925.rickandmorty.domain.repository.EpisodeRepository
import com.github.dragon925.rickandmorty.domain.repository.EpisodesState
import kotlinx.coroutines.flow.Flow

class LoadEpisodesUseCase(private val repository: EpisodeRepository) {

    operator fun invoke(): Flow<EpisodesState> = repository.loadEpisodes()

}