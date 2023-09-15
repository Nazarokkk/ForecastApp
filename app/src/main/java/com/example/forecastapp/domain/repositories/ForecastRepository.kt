package com.example.forecastapp.domain.repositories

import com.example.forecastapp.data.database.WeatherDataEntity.Companion.toWeatherCityWrapper
import com.example.forecastapp.data.network.WeatherDataResponse
import com.example.forecastapp.data.network.WeatherDataResponse.Companion.toWeatherCityWrapper
import com.example.forecastapp.domain.app.WeatherCityWrapper
import com.example.forecastapp.domain.app.WeatherCityWrapper.Companion.toWeatherDataEntity
import com.example.forecastapp.domain.database.WeatherDao
import com.example.forecastapp.domain.network.ApiService
import com.example.forecastapp.domain.network.ApiResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class ForecastRepository(
    private val dao: WeatherDao,
    private val apiService: ApiService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseRepository() {

    suspend fun getForecast(
        cityId: String,
    ): WeatherCityWrapper? {
        val weatherApiResponse = safeApiCall(dispatcher) {
            apiService.findCityWeatherData(cityId)
        }

        return when (weatherApiResponse) {
            is ApiResultWrapper.Success -> {
                val weatherCity = weatherApiResponse.value.body()?.toWeatherCityWrapper()
                weatherCity?.let {
                    dao.insertWeather(it.toWeatherDataEntity())
                    return weatherCity
                }
            }

            else -> null
        }
    }

    suspend fun getForecastFromDB(
        cityIds: List<String>,
    ): List<WeatherCityWrapper> {
        val weatherList: MutableList<WeatherCityWrapper> = mutableListOf()
        cityIds.forEach {
            dao.getWeather(it)?.let { weatherList.add(it.toWeatherCityWrapper()) }
        }

        return weatherList
    }
}