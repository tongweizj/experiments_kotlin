package com.max.weitong_comp304lab3_ex1.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("v1/forecast")
    suspend fun getCurrentWeather(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current") current: String = "temperature_2m,wind_speed_10m"
    ): Response<WeatherA>
}
// https://api.open-meteo.com/v1/forecast/