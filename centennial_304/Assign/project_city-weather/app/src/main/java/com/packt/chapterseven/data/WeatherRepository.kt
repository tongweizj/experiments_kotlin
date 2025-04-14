package com.packt.chapterseven.data

interface WeatherRepository {
    suspend fun getWeather(): NetworkResult<WeatherApiResp>
}