package com.max.weitong_comp304lab3.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response
import com.max.weitong_comp304lab3.data.City
import com.max.weitong_comp304lab3.data.CityRepository
import com.max.weitong_comp304lab3.data.Weather
import com.max.weitong_comp304lab3.data.WeatherApi
import com.max.weitong_comp304lab3.data.WeatherApiResp
import kotlinx.coroutines.flow.MutableStateFlow

class CityListViewModel(private val weatherApi: WeatherApi,private val cityRepository: CityRepository) : ViewModel(){
    val cityList = cityRepository.getCity()
    val cityWeatherMap  = MutableStateFlow(HashMap<Int, Weather>())

    suspend fun getAllCityWeather(){
        cityList.forEach { city ->
            processWeather(city)
        }
    }
    fun getCity(id: String): City {
        var item:City? = cityList.find { it.id == id.toInt() };
        if(item ==null){
            item = City(1, "Toronto", 43.86103683452462, -79.23287065483638 )
        }
        return item
    }
    // 临时方法，用于模拟网络请求

    suspend fun getWeather(city: City): Response<WeatherApiResp> {
        //val item:City = City(1, "Toronto", 43.86103683452462, -79.23287065483638 )
        return weatherApi.getCurrentWeather(city.latitude, city.longitude)
    }

    suspend fun processWeather(city: City) {
        viewModelScope.launch {
            val response = getWeather(city)
            if (response.isSuccessful) {
                val weather = response.body()
                // 处理成功响应，例如更新 UI
                weather?.let {
                    // 使用 weather 对象更新 UI 或执行其他操作
                    val x = cityWeatherMap.value
                    x[city.id] = it.current
                    cityWeatherMap.value = x
                }
            } else {
                val errorCode = response.code()
                val errorBody = response.errorBody()?.string()
                // 处理错误响应，例如显示错误消息
                println("错误代码：$errorCode，错误信息：$errorBody")
            }
        }
    }

    fun addCityToDB(city: City) {
        viewModelScope.launch {
            cityRepository.insertCity(city)
            //fetchUsers() // Refresh the user list after insertion
        }
    }
}