package com.max.weitong_comp304lab3_ex1

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import com.max.weitong_comp304lab3_ex1.di.appModule
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // 启动 Koin
        startKoin {
            androidContext(this@MyApplication) // 传入 Android 上下文
            modules(appModule) // 绑定依赖模块
        }
    }
}
