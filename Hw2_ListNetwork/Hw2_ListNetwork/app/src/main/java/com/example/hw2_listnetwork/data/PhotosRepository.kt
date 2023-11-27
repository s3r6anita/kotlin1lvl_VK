package com.example.hw2_listnetwork.data

import com.example.hw2_listnetwork.model.Photo
import com.example.hw2_listnetwork.network.ApiService

interface PhotosRepository {
    suspend fun getPhotosFromRepository(): List<Photo>
}

class NetworkPhotosRepository(private val apiService: ApiService) : PhotosRepository {
    override suspend fun getPhotosFromRepository(): List<Photo> = apiService.getPhotos()
}
