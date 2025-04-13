package com.packt.chapterseven.views

import com.packt.chapterseven.data.City
//import com.packt.chapterseven.data.Weather
import kotlinx.serialization.InternalSerializationApi


data class CitiesUIState @OptIn(InternalSerializationApi::class) constructor(
    val isLoading: Boolean = false,
    val cityList: List<City> = emptyList(),
    var favCityList: MutableList<City> =  mutableListOf(),
    val error: String? = null
)
