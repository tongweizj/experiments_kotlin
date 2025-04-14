package com.max.weitong_comp304lab3.data

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [CityEntity::class],
    version = 1,
)
//@TypeConverters(Converters::class)
abstract class CityDatabase: RoomDatabase() {
    abstract fun cityDao(): CityDao
}

//// Database class
//@Database(entities = [User::class], version = 1)
//@TypeConverters(Converters::class)
//abstract class AppDatabase : RoomDatabase() {
//    abstract fun userDao(): UserDao
//}