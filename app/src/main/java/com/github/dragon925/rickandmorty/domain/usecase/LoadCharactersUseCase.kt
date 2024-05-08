package com.github.dragon925.rickandmorty.domain.usecase

import com.github.dragon925.rickandmorty.domain.repository.CharacterRepository
import com.github.dragon925.rickandmorty.domain.repository.CharactersState
import kotlinx.coroutines.flow.Flow

class LoadCharactersUseCase(private val repository: CharacterRepository) {

    operator fun invoke(): Flow<CharactersState> = repository.loadCharacters()
}