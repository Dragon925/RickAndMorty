package com.github.dragon925.rickandmorty.domain.usecase

import com.github.dragon925.rickandmorty.domain.repository.CharacterRepository
import com.github.dragon925.rickandmorty.domain.repository.CharactersPageState
import kotlinx.coroutines.flow.Flow

class LoadCharactersUseCase(private val repository: CharacterRepository) {

    operator fun invoke(page: Int? = null): Flow<CharactersPageState> = repository.loadCharacters(page)
}