package com.github.dragon925.rickandmorty.domain.usecase

import com.github.dragon925.rickandmorty.domain.repository.CharacterRepository
import com.github.dragon925.rickandmorty.domain.repository.CharactersState
import com.github.dragon925.rickandmorty.domain.repository.EpisodeRepository
import com.github.dragon925.rickandmorty.domain.repository.EpisodeState
import com.github.dragon925.rickandmorty.domain.state.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoadEpisodeByIdUseCase(
    private val episodeRepository: EpisodeRepository,
    private val characterRepository: CharacterRepository
) {

    operator fun invoke(id: Long): Flow<EpisodeWithCharactersState> = flow {
        emit(DataState.Loading to DataState.Loading)
        episodeRepository.loadEpisode(id).collect { episodeState -> when(episodeState) {
            DataState.Loading -> emit(episodeState to DataState.Loading)
            is DataState.Error -> emit(episodeState to DataState.Loading)
            is DataState.Loaded -> characterRepository.loadCharactersByIds(episodeState.data.characterIds)
                .collect{ emit(episodeState to it) }
        } }
    }
}

typealias EpisodeWithCharactersState = Pair<EpisodeState, CharactersState>