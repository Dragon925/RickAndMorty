package com.github.dragon925.rickandmorty.domain.repository

import com.github.dragon925.rickandmorty.domain.errors.Error
import com.github.dragon925.rickandmorty.domain.models.Episode
import com.github.dragon925.rickandmorty.domain.models.Page
import com.github.dragon925.rickandmorty.domain.state.DataState
import com.github.dragon925.rickandmorty.domain.utils.Filters
import com.github.dragon925.rickandmorty.domain.utils.emptyEnumMap
import kotlinx.coroutines.flow.Flow
import java.util.EnumMap

interface EpisodeRepository {

    fun loadEpisodes(
        page: Int? = null,
        filters: EnumMap<Filters.Episode, String> = emptyEnumMap()
    ): Flow<EpisodesPageState>

    fun loadEpisode(id: Long): Flow<EpisodeState>

    fun loadEpisodesByIds(ids: List<Long>): Flow<EpisodesState>
}

typealias EpisodesPageState = DataState<Page<Episode>, Error>
typealias EpisodesState = DataState<List<Episode>, Error>
typealias EpisodeState = DataState<Episode, Error>