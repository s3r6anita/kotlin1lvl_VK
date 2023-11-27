package com.example.hw2_listnetwork

import android.app.Application
import com.example.hw2_listnetwork.data.AppContainer
import com.example.hw2_listnetwork.data.DefaultAppContainer

class PhotosApplication: Application() {
    /** AppContainer instance used by the rest of classes to obtain dependencies */
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}