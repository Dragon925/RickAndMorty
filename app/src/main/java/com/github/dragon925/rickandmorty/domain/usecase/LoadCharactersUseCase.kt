package com.github.dragon925.rickandmorty.domain.usecase

import com.github.dragon925.rickandmorty.domain.repository.CharacterRepository
import com.github.dragon925.rickandmorty.domain.repository.CharactersPageState
import com.github.dragon925.rickandmorty.domain.utils.Filters
import kotlinx.coroutines.flow.Flow
import java.util.EnumMap

class LoadCharactersUseCase(private val repository: CharacterRepository) {

    operator fun invoke(
        page: Int? = null,
        filters: EnumMap<Filters.Character, String>? = null
    ): Flow<CharactersPageState> = repository.loadCharacters(page)
}