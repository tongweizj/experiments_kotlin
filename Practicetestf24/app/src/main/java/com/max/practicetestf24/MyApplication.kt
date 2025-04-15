package com.max.practicetestf24

import android.app.Application
import androidx.room.Room
import com.max.practicetestf24.data.AppDatabase
import com.max.practicetestf24.repository.TaskRepository


class MyApplication: Application() {
    val database: AppDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "tasksdb"
        ).build()
    }
    val repository: TaskRepository by lazy { // Replace TaskRepository with your actual repository class
        TaskRepository(database.taskDao()) // Initialize your repository here
    }
    override fun onCreate() {
        super.onCreate()
    }
}