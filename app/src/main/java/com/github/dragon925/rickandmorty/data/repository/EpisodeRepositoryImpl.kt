package com.github.dragon925.rickandmorty.data.repository

import com.github.dragon925.rickandmorty.data.sources.SourcesHandler
import com.github.dragon925.rickandmorty.domain.models.Episode
import com.github.dragon925.rickandmorty.domain.repository.EpisodeRepository
import com.github.dragon925.rickandmorty.domain.repository.EpisodeState
import com.github.dragon925.rickandmorty.domain.repository.EpisodesPageState
import com.github.dragon925.rickandmorty.domain.repository.EpisodesState
import com.github.dragon925.rickandmorty.domain.state.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class EpisodeRepositoryImpl(
    private val sources: SourcesHandler<Episode>
) : EpisodeRepository {

    override fun loadEpisodes(page: Int?): Flow<EpisodesPageState> = flow {
        emit(DataState.Loading)
        delay(3000)
        emit(DataState.Loaded(genEpisodes()))
    }

    override fun loadEpisode(id: Long): Flow<EpisodeState> = flow {
        emit(DataState.Loading)
        delay(3000)
        emit(DataState.Loaded(genEpisodes().first()))
    }

    override fun loadEpisodesByIds(ids: List<Long>): Flow<EpisodesState> = flow {
        emit(DataState.Loading)
        delay(3000)
        emit(DataState.Loaded(genEpisodes()))
    }

    private fun genEpisodes() = listOf(
        Episode(
            1L,
            "Pilot",
            "December 2, 2013",
            "S01E01",
            "",
            "",
            List<Long>(19, Int::toLong)
        ),
        Episode(
            1L,
            "Rest and Ricklaxation",
            "August 27, 2017",
            "S03E06",
            "",
            "",
            List<Long>(19, Int::toLong)
        )
    )
}