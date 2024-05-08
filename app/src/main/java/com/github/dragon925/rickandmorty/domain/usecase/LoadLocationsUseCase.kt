package com.github.dragon925.rickandmorty.domain.usecase

import com.github.dragon925.rickandmorty.domain.repository.LocationRepository
import com.github.dragon925.rickandmorty.domain.repository.LocationsState
import kotlinx.coroutines.flow.Flow

class LoadLocationsUseCase(private val repository: LocationRepository) {

    operator fun invoke(): Flow<LocationsState> = repository.loadLoacations()
}