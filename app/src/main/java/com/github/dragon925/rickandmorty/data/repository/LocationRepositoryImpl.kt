package com.github.dragon925.rickandmorty.data.repository

import com.github.dragon925.rickandmorty.domain.models.Location
import com.github.dragon925.rickandmorty.domain.repository.LocationRepository
import com.github.dragon925.rickandmorty.domain.repository.LocationState
import com.github.dragon925.rickandmorty.domain.repository.LocationsState
import com.github.dragon925.rickandmorty.domain.state.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object LocationRepositoryImpl : LocationRepository {

    override fun loadLoacations(): Flow<LocationsState> = flow {
        emit(DataState.Loading)
        delay(3000)
        emit(DataState.Loaded(genLocations()))
    }

    override fun loadLoacation(id: Long): Flow<LocationState> = flow {
        emit(DataState.Loading)
        delay(3000)
        emit(DataState.Loaded(genLocations().first()))
    }

    private fun genLocations() = listOf(
        Location(
            0L,
            "Earth",
            "Planet",
            "Dimension C-137",
            "",
            "",
            List<Long>(27, Int::toLong)
        )
    )
}