package com.github.dragon925.rickandmorty.data.repository

import com.github.dragon925.rickandmorty.data.sources.SourcesHandler
import com.github.dragon925.rickandmorty.data.utils.Result
import com.github.dragon925.rickandmorty.domain.models.Character
import com.github.dragon925.rickandmorty.domain.repository.CharacterRepository
import com.github.dragon925.rickandmorty.domain.repository.CharacterState
import com.github.dragon925.rickandmorty.domain.repository.CharactersPageState
import com.github.dragon925.rickandmorty.domain.repository.CharactersState
import com.github.dragon925.rickandmorty.domain.state.DataState
import com.github.dragon925.rickandmorty.domain.utils.Filters
import com.github.dragon925.rickandmorty.domain.utils.toMap
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.EnumMap

class CharacterRepositoryImpl(
    private val source: SourcesHandler<Character>
) : CharacterRepository {

    override fun loadCharacters(
        page: Int?,
        filters: EnumMap<Filters.Character, String>?
    ): Flow<CharactersPageState> = flow {
        emit(DataState.Loading)
        val result = source.loadAll(
            page,
            filters?.toMap { it.name.toString().lowercase() } ?: emptyMap()
        )
        emit(when(result) {
            is Result.Error -> DataState.Error(result.error)
            is Result.Success -> DataState.Loaded(result.result)
        })
    }

    override fun loadCharacter(id: Long): Flow<CharacterState> = flow {
        emit(DataState.Loading)
        val result = source.loadById(id.toInt())
        emit(when(result) {
            is Result.Error -> DataState.Error(result.error)
            is Result.Success -> DataState.Loaded(result.result)
        })
    }

    override fun loadCharactersByIds(ids: List<Long>): Flow<CharactersState> = flow {
        emit(DataState.Loading)
        val result = source.loadByIds(ids.map(Long::toInt))
        emit(when(result) {
            is Result.Error -> DataState.Error(result.error)
            is Result.Success -> DataState.Loaded(result.result)
        })
    }

}