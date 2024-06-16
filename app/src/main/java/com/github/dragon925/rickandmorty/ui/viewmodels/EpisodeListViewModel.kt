package com.github.dragon925.rickandmorty.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.github.dragon925.rickandmorty.domain.errors.Error
import com.github.dragon925.rickandmorty.domain.models.Episode
import com.github.dragon925.rickandmorty.domain.repository.EpisodeRepository
import com.github.dragon925.rickandmorty.domain.repository.EpisodesPageState
import com.github.dragon925.rickandmorty.domain.state.DataState
import com.github.dragon925.rickandmorty.domain.usecase.LoadEpisodesUseCase
import com.github.dragon925.rickandmorty.ui.models.EpisodeItem
import com.github.dragon925.rickandmorty.ui.models.EpisodeShortItem
import com.github.dragon925.rickandmorty.ui.utils.map
import com.github.dragon925.rickandmorty.ui.utils.toItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EpisodeListViewModel(
    private val loadEpisodesUseCase: LoadEpisodesUseCase
) : ViewModel() {

    private val _episodes = MutableLiveData<EpisodesPageState>()
    val episodes: LiveData<EpisodeItemsState> get() = _episodes.map { state ->
        state.map { it.list.map(Episode::toItem) }
    }

    init { reload() }

    fun reload() {
        viewModelScope.launch(Dispatchers.IO) {
            loadEpisodesUseCase().collect{ _episodes.postValue(it) }
        }
    }

    fun loadNextPage() {
        val page = (_episodes.value as? DataState.Loaded)?.data?.takeIf { it.hasNext } ?: return

        val currentList = page.list.toMutableList()
        viewModelScope.launch(Dispatchers.IO) {
            loadEpisodesUseCase(page.current + 1).collect { state ->
                _episodes.postValue(
                    state.map { it.copy(list = currentList.apply { addAll(it.list) }) }
                )
            }
        }
    }

    class Factory(
        private val repository: EpisodeRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return EpisodeListViewModel(
                LoadEpisodesUseCase(repository)
            ) as T
        }
    }
}

typealias EpisodeItemsState = DataState<List<EpisodeItem>, Error>
typealias EpisodeShortItemsState = DataState<List<EpisodeShortItem>, Error>