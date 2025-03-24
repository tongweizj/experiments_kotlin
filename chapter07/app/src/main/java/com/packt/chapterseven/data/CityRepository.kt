package com.packt.chapterseven.data

import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.InternalSerializationApi

interface CityRepository {
    @OptIn(InternalSerializationApi::class)
    fun getCity(): List<City>
    @OptIn(InternalSerializationApi::class)
    suspend fun toggleFavorite(city: City)

    @OptIn(InternalSerializationApi::class)
     fun getFavCityList():List<City>

     // From DATABASE
    @OptIn(InternalSerializationApi::class)
    suspend fun getCityList(): Flow<List<City>>
    @OptIn(InternalSerializationApi::class)
    suspend fun updateCity(city: City)
    @OptIn(InternalSerializationApi::class)
    suspend fun getFavoritecity(): Flow<List<City>>
    suspend fun populateDatabase()
}