package com.example.hw2_listnetwork.data

import com.example.hw2_listnetwork.network.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


interface AppContainer {
    val photosRepository: PhotosRepository
}

/** variables from container will be share across the whole app */
class DefaultAppContainer: AppContainer {

    private val baseUrl = "https://android-kotlin-fun-mars-server.appspot.com/"
//    private val baseUrl = "https://api.punkapi.com/v2/"      // for PunkApi use this line

    private val photoRetrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .build()

    /** if we wont to use kotlinx.serialization.json.Json
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))  */

    private val photoRetrofitService: ApiService by lazy {
        photoRetrofit.create(ApiService::class.java)
    }

    override val photosRepository: PhotosRepository by lazy {
        NetworkPhotosRepository(photoRetrofitService)
    }
}