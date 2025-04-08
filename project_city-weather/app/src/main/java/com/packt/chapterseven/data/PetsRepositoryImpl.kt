package com.packt.chapterseven.data
import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import kotlinx.serialization.InternalSerializationApi

class PetsRepositoryImpl(
    private  val weatherApi: WeatherApi,
    private val dispatcher: CoroutineDispatcher
): PetsRepository {

    @OptIn(InternalSerializationApi::class)
    override suspend fun getWeather(latitude: Double, longitude: Double): NetworkResult<WeatherApiResp> {

        return withContext(dispatcher) {
            try {
                val response = weatherApi.getCurrentWeather(latitude, longitude)
                if (response.isSuccessful) {
                    NetworkResult.Success(response.body()!!)
                } else {
                    NetworkResult.Error("response.errorBody().toString()")
                }
            } catch (e: Exception) {
                NetworkResult.Error(e.message ?: "Unknown error")
            }finally {
            }
        }
    }

}