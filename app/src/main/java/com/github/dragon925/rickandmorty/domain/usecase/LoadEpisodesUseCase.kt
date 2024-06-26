package com.github.dragon925.rickandmorty.domain.usecase

import com.github.dragon925.rickandmorty.domain.repository.EpisodeRepository
import com.github.dragon925.rickandmorty.domain.repository.EpisodesPageState
import com.github.dragon925.rickandmorty.domain.utils.Filters
import kotlinx.coroutines.flow.Flow
import java.util.EnumMap

class LoadEpisodesUseCase(private val repository: EpisodeRepository) {

    operator fun invoke(
        page: Int? = null,
        filters: EnumMap<Filters.Episode, String>? = null
    ): Flow<EpisodesPageState> = repository.loadEpisodes(page, filters)

}