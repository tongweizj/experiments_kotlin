package com.max.weitong_comp304lab3.data
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "City")
data class CityEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val latitude: Double,
    val longitude: Double,
)


