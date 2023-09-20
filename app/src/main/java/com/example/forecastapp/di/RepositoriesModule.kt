package com.example.forecastapp.di

import com.example.forecastapp.domain.database.WeatherDao
import com.example.forecastapp.domain.network.ApiService
import com.example.forecastapp.domain.repositories.ForecastRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {

    @Provides
    fun provideForecastRepository(
        weatherDao: WeatherDao,
        apiService: ApiService
    ): ForecastRepository {
        return ForecastRepository(
            weatherDao,
            apiService,
            Dispatchers.IO
        )
    }
}