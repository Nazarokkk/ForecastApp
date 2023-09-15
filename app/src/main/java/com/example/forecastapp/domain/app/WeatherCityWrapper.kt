package com.example.forecastapp.domain.app

import com.example.forecastapp.data.database.WeatherDataEntity

data class WeatherCityWrapper (
    val lat: String,

    val lon: String,

    val temp: String,

    val datetime: String,

    val locationId: String,

    val locationName: String,

    val weatherType: String,

    val icon: String
){
    companion object{
        fun WeatherCityWrapper.toWeatherDataEntity(): WeatherDataEntity{
            return WeatherDataEntity(
                lat = lat,
                lon = lon,
                temp = temp,
                datetime = datetime,
                locationId = locationId,
                locationName = locationName,
                weatherType = weatherType,
                icon = icon
            )
        }
    }
}