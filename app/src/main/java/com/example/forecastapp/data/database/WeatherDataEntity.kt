package com.example.forecastapp.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.example.forecastapp.domain.app.WeatherCityWrapper

@Entity(tableName = "weather_data", primaryKeys = ["locationId"])
class WeatherDataEntity(
    @ColumnInfo(name = "lat")
    val lat: String,

    @ColumnInfo(name = "lon")
    val lon: String,

    @ColumnInfo(name = "temp")
    val temp: String,

    @ColumnInfo(name = "datetime")
    val datetime: String,

    @ColumnInfo(name = "locationId")
    val locationId: String,

    @ColumnInfo(name = "locationName")
    val locationName: String,

    @ColumnInfo(name = "weatherType")
    val weatherType: String,

    @ColumnInfo(name = "icon")
    val icon: String
){
    companion object{
        fun WeatherDataEntity.toWeatherCityWrapper(): WeatherCityWrapper{
            return WeatherCityWrapper(
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