package com.example.hw2_listnetwork.data

import com.example.hw2_listnetwork.model.StarsImage
import com.example.hw2_listnetwork.network.StarsApiService

interface StarsImagesRepository {
    suspend fun getStarsImages(): List<StarsImage>
}

class NetworkStarsImagesRepository(
    private val starsApiService: StarsApiService
) : StarsImagesRepository {

    override suspend fun getStarsImages(): List<StarsImage> = starsApiService.getImages()
}
