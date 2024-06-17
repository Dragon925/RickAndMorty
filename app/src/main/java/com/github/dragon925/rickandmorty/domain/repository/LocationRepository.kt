package com.github.dragon925.rickandmorty.domain.repository

import com.github.dragon925.rickandmorty.domain.errors.Error
import com.github.dragon925.rickandmorty.domain.models.Location
import com.github.dragon925.rickandmorty.domain.models.Page
import com.github.dragon925.rickandmorty.domain.state.DataState
import com.github.dragon925.rickandmorty.domain.utils.Filters
import com.github.dragon925.rickandmorty.domain.utils.emptyEnumMap
import kotlinx.coroutines.flow.Flow
import java.util.EnumMap

interface LocationRepository {

    fun loadLoacations(
        page: Int? = null,
        filters: EnumMap<Filters.Location, String> = emptyEnumMap()
    ): Flow<LocationsPageState>

    fun loadLoacation(id: Long): Flow<LocationState>

    fun loadLocationsByIds(ids: List<Long>): Flow<LocationsState>
}

typealias LocationsPageState = DataState<Page<Location>, Error>
typealias LocationsState = DataState<List<Location>, Error>
typealias LocationState = DataState<Location, Error>