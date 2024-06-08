package com.github.dragon925.rickandmorty.data.repository

import com.github.dragon925.rickandmorty.domain.models.CharacterStatus
import com.github.dragon925.rickandmorty.domain.models.Gender
import com.github.dragon925.rickandmorty.domain.models.Character
import com.github.dragon925.rickandmorty.domain.models.LocationName
import com.github.dragon925.rickandmorty.domain.repository.CharacterRepository
import com.github.dragon925.rickandmorty.domain.repository.CharacterState
import com.github.dragon925.rickandmorty.domain.repository.CharactersState
import com.github.dragon925.rickandmorty.domain.state.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object CharacterRepositoryImpl : CharacterRepository {

    override fun loadCharacters(): Flow<CharactersState> = flow {
        emit(DataState.Loading)
        delay(3000)
        emit(DataState.Loaded<List<Character>>(
            genCharacters()
        ))
    }

    override fun loadCharacter(id: Long): Flow<CharacterState> = flow {
        emit(DataState.Loading)
        delay(3000)
        emit(DataState.Loaded<Character>(
            genCharacters().last()
        ))
    }

    override fun loadCharactersByIds(ids: List<Long>): Flow<CharactersState> = flow {
        emit(DataState.Loading)
        delay(3000)
        emit(DataState.Loaded<List<Character>>(genCharacters()))
    }

    private fun genCharacters() = listOf(
        Character(
            0L,
            "Pro trunk people marriage guy",
            CharacterStatus.ALIVE,
            "Human",
            "",
            Gender.MALE,
            LocationName(0L, "Interdimensional Cable"),
            LocationName(0L, "Interdimensional Cable"),
            "https://rickandmortyapi.com/api/character/avatar/415.jpeg",
            "",
            "",
            List<Long>(1, Int::toLong)
        ),
        Character(
            1L,
            "Jacqueline",
            CharacterStatus.ALIVE,
            "Human",
            "",
            Gender.FEMALE,
            LocationName(1L, "Earth (Replacement Dimension)"),
            LocationName(1L, "Earth (Replacement Dimension)"),
            "https://rickandmortyapi.com/api/character/avatar/170.jpeg",
            "",
            "",
            List<Long>(1, Int::toLong)
        )
    )
}