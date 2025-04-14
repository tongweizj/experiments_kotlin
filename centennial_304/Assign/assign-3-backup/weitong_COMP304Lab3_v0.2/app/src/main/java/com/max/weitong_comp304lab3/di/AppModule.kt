package com.max.weitong_comp304lab3.di

import androidx.room.Room
import com.max.weitong_comp304lab3.data.CityDatabase
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.max.weitong_comp304lab3.data.CityRepository
import com.max.weitong_comp304lab3.data.CityRepositoryImpl
import com.max.weitong_comp304lab3.viewModel.CityListViewModel
import com.max.weitong_comp304lab3.data.WeatherApi
import org.koin.android.ext.koin.androidContext

// 依赖注入模块
val appModule = module {
    single<CityRepository> { CityRepositoryImpl(get()) }
    single { CityListViewModel(get(),get()) }
    single {
        Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single {
        get<Retrofit>().create(WeatherApi::class.java) }

    single {
        Room.databaseBuilder(
            androidContext(),
            CityDatabase::class.java,
            "cat-database"
        ).build()
    }
    single { get<CityDatabase>().cityDao() }
}