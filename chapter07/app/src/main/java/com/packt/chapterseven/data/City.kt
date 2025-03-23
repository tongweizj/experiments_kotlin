package com.packt.chapterseven.data

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

@kotlinx.serialization.InternalSerializationApi  @Serializable
data class City (
    val id: Int,
    val name: String,
    val latitude:Double,
    val longitude:Double,
)