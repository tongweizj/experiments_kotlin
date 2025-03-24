package com.packt.chapterseven.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(cityEntity: CityEntity)

    @Query("SELECT * FROM City")
    fun getCityList(): Flow<List<CityEntity>>

    @Update
    suspend fun updateCity(cityEntity: CityEntity)


    @Query("SELECT * FROM City where isFavorite = 1")
    fun getFavCity(): Flow<List<CityEntity>>

}