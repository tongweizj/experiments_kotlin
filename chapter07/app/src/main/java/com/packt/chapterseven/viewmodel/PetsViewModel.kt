package com.packt.chapterseven.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.packt.chapterseven.data.City
import com.packt.chapterseven.data.CityRepository
import com.packt.chapterseven.data.NetworkResult
import com.packt.chapterseven.data.PetsRepository
import com.packt.chapterseven.views.PetsUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Response
import kotlin.collections.set

class PetsViewModel(
    private val petsRepository: PetsRepository,
    private val cityRepository: CityRepository,
): ViewModel() {
    val petsUIState = MutableStateFlow(PetsUIState())

    init {
        getPets()
    }

    private fun getPets() {
        petsUIState.value = PetsUIState(isLoading = true)
        viewModelScope.launch {
            when (val result = petsRepository.getPets()) {
                is NetworkResult.Success -> {
                    // city
                    val cityResult = cityRepository.getCity()
                    petsUIState.update {
                        it.copy(isLoading = false, cityList = cityResult)
                    }

                    petsUIState.update {
                        it.copy(isLoading = false, pets = result.data)
                    }
                }
                is NetworkResult.Error -> {
                    petsUIState.update {
                        it.copy(isLoading = false, error = result.error)
                    }
                }
            }

//            result.forEach { city ->
//                val response = weatherApi.getCurrentWeather(city.latitude, city.longitude)
//                if (response.isSuccessful) {
//                    val weather = response.body()
//                    // 处理成功响应，例如更新 UI
//                    weather?.let {
//                        val item: Weather = it.current
//                        petsUIState.update {
//                            // 使用 weather 对象更新 UI 或执行其他操作
//                            val x = petsUIState.value.cityWeatherMap
//                            x[city.id] = item
//                            it.copy(isLoading = false, cityWeatherMap = x)
//                        }
//
//                    }
//                } else {
//                    val errorCode = response.code()
//                    val errorBody = response.errorBody()?.string()
//                    // 处理错误响应，例如显示错误消息
//                    println("错误代码：$errorCode，错误信息：$errorBody")
//                }
//            }
        }
    }


}