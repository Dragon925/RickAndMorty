package com.github.dragon925.rickandmorty.domain.usecase

import com.github.dragon925.rickandmorty.domain.repository.CharacterRepository
import com.github.dragon925.rickandmorty.domain.repository.CharacterState
import kotlinx.coroutines.flow.Flow

class LoadCharacterByIdUseCase(private val repository: CharacterRepository) {

    operator fun invoke(id: Long): Flow<CharacterState> = repository.loadCharacter(id)
}