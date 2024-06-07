package com.github.dragon925.rickandmorty.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.github.dragon925.rickandmorty.domain.models.Character
import com.github.dragon925.rickandmorty.domain.repository.CharacterRepository
import com.github.dragon925.rickandmorty.domain.repository.LocationRepository
import com.github.dragon925.rickandmorty.domain.repository.LocationState
import com.github.dragon925.rickandmorty.domain.usecase.LoadLocationByIdUseCase
import com.github.dragon925.rickandmorty.domain.usecase.LocationWithCharactersState
import com.github.dragon925.rickandmorty.ui.utils.map
import com.github.dragon925.rickandmorty.ui.utils.toShortItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocationViewModel(
    private val locationId: Long,
    private val loadLocationByIdUseCase: LoadLocationByIdUseCase
) : ViewModel() {

    private val _locations = MutableLiveData<LocationWithCharactersState>()
    val location: LiveData<LocationWithCharacterItemsState> get() = _locations.map { state ->
        state.first to state.second.map { it.map(Character::toShortItem) }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            loadLocationByIdUseCase(locationId).collect {
                _locations.postValue(it)
            }
        }
    }

    class Factory(
        private val locationId: Long,
        private val locationRepository: LocationRepository,
        private val characterRepository: CharacterRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LocationViewModel(
                locationId,
                LoadLocationByIdUseCase(locationRepository, characterRepository)
            ) as T
        }
    }
}

typealias LocationWithCharacterItemsState = Pair<LocationState, CharacterShortItemsState>