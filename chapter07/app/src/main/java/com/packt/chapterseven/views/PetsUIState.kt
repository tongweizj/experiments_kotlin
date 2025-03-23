package com.packt.chapterseven.views

import com.packt.chapterseven.data.Cat
import com.packt.chapterseven.data.City


data class PetsUIState(
    val isLoading: Boolean = false,
    val pets: List<Cat> = emptyList(),
    val cityList: List<City> = emptyList(),
    val error: String? = null
)
