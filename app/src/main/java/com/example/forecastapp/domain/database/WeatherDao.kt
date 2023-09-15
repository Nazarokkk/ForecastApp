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
//
//    @Query("SELECT * FROM completed_challenges WHERE member_username LIKE :username AND datetime(completed_at) < datetime(:lastShownChallengeCompletedAt) AND datetime(completed_at) >= datetime(:lastApiChallengeCompletedAt) ORDER BY datetime(completed_at) DESC LIMIT 200")
//    suspend fun getChallengesFromMemberBetweenDates(
//        username: String,
//        lastShownChallengeCompletedAt: String,
//        lastApiChallengeCompletedAt: String
//    ): List<CompletedChallengeEntity>
}