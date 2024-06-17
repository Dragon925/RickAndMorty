package com.github.dragon925.rickandmorty.domain.usecase

import com.github.dragon925.rickandmorty.domain.repository.LocationRepository
import com.github.dragon925.rickandmorty.domain.repository.LocationsPageState
import com.github.dragon925.rickandmorty.domain.utils.Filters
import com.github.dragon925.rickandmorty.domain.utils.emptyEnumMap
import kotlinx.coroutines.flow.Flow
import java.util.EnumMap

class LoadLocationsUseCase(private val repository: LocationRepository) {

    operator fun invoke(
        page: Int? = null,
        filters: EnumMap<Filters.Location, String> = emptyEnumMap()
    ): Flow<LocationsPageState> = repository.loadLoacations(page, filters)
}