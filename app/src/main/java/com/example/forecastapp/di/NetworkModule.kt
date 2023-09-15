package com.example.forecastapp.di

import com.example.forecastapp.domain.network.ApiClient
import com.example.forecastapp.domain.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideApiClient(): Retrofit {
        return ApiClient().getInstance()
    }

    @Provides
    fun provideApiService(client: Retrofit): ApiService {
        return client.create(ApiService::class.java)
    }
}