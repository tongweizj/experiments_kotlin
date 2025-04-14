package com.max.weitong_comp304lab3_ex1.data

interface WeatherRepository {
    suspend fun getWeather(latitude:Double,longitude:Double): NetworkResult<WeatherA>
    //suspend fun fetchRemoteWeather(latitude:Double,longitude:Double)
}
//
//data class WeatherResponse(
//    val latitude: Double,
//    val longitude: Double,
//    val generationtime_ms: Double,
//    val utc_offset_seconds: Int,
//    val timezone: String,
//    val timezone_abbreviation: String,
//    val elevation: Double,
//    val current_weather: CurrentWeather
//)
//
//data class CurrentWeather(
//    val temperature: Double,
//    val windspeed: Double,
//    val winddirection: Int,
//    val weathercode: Int,
//    val time: String
//)
