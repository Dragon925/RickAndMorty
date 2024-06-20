package com.github.dragon925.rickandmorty.data.repository

import com.github.dragon925.rickandmorty.data.sources.SourcesHandler
import com.github.dragon925.rickandmorty.data.utils.Result
import com.github.dragon925.rickandmorty.domain.models.Location
import com.github.dragon925.rickandmorty.domain.repository.LocationRepository
import com.github.dragon925.rickandmorty.domain.repository.LocationState
import com.github.dragon925.rickandmorty.domain.repository.LocationsPageState
import com.github.dragon925.rickandmorty.domain.repository.LocationsState
import com.github.dragon925.rickandmorty.domain.state.DataState
import com.github.dragon925.rickandmorty.domain.utils.Filters
import com.github.dragon925.rickandmorty.domain.utils.toMap
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.EnumMap

class LocationRepositoryImpl(
    private val source: SourcesHandler<Location>
) : LocationRepository {

    override fun loadLoacations(
        page: Int?,
        filters: EnumMap<Filters.Location, String>?
    ): Flow<LocationsPageState> = flow {
        emit(DataState.Loading)
        val result = source.loadAll(
            page,
            filters?.toMap { it.name.toString().lowercase() } ?: emptyMap()
        )
        emit(when(result) {
            is Result.Error -> DataState.Error(result.error)
            is Result.Success -> DataState.Loaded(result.result)
        })
    }

    override fun loadLoacation(id: Long): Flow<LocationState> = flow {
        emit(DataState.Loading)
        val result = source.loadById(id.toInt())
        emit(when(result) {
            is Result.Error -> DataState.Error(result.error)
            is Result.Success -> DataState.Loaded(result.result)
        })
    }

    override fun loadLocationsByIds(ids: List<Long>): Flow<LocationsState> = flow {
        emit(DataState.Loading)
        val result = source.loadByIds(ids.map(Long::toInt))
        emit(when(result) {
            is Result.Error -> DataState.Error(result.error)
            is Result.Success -> DataState.Loaded(result.result)
        })
    }

}