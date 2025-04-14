package com.max.weitong_comp304lab3.data

import kotlinx.coroutines.flow.Flow

interface CityRepository {
    fun getCity(): List<City>
    suspend fun initCityDB()
    //fun getWeather(latitude:Double,longitude:Double)
    suspend fun getAllCity():  Flow<List<City>>
    suspend fun insertCity(city: City)
    suspend fun updateCity(city: City)
}