package com.max.weitong_comp304lab3_ex1.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Weather(
    @SerialName("time")
    val time: String = "",
    @SerialName("interval")
    val interval: String="30",
    @SerialName("temperature_2m")
    val temperature_2m: Double=0.0,
    @SerialName("wind_speed_10m")
    val wind_speed_10m: Double=0.0,
    )