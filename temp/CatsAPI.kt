package com.packt.chapterseven.data

import kotlinx.serialization.InternalSerializationApi
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CatsAPI {
    @OptIn(InternalSerializationApi::class)
    @GET("cats")
    suspend fun fetchCats(
        @Query("tag") tag: String,
    ): Response<List<Cat>>
    @GET("v1/forecast")
    suspend fun getCurrentWeather(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current") current: String = "temperature_2m,wind_speed_10m"
    ): Response<WeatherApi>
}