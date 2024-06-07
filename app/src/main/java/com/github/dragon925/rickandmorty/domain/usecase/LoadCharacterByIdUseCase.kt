package com.github.dragon925.rickandmorty.domain.usecase

import com.github.dragon925.rickandmorty.domain.repository.CharacterRepository
import com.github.dragon925.rickandmorty.domain.repository.CharacterState
import com.github.dragon925.rickandmorty.domain.repository.CharactersState
import com.github.dragon925.rickandmorty.domain.repository.EpisodeRepository
import com.github.dragon925.rickandmorty.domain.repository.EpisodesState
import com.github.dragon925.rickandmorty.domain.state.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion

class LoadCharacterByIdUseCase(
    private val characterRepository: CharacterRepository,
    private val episodeRepository: EpisodeRepository
) {

    operator fun invoke(id: Long): Flow<CharacterWithEpisodesState> = flow {
        emit(DataState.Loading to DataState.Loading)
        characterRepository.loadCharacter(id).collect { characterState -> when(characterState) {
            DataState.Loading -> emit(characterState to DataState.Loading)
            is DataState.Error -> emit(characterState to DataState.Loading)
            is DataState.Loaded -> episodeRepository.loadEpisodesByIds(characterState.data.episodIds)
                .collect { emit(characterState to it) }
        } }
    }
}

typealias CharacterWithEpisodesState = Pair<CharacterState, EpisodesState>