package com.baism.rickandmortyzad3.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baism.rickandmortyzad3.data.CharacterInfo
import com.baism.rickandmortyzad3.data.RickAndMortyApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class UiState(
    val isLoading: Boolean = false,
    val characters: List<CharacterInfo> = emptyList(),
    val selectedCharacter: CharacterInfo? = null,
    val errorMessage: String? = null
)

class MainViewModel(
    private val api: RickAndMortyApi = RickAndMortyApi()
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun loadAllCharacters() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true, errorMessage = null, selectedCharacter = null)
            }
            try {
                val characters = withContext(Dispatchers.IO) {
                    api.fetchAllCharacters()
                }
                _uiState.update {
                    it.copy(isLoading = false, characters = characters, errorMessage = null)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Nie udało się pobrać postaci"
                    )
                }
            }
        }
    }

    fun loadCharacterById(idText: String) {
        val id = idText.trim().toIntOrNull()
        if (id == null || id <= 0) {
            _uiState.update {
                it.copy(errorMessage = "Podaj poprawne ID postaci (liczba całkowita > 0)")
            }
            return
        }

        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true, errorMessage = null, selectedCharacter = null)
            }
            try {
                val character = withContext(Dispatchers.IO) {
                    api.fetchCharacterById(id)
                }
                _uiState.update {
                    it.copy(isLoading = false, selectedCharacter = character, errorMessage = null)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Nie udało się pobrać postaci po ID"
                    )
                }
            }
        }
    }
}
