package com.example.hw2_listnetwork.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.hw2_listnetwork.data.StarsImagesRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

/** UI state for the Home screen */
sealed interface StarsUiState {
    data class Success(val photos: String) : StarsUiState
    object Error : StarsUiState
    object Loading : StarsUiState
}

class StarsViewModel(private val starsImagesRepository: StarsImagesRepository) : ViewModel() {

    /** status of request */
    var starsUiState: StarsUiState by mutableStateOf(StarsUiState.Loading)
        private set

    init {
        getStarsPhotos()
    }

    fun getStarsPhotos() {
        viewModelScope.launch {
            starsUiState = StarsUiState.Loading
            starsUiState = try {
                val listResult = starsPhotosRepository.getMarsPhotos()
                srarsUiState.Success(
                    "Success: ${listResult.size} Stars photos retrieved"
                )
            } catch (e: IOException) {
                starsUiState.Error
            } catch (e: HttpException) {
                starsUiState.Error
            }
        }
    }

    /**
     * Factory for [MarsViewModel] that takes [MarsPhotosRepository] as a dependency
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MarsPhotosApplication)
                val marsPhotosRepository = application.container.marsPhotosRepository
                MarsViewModel(marsPhotosRepository = marsPhotosRepository)
            }
        }
    }
}