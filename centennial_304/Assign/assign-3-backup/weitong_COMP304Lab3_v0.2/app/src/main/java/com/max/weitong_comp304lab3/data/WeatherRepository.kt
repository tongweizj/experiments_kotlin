package com.max.weitong_comp304lab3.data

import retrofit2.Response

interface WeatherRepository {

    fun buildCityListWeather(): HashMap<Int, Weather>
    //suspend fun fetchRemoteWeather(latitude:Double,longitude:Double)
//    suspend fun getWeather(): Response<Weather>
}