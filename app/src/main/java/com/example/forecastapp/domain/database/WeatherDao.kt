package com.example.forecastapp.domain.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.forecastapp.data.database.WeatherDataEntity

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(member: WeatherDataEntity)

    @Query("SELECT * FROM weather_data WHERE locationId LIKE :cityId")
    suspend fun getWeather(
        cityId: String
    ): WeatherDataEntity?
}