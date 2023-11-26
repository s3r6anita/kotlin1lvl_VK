package com.example.hw2_listnetwork.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class  StarsImage(
    val id: String,
    @SerialName(value = "img_src")
    val imgSrc: String
)