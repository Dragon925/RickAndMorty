package com.github.dragon925.rickandmorty.domain.usecase

import com.github.dragon925.rickandmorty.domain.repository.LocationRepository
import com.github.dragon925.rickandmorty.domain.repository.LocationState
import kotlinx.coroutines.flow.Flow

class LoadLocationByIdUseCase(private val repository: LocationRepository) {

    operator fun invoke(id: Long): Flow<LocationState> = repository.loadLoacation(id)
}