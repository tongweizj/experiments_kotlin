package com.max.weitong_comp304lab3_ex1.di
import com.max.weitong_comp304lab3_ex1.data.WeatherApi
import com.max.weitong_comp304lab3_ex1.data.CityRepository
import com.max.weitong_comp304lab3_ex1.data.CityRepositoryImpl
import com.max.weitong_comp304lab3_ex1.data.WeatherRepository
import com.max.weitong_comp304lab3_ex1.viewModel.CityViewModel
import com.max.weitong_comp304lab3_ex1.viewModel.WeatherViewModel
import com.max.weitong_comp304lab3_ex1.data.WeatherRepositoryImpl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// 依赖注入模块
val appModule = module {
    single<WeatherRepository> { WeatherRepositoryImpl(get(), get()) }
    single<CoroutineDispatcher> { Dispatchers.IO }
    single { WeatherViewModel(get()) }
    single {
        Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single {
        get<Retrofit>().create(WeatherApi::class.java) }
    // ViewModel（自动注入 Repository）
    single<CityRepository> { CityRepositoryImpl() }
    single { CityViewModel(get()) }
}
