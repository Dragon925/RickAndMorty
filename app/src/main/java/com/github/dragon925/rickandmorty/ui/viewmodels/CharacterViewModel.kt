package com.github.dragon925.rickandmorty.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.github.dragon925.rickandmorty.domain.repository.CharacterRepository
import com.github.dragon925.rickandmorty.domain.repository.CharacterState
import com.github.dragon925.rickandmorty.domain.usecase.LoadCharacterByIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharacterViewModel(
    private val characterId: Long,
    private val loadCharacterByIdUseCase: LoadCharacterByIdUseCase,
) : ViewModel() {

    private val _character = MutableLiveData<CharacterState>()
    val character: LiveData<CharacterState> get() = _character

    init {
        viewModelScope.launch(Dispatchers.IO) {
            loadCharacterByIdUseCase(characterId).collect {
                _character.postValue(it)
            }
        }
    }

    class Factory(
        private val characterId: Long,
        private val characterRepository: CharacterRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CharacterViewModel(
                characterId,
                LoadCharacterByIdUseCase(characterRepository)
            ) as T
        }
    }
}