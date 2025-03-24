package com.packt.chapterseven.di

import androidx.room.Room
import com.packt.chapterseven.data.AppDatabase
import com.packt.chapterseven.data.PetsRepository
import com.packt.chapterseven.data.PetsRepositoryImpl
import com.packt.chapterseven.data.CityRepository
import com.packt.chapterseven.data.CityRepositoryImpl
import com.packt.chapterseven.data.WeatherApi
import com.packt.chapterseven.viewmodel.PetsViewModel
import com.packt.chapterseven.workers.PetsSyncWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.dsl.worker
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit



private val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
}
val okHttpClient = OkHttpClient.Builder()
    .connectTimeout(3, TimeUnit.SECONDS)
    .readTimeout(3, TimeUnit.SECONDS)
    .writeTimeout(3, TimeUnit.SECONDS)
    .build()
val appModules = module {
    single<PetsRepository> { PetsRepositoryImpl(get(),get()) }
    single<CityRepository> { CityRepositoryImpl(get(),get()) }

    single { Dispatchers.IO }
    single { PetsViewModel(get(),get(),get()) }

    single {
        Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single {
        get<Retrofit>().create(WeatherApi::class.java) }

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "app-database"
        ).build()
    }
    single { get<AppDatabase>().cityDao() }

    worker { PetsSyncWorker(get(), get(), get()) }

}