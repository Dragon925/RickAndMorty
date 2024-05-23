package com.github.dragon925.rickandmorty.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.github.dragon925.rickandmorty.domain.repository.EpisodeRepository
import com.github.dragon925.rickandmorty.domain.repository.EpisodeState
import com.github.dragon925.rickandmorty.domain.usecase.LoadEpisodeByIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EpisodeViewModel(
    private val episodeId: Long,
    private val loadEpisodeByIdUseCase: LoadEpisodeByIdUseCase
) : ViewModel() {

    private val _episodes = MutableLiveData<EpisodeState>()
    val episodes: LiveData<EpisodeState> get() = _episodes

    init {
        viewModelScope.launch(Dispatchers.IO) {
            loadEpisodeByIdUseCase(episodeId).collect {
                _episodes.postValue(it)
            }
        }
    }

    class Factory(
        private val episodeId: Long,
        private val episodeRepository: EpisodeRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return EpisodeViewModel(
                episodeId,
                LoadEpisodeByIdUseCase(episodeRepository)
            ) as T
        }
    }
}