package com.example.hw2_listnetwork.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.hw2_listnetwork.PhotosApplication
import com.example.hw2_listnetwork.data.PhotosRepository
import com.example.hw2_listnetwork.model.Photo
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface UiState {
    data class Success(val photos: List<Photo>) : UiState
    data class Error(val e: Exception) : UiState
    object Loading : UiState
}

class PhotosViewModel (private val photosRepository: PhotosRepository) : ViewModel() {

//    var photosList: List<Photo> by mutableStateOf(listOf())

    var uiState: UiState by mutableStateOf(UiState.Loading)
        private set

    init {
        getPhotos()
    }

    fun getPhotos() {
        viewModelScope.launch {
            uiState = UiState.Loading
            uiState = try {
                UiState.Success(photosRepository.getPhotosFromRepository())
            } catch (e: IOException) {
                UiState.Error(e)
            } catch (e: HttpException) {
                UiState.Error(e)
            }
        }
    }

    /** Factory for [PhotosViewModel] that takes [PhotosRepository] as a dependency */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as PhotosApplication)
                val photosRepository = application.container.photosRepository
                PhotosViewModel(photosRepository = photosRepository)
            }
        }
    }
}
