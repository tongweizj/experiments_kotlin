package com.packt.chapterseven

import android.app.Application
import com.packt.chapterseven.data.AppDatabase
import com.packt.chapterseven.di.appModules
import org.koin.core.context.startKoin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import kotlin.getValue

class ChapterSevenApplication: Application() {


    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ChapterSevenApplication)  // 关键注入点
            modules(appModules)
        }
    }
}