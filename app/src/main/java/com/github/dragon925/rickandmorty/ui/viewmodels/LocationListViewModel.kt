package com.github.dragon925.rickandmorty.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.github.dragon925.rickandmorty.domain.errors.Error
import com.github.dragon925.rickandmorty.domain.models.Location
import com.github.dragon925.rickandmorty.domain.repository.LocationRepository
import com.github.dragon925.rickandmorty.domain.repository.LocationsPageState
import com.github.dragon925.rickandmorty.domain.state.DataState
import com.github.dragon925.rickandmorty.domain.usecase.LoadLocationsUseCase
import com.github.dragon925.rickandmorty.ui.models.LocationItem
import com.github.dragon925.rickandmorty.ui.utils.map
import com.github.dragon925.rickandmorty.ui.utils.toItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocationListViewModel(
    private val loadLocationsUseCase: LoadLocationsUseCase
) : ViewModel() {

    private val _locations = MutableLiveData<LocationsPageState>()
    val locations: LiveData<LocationItemsState> get() = _locations.map { state ->
        state.map { it.list.map(Location::toItem) }
    }

    init { reload() }

    fun reload() {
        viewModelScope.launch(Dispatchers.IO) {
            loadLocationsUseCase().collect{ _locations.postValue(it) }
        }
    }

    fun loadNextPage() {
        val page = (_locations.value as? DataState.Loaded)?.data?.takeIf { it.hasNext } ?: return

        val currentList = page.list.toMutableList()
        viewModelScope.launch(Dispatchers.IO) {
            loadLocationsUseCase(page.current + 1).collect { state ->
                _locations.postValue(
                    state.map { it.copy(list = currentList.apply { addAll(it.list) }) }
                )
            }
        }
    }


    class Factory(
        private val repository: LocationRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LocationListViewModel(
                LoadLocationsUseCase(repository)
            ) as T
        }
    }
}

typealias LocationItemsState = DataState<List<LocationItem>, Error>