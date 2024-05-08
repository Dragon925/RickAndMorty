package com.github.dragon925.rickandmorty.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.github.dragon925.rickandmorty.domain.models.Location
import com.github.dragon925.rickandmorty.domain.repository.LocationRepository
import com.github.dragon925.rickandmorty.domain.repository.LocationsState
import com.github.dragon925.rickandmorty.domain.state.DataState
import com.github.dragon925.rickandmorty.domain.usecase.LoadLocationsUseCase
import com.github.dragon925.rickandmorty.ui.models.LocationItem
import com.github.dragon925.rickandmorty.ui.utils.toItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocationListViewModel(
    private val loadLocationsUseCase: LoadLocationsUseCase
) : ViewModel() {

    private val _locations = MutableLiveData<LocationsState>()
    val locations: LiveData<LocationItemsState> get() = _locations.map { state -> when(state) {
        is DataState.Loading -> state
        is DataState.Error -> state
        is DataState.Loaded -> DataState.Loaded(state.data.map(Location::toItem))
    } }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            loadLocationsUseCase().collect{ _locations.postValue(it) }
        }
    }

    fun reload() {
        viewModelScope.launch(Dispatchers.IO) {
            loadLocationsUseCase().collect{ _locations.postValue(it) }
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

typealias LocationItemsState = DataState<List<LocationItem>>