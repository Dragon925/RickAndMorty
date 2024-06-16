package com.github.dragon925.rickandmorty.domain.repository

import com.github.dragon925.rickandmorty.domain.errors.Error
import com.github.dragon925.rickandmorty.domain.models.Episode
import com.github.dragon925.rickandmorty.domain.models.Page
import com.github.dragon925.rickandmorty.domain.state.DataState
import kotlinx.coroutines.flow.Flow

interface EpisodeRepository {

    fun loadEpisodes(page: Int? = null): Flow<EpisodesPageState>

    fun loadEpisode(id: Long): Flow<EpisodeState>

    fun loadEpisodesByIds(ids: List<Long>): Flow<EpisodesState>
}

typealias EpisodesPageState = DataState<Page<Episode>, Error>
typealias EpisodesState = DataState<List<Episode>, Error>
typealias EpisodeState = DataState<Episode, Error>