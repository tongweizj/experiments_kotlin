package com.packt.chapterseven.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.packt.chapterseven.data.asResult
import com.packt.chapterseven.data.City
import com.packt.chapterseven.data.CityRepository
import com.packt.chapterseven.data.NetworkResult
import com.packt.chapterseven.data.PetsRepository
import com.packt.chapterseven.data.Weather
import com.packt.chapterseven.data.WeatherApi
import com.packt.chapterseven.views.PetsUIState
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.InternalSerializationApi


class PetsViewModel(
    private val petsRepository: PetsRepository,
    private val cityRepository: CityRepository,
    private val weatherApi: WeatherApi,
): ViewModel() {
    @OptIn(InternalSerializationApi::class)
    val petsUIState = MutableStateFlow(PetsUIState())

    init {
        getPets()
    }

    @OptIn(InternalSerializationApi::class)
    private fun getPets() {
        petsUIState.value = PetsUIState(isLoading = true)
        Log.d("maxLog", "PetsViewModel: petsUIState: ${petsUIState.toString()}")
        val item:City =  City(1, "Toronto", 43.86103683452462, -79.23287065483638 )
        viewModelScope.launch {
            when (val result = petsRepository.getWeather(item.latitude,item.longitude)) {
                is NetworkResult.Success -> {
                    // city
                    val cityResult = cityRepository.getCity()
                    cityResult.forEach { city ->
                        val response = weatherApi.getCurrentWeather(city.latitude, city.longitude)
                        if (response.isSuccessful) {
                            val weather = response.body()
                            // 处理成功响应，例如更新 UI
                            weather?.let {
                                // 使用 weather 对象更新 UI 或执行其他操作
                                val weatherMapData: HashMap<Int, Weather> = petsUIState.value.weatherMap
                                weatherMapData[city.id] = it.current
                                petsUIState.value.weatherMap =  weatherMapData
                            }
                        } else {
                            val errorCode = response.code()
                            val errorBody = response.errorBody()?.string()
                            // 处理错误响应，例如显示错误消息
                            println("错误代码：$errorCode，错误信息：$errorBody")
                        }
                    }
                    petsUIState.update {
                        it.copy(isLoading = false,cityList= cityResult, weather = result.data.current )
                    }
                }
                is NetworkResult.Error -> {
                    petsUIState.update {
                        it.copy(isLoading = false, error = result.error)
                    }
                }
            }

        }
    }
    @OptIn(InternalSerializationApi::class)
    fun getWeather(latitude: Double, longitude: Double) {
        Log.d("maxLog", "getWeather:${latitude}")
        Log.d("maxLog", "viewModelScope out:${latitude}")
        viewModelScope.launch {
            Log.d("CoroutineLog", "viewModelScope协程启动，线程：${Thread.currentThread().name}")
            Log.d("maxLog", "viewModelScope:${latitude}")
            petsUIState.update { it.copy(isLoading = true ) }
            when (val result = petsRepository.getWeather(latitude,longitude)) {
                is NetworkResult.Success -> {
                    Log.d("maxLog", "PetsViewModel1: weather: ${result.data.current .toString()}")
                    petsUIState.update {
                        it.copy(isLoading = false, weather = result.data.current )
                    }
                    if (petsUIState.value.isLoading==false) {
                        cancel() // 手动取消协程
                    }
                }
                is NetworkResult.Error -> {
                    petsUIState.update {
                        it.copy(isLoading = false, error = result.error)
                    }
                 }
            }

        }
    }

    @OptIn(InternalSerializationApi::class)
    fun toggleFavorite(city: City){
        viewModelScope.launch {
            //TODO： 同步数据库
            // cityRepository.toggleFavorite(city)
            // 同步state

            val cityToUpdate = petsUIState.value.cityList.find { it == city}

            if (cityToUpdate != null) {
                cityToUpdate.isFavorite = !cityToUpdate.isFavorite
            }
            if(petsUIState.value.favCityList.contains(city)){
             petsUIState.value.favCityList.remove(city)
            }else{
                petsUIState.value.favCityList.add(city)
            }

        }
    }


    // get fav city list from DB

    @OptIn(InternalSerializationApi::class)
    private suspend fun getCityList() {
        petsUIState.value = PetsUIState(isLoading = true)
        viewModelScope.launch {
            cityRepository.getCityList().asResult().collect { result ->
                when (result ) {
                    is NetworkResult.Success -> {
                        petsUIState.update {
                            it.copy(isLoading = false, cityList = result.data)
                        }
                    }
                    is NetworkResult.Error -> {
                        petsUIState.update {
                            it.copy(isLoading = false, error = result.error)
                        }
                    }
                }
            }
        }
        if(petsUIState.value.cityList.isEmpty()){
            //TODO add city to DB
            // userDao.insert(User(1, "Alice"))
            cityRepository.populateDatabase()
        }

    }
    @OptIn(InternalSerializationApi::class)
    fun getFavCityList(){
        viewModelScope.launch {
            petsUIState.value.favCityList = cityRepository.getFavCityList() as MutableList<City>
        }

    }

}