package com.github.dragon925.rickandmorty.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.github.dragon925.rickandmorty.domain.models.Character
import com.github.dragon925.rickandmorty.domain.models.Episode
import com.github.dragon925.rickandmorty.domain.repository.CharacterRepository
import com.github.dragon925.rickandmorty.domain.repository.CharacterState
import com.github.dragon925.rickandmorty.domain.repository.EpisodeRepository
import com.github.dragon925.rickandmorty.domain.state.DataState
import com.github.dragon925.rickandmorty.domain.usecase.CharacterWithEpisodesState
import com.github.dragon925.rickandmorty.domain.usecase.LoadCharacterByIdUseCase
import com.github.dragon925.rickandmorty.ui.utils.map
import com.github.dragon925.rickandmorty.ui.utils.toShortItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharacterViewModel(
    private val characterId: Long,
    private val loadCharacterByIdUseCase: LoadCharacterByIdUseCase,
) : ViewModel() {

    private val _character = MutableLiveData<CharacterWithEpisodesState>()
    val character: LiveData<CharacterWithEpisodeItemsState> get() = _character.map { state ->
        state.first to state.second.map { it.map(Episode::toShortItem) } }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            loadCharacterByIdUseCase(characterId).collect {
                _character.postValue(it)
            }
        }
    }

    class Factory(
        private val characterId: Long,
        private val characterRepository: CharacterRepository,
        private val episodeRepository: EpisodeRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CharacterViewModel(
                characterId,
                LoadCharacterByIdUseCase(characterRepository, episodeRepository)
            ) as T
        }
    }
}

typealias CharacterWithEpisodeItemsState = Pair<CharacterState, EpisodeShortItemsState>