package com.max.weitong_comp304lab3_ex1.view

import com.max.weitong_comp304lab3_ex1.data.City


data class CityUIState(
    val isLoading: Boolean = false,
    val city: List<City> = emptyList(),
    val error: String? = null
)
