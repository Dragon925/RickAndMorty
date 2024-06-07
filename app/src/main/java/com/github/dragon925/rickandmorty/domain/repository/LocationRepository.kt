package com.github.dragon925.rickandmorty.domain.repository

import com.github.dragon925.rickandmorty.domain.models.Location
import com.github.dragon925.rickandmorty.domain.state.DataState
import kotlinx.coroutines.flow.Flow

interface LocationRepository {

    fun loadLoacations(): Flow<LocationsState>

    fun loadLoacation(id: Long): Flow<LocationState>

    fun loadLocationsByIds(ids: List<Long>): Flow<LocationsState>
}

typealias LocationsState = DataState<List<Location>>
typealias LocationState = DataState<Location>