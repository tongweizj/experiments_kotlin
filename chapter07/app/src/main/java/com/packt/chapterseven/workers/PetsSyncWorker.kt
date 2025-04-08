package com.packt.chapterseven.workers


import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.packt.chapterseven.data.CityRepository

class PetsSyncWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val  cityRepository: CityRepository
): CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            cityRepository.populateDatabase()
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}