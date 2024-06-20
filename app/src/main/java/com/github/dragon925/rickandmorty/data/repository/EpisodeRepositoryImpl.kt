package com.github.dragon925.rickandmorty.data.repository

import com.github.dragon925.rickandmorty.data.sources.SourcesHandler
import com.github.dragon925.rickandmorty.data.utils.Result
import com.github.dragon925.rickandmorty.domain.models.Episode
import com.github.dragon925.rickandmorty.domain.repository.EpisodeRepository
import com.github.dragon925.rickandmorty.domain.repository.EpisodeState
import com.github.dragon925.rickandmorty.domain.repository.EpisodesPageState
import com.github.dragon925.rickandmorty.domain.repository.EpisodesState
import com.github.dragon925.rickandmorty.domain.state.DataState
import com.github.dragon925.rickandmorty.domain.utils.Filters
import com.github.dragon925.rickandmorty.domain.utils.toMap
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.EnumMap

class EpisodeRepositoryImpl(
    private val sources: SourcesHandler<Episode>
) : EpisodeRepository {

    override fun loadEpisodes(
        page: Int?,
        filters: EnumMap<Filters.Episode, String>?
    ): Flow<EpisodesPageState> = flow {
        emit(DataState.Loading)
        val result = sources.loadAll(
            page,
            filters?.toMap { it.name.toString().lowercase() } ?: emptyMap()
        )
        emit(when(result) {
            is Result.Error -> DataState.Error(result.error)
            is Result.Success -> DataState.Loaded(result.result)
        })
    }

    override fun loadEpisode(id: Long): Flow<EpisodeState> = flow {
        emit(DataState.Loading)
        val result = sources.loadById(id.toInt())
        emit(when(result) {
            is Result.Error -> DataState.Error(result.error)
            is Result.Success -> DataState.Loaded(result.result)
        })
    }

    override fun loadEpisodesByIds(ids: List<Long>): Flow<EpisodesState> = flow {
        emit(DataState.Loading)
        val result = sources.loadByIds(ids.map(Long::toInt))
        emit(when(result) {
            is Result.Error -> DataState.Error(result.error)
            is Result.Success -> DataState.Loaded(result.result)
        })
    }

}