package com.example.forecastapp.domain.network

import com.example.forecastapp.data.network.WeatherDataResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ApiService {
    @GET("weather")
    suspend fun findCityWeatherData(
        @Query("id") cityId: String,
        @Query("units") units: String = "metric",
        @Query("appid") appid: String = "8eb3d8e9839a8454bad9fe972a113b34"
    ): Response<WeatherDataResponse>

    @GET("weather")
    suspend fun findLocationWeatherData(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("units") units: String = "metric",
        @Query("appid") appid: String = "8eb3d8e9839a8454bad9fe972a113b34"
    ): Response<WeatherDataResponse>
}
