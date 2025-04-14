package com.max.weitong_comp304lab3_ex1.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import android.util.Log
class WeatherRepositoryImpl(
    private  val weatherAPI: WeatherApi,
    private val dispatcher: CoroutineDispatcher
): WeatherRepository {
    // 构造函数接受两个参数：
    // catsAPI: CatsAPI: 一个 CatsAPI 接口的实例，用于执行网络请求。
    // dispatcher: CoroutineDispatcher: 一个协程调度器，
    // 用于指定网络请求在哪个线程上执行。


    override suspend fun getWeather(latitude:Double,longitude:Double): NetworkResult<WeatherA> {
        return withContext(dispatcher) {
            try {
                Log.d("maxLog", "start")
                val response = weatherAPI.getCurrentWeather(latitude,longitude)


                if (response.isSuccessful) {
                    Log.d("maxLog", "getWeather success: ${response.body()}")
                    NetworkResult.Success(response.body()!!)
                    // NetworkResult.Success(response.body()!!):
                    // 如果响应成功，则创建一个 NetworkResult.Success 对象，
                    // 其中包含响应体中的猫咪数据。
                    // response.body()!! 获取响应体，
                    // 并使用 !! 操作符强制解包，如果响应体为空，则抛出 NullPointerException。
                } else {
                    Log.d("maxLog", "err:${response.errorBody().toString()}")
                    NetworkResult.Error(response.errorBody().toString())
                }
            } catch (e: Exception) {
                NetworkResult.Error(e.message ?: "Unknown error")
            }
        }
    }
}