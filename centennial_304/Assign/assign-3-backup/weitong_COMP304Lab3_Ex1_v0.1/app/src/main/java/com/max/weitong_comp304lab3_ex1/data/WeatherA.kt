package com.max.weitong_comp304lab3_ex1.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherA(
    @SerialName("current")
    val current: Weather,

    )