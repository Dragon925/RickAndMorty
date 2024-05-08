package com.github.dragon925.rickandmorty.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.github.dragon925.rickandmorty.domain.models.Character
import com.github.dragon925.rickandmorty.domain.repository.CharacterRepository
import com.github.dragon925.rickandmorty.domain.repository.CharactersState
import com.github.dragon925.rickandmorty.domain.state.DataState
import com.github.dragon925.rickandmorty.domain.usecase.LoadCharactersUseCase
import com.github.dragon925.rickandmorty.ui.models.CharacterItem
import com.github.dragon925.rickandmorty.ui.utils.toItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharacterListViewModel(
    private val loadCharactersUseCase: LoadCharactersUseCase
) : ViewModel() {

    private val _charcters = MutableLiveData<CharactersState>()
    val characters: LiveData<CharacterItemsState>
        get() = _charcters.map { state -> when(state) {
            is DataState.Loading -> state
            is DataState.Error -> state
            is DataState.Loaded -> DataState.Loaded(state.data.map(Character::toItem))
        } }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            loadCharactersUseCase().collect {
//                withContext(Dispatchers.Main) {
//                    _charcters.value = it
//                }
                _charcters.postValue(it)
            }
        }
    }

    fun reload() {
        viewModelScope.launch(Dispatchers.IO) {
            loadCharactersUseCase().collect {
//                withContext(Dispatchers.Main) {
//                    _charcters.value = it
//                }
                _charcters.postValue(it)
            }
        }
    }

    class Factory(
        private val repository: CharacterRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CharacterListViewModel(
                LoadCharactersUseCase(repository)
            ) as T
        }
    }

}

typealias CharacterItemsState = DataState<List<CharacterItem>>