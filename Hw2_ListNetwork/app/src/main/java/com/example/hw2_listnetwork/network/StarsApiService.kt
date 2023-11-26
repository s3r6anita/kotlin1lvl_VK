package com.example.hw2_listnetwork.network

import com.example.hw2_listnetwork.model.StarsImage
import retrofit2.http.GET

interface StarsApiService {
    /** returns a [List] of [StarsImage] and this method can be called from a Coroutine */
    @GET("images")
    suspend fun getImages(): List<StarsImage>
}