package com.packt.chapterseven.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.packt.chapterseven.data.asResult
import com.packt.chapterseven.data.City
import com.packt.chapterseven.data.CityRepository
import com.packt.chapterseven.data.NetworkResult
import com.packt.chapterseven.views.CitiesUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.InternalSerializationApi


class CitiesViewModel(

    private val cityRepository: CityRepository,

): ViewModel() {
    @OptIn(InternalSerializationApi::class)
    val citiesUIState = MutableStateFlow(CitiesUIState())
    @OptIn(InternalSerializationApi::class)
    private val _favoriteCities = MutableStateFlow<List<City>>(emptyList())
    @OptIn(InternalSerializationApi::class)
    val favoriteCities: StateFlow<List<City>> get() = _favoriteCities

    init {
        getCityList()
    }
//    @OptIn(InternalSerializationApi::class)
//     fun getAllCityWeather(cityList: List<City>){
//        //val cityList = citiesUIState.value.cityList
//        viewModelScope.launch {
//            Log.d("maxLog", "getAllCityWeather:${cityList.toString()}")
//
////            cityList.forEach { city ->
////            val response = weatherApi.getCurrentWeather(city.latitude, city.longitude)
////            if (response.isSuccessful) {
////                val weather = response.body()
////                // 处理成功响应，例如更新 UI
////                weather?.let {
////                    // 使用 weather 对象更新 UI 或执行其他操作
////                    val weatherMapData: HashMap<Int, Weather> = citiesUIState.value.weatherMap
////                    weatherMapData[city.id] = it.current
////                    citiesUIState.value.weatherMap =  weatherMapData
////                }
////            } else {
////                val errorCode = response.code()
////                val errorBody = response.errorBody()?.string()
////                // 处理错误响应，例如显示错误消息
////                println("错误代码：$errorCode，错误信息：$errorBody")
////            }
//        }
//    }

//    @OptIn(InternalSerializationApi::class)
//    private fun getPets() {
//        citiesUIState.value = CitiesUIState(isLoading = true)
//        val item:City =  City(1, "Toronto", 43.86103683452462, -79.23287065483638 )
//        viewModelScope.launch {
//            when (val result = citiesRepository.getWeather(item.latitude,item.longitude)) {
//                is NetworkResult.Success -> {
//                    // city
//                    val cityResult = cityRepository.getCity()
//                    cityResult.forEach { city ->
//                        val response = weatherApi.getCurrentWeather(city.latitude, city.longitude)
//                        if (response.isSuccessful) {
//                            val weather = response.body()
//                            // 处理成功响应，例如更新 UI
//                            weather?.let {
//                                // 使用 weather 对象更新 UI 或执行其他操作
//                                val weatherMapData: HashMap<Int, Weather> = citiesUIState.value.weatherMap
//                                weatherMapData[city.id] = it.current
//                                citiesUIState.value.weatherMap =  weatherMapData
//                            }
//                        } else {
//                            val errorCode = response.code()
//                            val errorBody = response.errorBody()?.string()
//                            // 处理错误响应，例如显示错误消息
//                            println("错误代码：$errorCode，错误信息：$errorBody")
//                        }
//                    }
//                    citiesUIState.update {
//                        it.copy(isLoading = false,cityList= cityResult, weather = result.data.current )
//                    }
//                }
////                is NetworkResult.Error -> {
////                    citiesUIState.update {
////                        it.copy(isLoading = false, error = result.error)
////                    }
////                }
//            }
//
//        }
//    }

    @OptIn(InternalSerializationApi::class)
    fun updatePet(city: City) {
        viewModelScope.launch {
            cityRepository.updateCity(city)
        }
    }

    // get data from DB
    @OptIn(InternalSerializationApi::class)
     fun getCityList() {
        citiesUIState.value = CitiesUIState(isLoading = true)
        //Log.d("maxLog", "getCityList start:${citiesUIState.value.cityList.toString()}")
        viewModelScope.launch {
            cityRepository.getCityList().asResult().collect { result ->
                when (result ) {
                    is NetworkResult.Success -> {
                        citiesUIState.update {
                            it.copy(isLoading = false, cityList = result.data)
                        }
                        //getAllCityWeather(result.data)
                    }
                    is NetworkResult.Error -> {
                        citiesUIState.update {
                            it.copy(isLoading = false, error = result.error)
                        }
                    }
                }
            }

        }
    }
    // get fav city list from DB
    @OptIn(InternalSerializationApi::class)
    fun getFavoriteCities() {
        viewModelScope.launch {
            cityRepository.getFavoriteCities().collect {
                _favoriteCities.value = it
            }
        }
    }
    @OptIn(InternalSerializationApi::class)
    fun toggleFavorite(city: City){
        viewModelScope.launch {
            //TODO： 同步数据库
            // cityRepository.toggleFavorite(city)
            // 同步state
            val cityToUpdate = citiesUIState.value.cityList.find { it == city}

            if (cityToUpdate != null) {
                cityToUpdate.isFavorite = !cityToUpdate.isFavorite
            }
            if(citiesUIState.value.favCityList.contains(city)){
                citiesUIState.value.favCityList.remove(city)
            }else{
                citiesUIState.value.favCityList.add(city)
            }

        }
    }

}