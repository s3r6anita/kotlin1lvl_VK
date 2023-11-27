package com.example.hw2_listnetwork.model

/** with kotlinx.serialization use
import kotlinx.serialization.SerialName */

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Photo(
    val id: String,
//    @SerializedName(value = "image_url")   //for PunkApi use this line
    @SerializedName(value = "img_src")
    val imgSrc: String
)