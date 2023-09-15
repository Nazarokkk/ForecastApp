package com.example.forecastapp.domain.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.forecastapp.data.database.WeatherDataEntity

@Database(
    entities = [
        WeatherDataEntity::class
    ], version = 1
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao

    companion object {
        fun getInstance(applicationContext: Context): AppDatabase {
            return Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "forecast-database"
            ).build()
        }
    }
}