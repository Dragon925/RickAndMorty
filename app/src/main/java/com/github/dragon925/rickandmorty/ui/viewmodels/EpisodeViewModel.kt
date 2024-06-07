package com.github.dragon925.rickandmorty.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.github.dragon925.rickandmorty.domain.models.Character
import com.github.dragon925.rickandmorty.domain.repository.CharacterRepository
import com.github.dragon925.rickandmorty.domain.repository.EpisodeRepository
import com.github.dragon925.rickandmorty.domain.repository.EpisodeState
import com.github.dragon925.rickandmorty.domain.usecase.EpisodeWithCharactersState
import com.github.dragon925.rickandmorty.domain.usecase.LoadEpisodeByIdUseCase
import com.github.dragon925.rickandmorty.ui.utils.map
import com.github.dragon925.rickandmorty.ui.utils.toShortItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EpisodeViewModel(
    private val episodeId: Long,
    private val loadEpisodeByIdUseCase: LoadEpisodeByIdUseCase
) : ViewModel() {

    private val _episodes = MutableLiveData<EpisodeWithCharactersState>()
    val episodes: LiveData<EpisodeWithCharacterItemsState> get() = _episodes.map { state ->
        state.first to state.second.map { it.map(Character::toShortItem) }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            loadEpisodeByIdUseCase(episodeId).collect {
                _episodes.postValue(it)
            }
        }
    }

    class Factory(
        private val episodeId: Long,
        private val episodeRepository: EpisodeRepository,
        private val characterRepository: CharacterRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return EpisodeViewModel(
                episodeId,
                LoadEpisodeByIdUseCase(episodeRepository, characterRepository)
            ) as T
        }
    }
}

typealias EpisodeWithCharacterItemsState = Pair<EpisodeState, CharacterShortItemsState>