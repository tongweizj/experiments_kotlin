package com.packt.chapterseven.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CityEntity::class],
    version = 1,
)
//@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun cityDao(): CityDao
}

//// Database class
//@Database(entities = [User::class], version = 1)
//@TypeConverters(Converters::class)
//abstract class AppDatabase : RoomDatabase() {
//    abstract fun userDao(): UserDao
//}