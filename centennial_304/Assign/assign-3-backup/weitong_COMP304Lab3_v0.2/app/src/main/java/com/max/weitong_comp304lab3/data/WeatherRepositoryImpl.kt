package com.max.weitong_comp304lab3.data

import retrofit2.Response
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class WeatherRepositoryImpl(private  val weatherAPI: WeatherApi,): WeatherRepository {
    override fun buildCityListWeather(): HashMap<Int, Weather> {
        val weatherMap = HashMap<Int, Weather>()
        weatherMap[0] = Weather()
        return weatherMap
    }

}