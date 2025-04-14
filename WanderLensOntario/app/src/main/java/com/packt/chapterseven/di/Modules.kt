package com.packt.chapterseven.di

import androidx.room.Room
import com.packt.chapterseven.data.AppDatabase
import com.packt.chapterseven.data.CityRepository
import com.packt.chapterseven.data.CityRepositoryImpl
import com.packt.chapterseven.viewmodel.CitiesViewModel
import com.packt.chapterseven.views.DirectionsService
import com.packt.chapterseven.workers.PetsSyncWorker
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.dsl.worker
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val appModules = module {

    single<CityRepository> { CityRepositoryImpl(get(),get()) }

    single { Dispatchers.IO }
    single { CitiesViewModel(get()) }
    single {
         OkHttpClient.Builder()
             .connectTimeout(3, TimeUnit.SECONDS)
            .readTimeout(3, TimeUnit.SECONDS)
            .writeTimeout(3, TimeUnit.SECONDS)
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    // 单例API服务
    single<DirectionsService> {
        get<Retrofit>().create(DirectionsService::class.java)
    }

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