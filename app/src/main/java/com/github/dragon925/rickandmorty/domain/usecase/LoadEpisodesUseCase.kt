package com.github.dragon925.rickandmorty.domain.usecase

import com.github.dragon925.rickandmorty.domain.repository.EpisodeRepository
import com.github.dragon925.rickandmorty.domain.repository.EpisodesPageState
import kotlinx.coroutines.flow.Flow

class LoadEpisodesUseCase(private val repository: EpisodeRepository) {

    operator fun invoke(page: Int? = null): Flow<EpisodesPageState> = repository.loadEpisodes(page)

}