package com.example.forecastapp.data.network

import com.example.forecastapp.domain.app.WeatherCityWrapper
import kotlin.math.roundToInt

data class WeatherDataResponse(
    val coord: Coord,
    val weather: List<Weather>,
    val main: Main,
    val dt: Long,
    val id: Long,
    val name: String,
    val cod: Long
) {
    companion object{
        fun WeatherDataResponse.toWeatherCityWrapper(): WeatherCityWrapper{
            return WeatherCityWrapper(
                lat = coord.lat.toString(),
                lon = coord.lon.toString(),
                temp = main.temp.roundToInt().toString(),
                datetime = dt.toString(),
                locationId = id.toString(),
                locationName = name,
                weatherType = weather[0].main,
                icon = weather[0].icon
            )
        }
    }
}
data class Coord(
    val lon: Double,
    val lat: Double
)

data class Main(
    val temp: Double
)

data class Weather(
    val main: String,
    val icon: String
)