package com.example.forecastapp.di

import android.content.Context
import com.example.forecastapp.domain.database.AppDatabase
import com.example.forecastapp.domain.database.WeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideAppDatabase(@ApplicationContext applicationContext: Context): AppDatabase {
        return AppDatabase.getInstance(applicationContext)
    }

    @Provides
    fun provideCompletedChallengeDao(
        database: AppDatabase
    ): WeatherDao {
        return database.weatherDao()
    }
}