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
            Log.d("CoroutineLog", "协程启动，线程：${Thread.currentThread().name}")
            try {
                val response = weatherApi.getCurrentWeather(latitude, longitude)
                Log.d("CoroutineLog", "网络请求完成，线程：${Thread.currentThread().name}")
                if (response.isSuccessful) {
                    Log.d("CoroutineLog", "获取天气成功，纬度：$latitude，经度：$longitude，响应：${response.body().toString()}")
                    NetworkResult.Success(response.body()!!)
                } else {
                    NetworkResult.Error("response.errorBody().toString()")
                }
            } catch (e: Exception) {
                NetworkResult.Error(e.message ?: "Unknown error")
            }finally {
                Log.d("CoroutineLog", "协程结束，线程：${Thread.currentThread().name}")
            }
        }
    }

}