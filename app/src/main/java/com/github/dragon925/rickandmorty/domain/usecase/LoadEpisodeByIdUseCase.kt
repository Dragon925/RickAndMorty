package com.github.dragon925.rickandmorty.domain.usecase

import com.github.dragon925.rickandmorty.domain.repository.EpisodeRepository
import com.github.dragon925.rickandmorty.domain.repository.EpisodeState
import kotlinx.coroutines.flow.Flow

class LoadEpisodeByIdUseCase(private val repository: EpisodeRepository) {

    operator fun invoke(id: Long): Flow<EpisodeState> = repository.loadEpisode(id)
}