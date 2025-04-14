package com.packt.chapterseven.data

data class Weather(
    val time: String = "",
    val interval: String="30",
    val temperature_2m: Double=0.0,
    val apparent_Temperature : Double=0.0,
    val wind_speed_10m: Double=0.0,
)