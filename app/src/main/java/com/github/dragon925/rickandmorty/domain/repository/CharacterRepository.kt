package com.github.dragon925.rickandmorty.domain.repository

import com.github.dragon925.rickandmorty.domain.errors.Error
import com.github.dragon925.rickandmorty.domain.models.Character
import com.github.dragon925.rickandmorty.domain.models.Page
import com.github.dragon925.rickandmorty.domain.state.DataState
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    fun loadCharacters(page: Int? = null): Flow<CharactersPageState>

    fun loadCharacter(id: Long): Flow<CharacterState>

    fun loadCharactersByIds(ids: List<Long>): Flow<CharactersState>
}

typealias CharactersPageState = DataState<Page<Character>, Error>
typealias CharactersState = DataState<List<Character>, Error>
typealias CharacterState = DataState<Character, Error>