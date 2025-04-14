package com.max.weitong_comp304lab3_ex1.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.max.weitong_comp304lab3_ex1.data.City
import com.max.weitong_comp304lab3_ex1.data.NetworkResult
import com.max.weitong_comp304lab3_ex1.data.Weather
import com.max.weitong_comp304lab3_ex1.data.WeatherRepository
import com.max.weitong_comp304lab3_ex1.view.WeatherUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val weatherRepository: WeatherRepository
): ViewModel() {
    val weatherUIState = MutableStateFlow(WeatherUIState())
    // 创建一个 MutableStateFlow 实例 petsUIState，初始值为 PetsUIState()。
    // MutableStateFlow 用于管理 UI 的状态，当状态发生变化时，会自动通知观察者。
    init {
        //getWeather()
    }

    fun getWeather() {
        Log.d("maxLog", "getWeather")
        weatherUIState.value = WeatherUIState(isLoading = true)
        viewModelScope.launch {
            when (val result = weatherRepository.getWeather(43.87, -79.24)) {
                is NetworkResult.Success -> {
                    Log.d("maxLog", "NetworkResult Success：${result}")
                    weatherUIState.update {
                        it.copy(isLoading = false, currentWeather = result.data.current)
                    }
                }
                is NetworkResult.Error -> {
                    Log.d("maxLog", "NetworkResult.Error")
                    Log.d("maxLog", "Error:${result.error}")
                    weatherUIState.update {
                        it.copy(isLoading = false, error = result.error)
                    }
                }
            }
        }
    }

     fun updateWeatherByCity(city:City) {
        Log.d("maxLog", "updateWeatherByCity/${city.id}")
        Log.d("maxLog", "updateWeatherByCity: $city") // log the city object
//        weatherUIState.value = WeatherUIState(isLoading = true)
         weatherUIState.value = WeatherUIState(isLoading = true, currentWeather =  Weather(), error = null)
        viewModelScope.launch {
            Log.d("maxLog", "viewModelScope.launch")
            when (val result = weatherRepository.getWeather(city.latitude, city.longitude)) {
                is NetworkResult.Success -> {
                    Log.d("maxLog", "NetworkResult.Success")
                    Log.d("maxLog", "NetworkResult.Success, result: $result")
                    weatherUIState.update {
                        it.copy(isLoading = false, currentWeather = result.data.current)
                    }
                    Log.d("maxLog", "updated:${weatherUIState.value.currentWeather.toString()}")
                    //weatherUIState.value.currentWeather
                }
                is NetworkResult.Error -> {
                    Log.d("maxLog", "NetworkResult.Error")
                    Log.d("maxLog", "Error:${result.error}")
                    weatherUIState.update {
                        it.copy(isLoading = false, error = result.error)
                    }
                }

            }
        }
        Log.d("maxLog", "updateWeatherByCity exit")
    }
}
