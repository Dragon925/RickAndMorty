package com.github.dragon925.rickandmorty.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.github.dragon925.rickandmorty.domain.errors.Error
import com.github.dragon925.rickandmorty.domain.models.Character
import com.github.dragon925.rickandmorty.domain.repository.CharacterRepository
import com.github.dragon925.rickandmorty.domain.repository.CharactersPageState
import com.github.dragon925.rickandmorty.domain.state.DataState
import com.github.dragon925.rickandmorty.domain.usecase.LoadCharactersUseCase
import com.github.dragon925.rickandmorty.ui.models.CharacterItem
import com.github.dragon925.rickandmorty.ui.models.CharacterShortItem
import com.github.dragon925.rickandmorty.ui.utils.map
import com.github.dragon925.rickandmorty.ui.utils.toItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharacterListViewModel(
    private val loadCharactersUseCase: LoadCharactersUseCase
) : ViewModel() {

    private val _charcters = MutableLiveData<CharactersPageState>()
    val characters: LiveData<CharacterItemsState> get() = _charcters.map { state ->
        state.map { it.list.map(Character::toItem) }
    }

    init { reload() }

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

typealias CharacterItemsState = DataState<List<CharacterItem>, Error>
typealias CharacterShortItemsState = DataState<List<CharacterShortItem>, Error>