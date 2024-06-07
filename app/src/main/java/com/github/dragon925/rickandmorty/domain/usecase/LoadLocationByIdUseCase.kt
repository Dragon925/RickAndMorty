package com.github.dragon925.rickandmorty.domain.usecase

import com.github.dragon925.rickandmorty.domain.repository.CharacterRepository
import com.github.dragon925.rickandmorty.domain.repository.CharactersState
import com.github.dragon925.rickandmorty.domain.repository.LocationRepository
import com.github.dragon925.rickandmorty.domain.repository.LocationState
import com.github.dragon925.rickandmorty.domain.state.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoadLocationByIdUseCase(
    private val locationRepository: LocationRepository,
    private val characterRepository: CharacterRepository
) {

    operator fun invoke(id: Long): Flow<LocationWithCharactersState> = flow {
        emit(DataState.Loading to DataState.Loading)
        locationRepository.loadLoacation(id).collect{ locationState -> when(locationState) {
            DataState.Loading -> emit(locationState to DataState.Loading)
            is DataState.Error -> emit(locationState to DataState.Loading)
            is DataState.Loaded -> characterRepository.loadCharactersByIds(locationState.data.residentIds)
                .collect { emit(locationState to it) }
        } }
    }
}

typealias LocationWithCharactersState = Pair<LocationState, CharactersState>