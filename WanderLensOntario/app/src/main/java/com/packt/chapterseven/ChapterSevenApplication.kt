package com.packt.chapterseven

import android.app.Application
import android.util.Log
import com.google.android.gms.maps.MapsInitializer
import com.packt.chapterseven.di.appModules
import org.koin.core.context.startKoin
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.koin.workManagerFactory


class ChapterSevenApplication: Application() {



    override fun onCreate() {
        super.onCreate()
        MapsInitializer.initialize(this, MapsInitializer.Renderer.LATEST) {
            Log.d("Maps", "Renderer: ${it.name}")
        }
        startKoin {
            androidContext(this@ChapterSevenApplication)  // 关键注入点
            workManagerFactory()
            modules(appModules)
        }
    }
}