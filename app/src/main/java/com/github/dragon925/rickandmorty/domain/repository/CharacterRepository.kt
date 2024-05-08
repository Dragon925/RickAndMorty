package com.github.dragon925.rickandmorty.domain.repository

import com.github.dragon925.rickandmorty.domain.models.Character
import com.github.dragon925.rickandmorty.domain.state.DataState
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    fun loadCharacters(): Flow<CharactersState>

    fun loadCharacter(id: Long): Flow<CharacterState>
}

typealias CharactersState = DataState<List<Character>>
typealias CharacterState = DataState<Character>