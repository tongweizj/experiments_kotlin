package com.max.weitong_comp304lab3_ex1.view

import com.max.weitong_comp304lab3_ex1.data.Weather

data class WeatherUIState (
    val isLoading: Boolean = false,
    val currentWeather: Weather? = null,
    val error: String? = null
)
