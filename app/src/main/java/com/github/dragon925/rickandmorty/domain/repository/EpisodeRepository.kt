package com.github.dragon925.rickandmorty.domain.repository

import com.github.dragon925.rickandmorty.domain.models.Episode
import com.github.dragon925.rickandmorty.domain.state.DataState
import kotlinx.coroutines.flow.Flow

interface EpisodeRepository {

    fun loadEpisodes(): Flow<EpisodesState>

    fun loadEpisode(id: Long): Flow<EpisodeState>
}

typealias EpisodesState = DataState<List<Episode>>
typealias EpisodeState = DataState<Episode>