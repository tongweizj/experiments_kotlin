package com.max.weitong_comp304lab3.data

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
    fun getAllCity(): Flow<List<CityEntity>>

    @Update
    suspend fun updateCity(cityEntity: CityEntity)

}