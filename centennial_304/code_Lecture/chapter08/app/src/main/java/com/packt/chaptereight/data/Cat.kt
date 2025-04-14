package com.packt.chaptereight.data


import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@InternalSerializationApi @Serializable
data class Cat(
    @SerialName("createdAt")
    val createdAt: String = "",
    @SerialName("id")
    val id: String,
    @SerialName("owner")
    val owner: String = "",
    @SerialName("tags")
    val tags: List<String>,
    @SerialName("updatedAt")
    val updatedAt: String = "",
    val isFavorite: Boolean = false
)