package com.packt.chapterseven.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope


interface CitiesRepository {
    suspend fun getWeather(latitude: Double, longitude: Double): NetworkResult<WeatherApiResp>
}