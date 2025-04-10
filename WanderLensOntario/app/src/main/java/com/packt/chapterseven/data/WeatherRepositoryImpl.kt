package com.packt.chapterseven.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.InternalSerializationApi


//class WeatherRepositoryImpl(
//    private  val weatherApi: WeatherApi,
//    private val dispatcher: CoroutineDispatcher
//): WeatherRepository {
//    @OptIn(InternalSerializationApi::class)
//    override suspend fun getWeather(): NetworkResult<WeatherApiResp> {
//        return withContext(dispatcher) {
//            try {
//                val item:City =  City(1, "Toronto", 43.86103683452462, -79.23287065483638 )
//                val response = weatherApi.getCurrentWeather(item.latitude,item.longitude)
//                if (response.isSuccessful) {
//                    NetworkResult.Success(response.body()!!)
//                } else {
//                    NetworkResult.Error(response.errorBody().toString())
//                }
//            } catch (e: Exception) {
//                NetworkResult.Error(e.message ?: "Unknown error")
//            }
//        }
//    }
//}