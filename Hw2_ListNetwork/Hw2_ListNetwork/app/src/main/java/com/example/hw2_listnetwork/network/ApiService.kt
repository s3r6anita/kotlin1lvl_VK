package com.example.hw2_listnetwork.network

import com.example.hw2_listnetwork.model.Photo
import retrofit2.http.GET

interface ApiService {
//    @GET("beers")     //for PunkApi use this line
    @GET("photos")
    suspend fun getPhotos(): List<Photo>
}
