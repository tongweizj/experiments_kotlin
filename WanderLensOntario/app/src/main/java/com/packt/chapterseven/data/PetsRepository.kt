package com.packt.chapterseven.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope


interface PetsRepository {

//    suspend fun getPets(): NetworkResult<List<Cat>>
    suspend fun getWeather(latitude: Double, longitude: Double): NetworkResult<WeatherApiResp>


}