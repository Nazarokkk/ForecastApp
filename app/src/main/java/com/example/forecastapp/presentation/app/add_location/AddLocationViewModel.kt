package com.example.forecastapp.presentation.app.add_location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.forecastapp.domain.app.WeatherCityWrapper
import com.example.forecastapp.domain.repositories.FirebaseFirestoreRepository
import com.example.forecastapp.domain.repositories.ForecastRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddLocationViewModel @Inject constructor(
    private val forecastRepository: ForecastRepository
) : ViewModel() {
    private lateinit var firebaseFirestoreRepository: FirebaseFirestoreRepository

    private val _weatherCity = MutableStateFlow<WeatherCityWrapper?>(null)
    val weatherCity: StateFlow<WeatherCityWrapper?> get() = _weatherCity

    fun getForecastForLocation(uid: String, lat: String, lon: String) {
        firebaseFirestoreRepository = FirebaseFirestoreRepository(uid)

        //Get forecast for locations
        viewModelScope.launch {
            _weatherCity.value = forecastRepository.getForecastByLatLon(lat, lon)
        }
    }

    fun addLocation(weatherCityWrapper: WeatherCityWrapper) {
        firebaseFirestoreRepository.saveNewLocation(weatherCityWrapper.locationId)

        viewModelScope.launch {
            forecastRepository.addForecastToDB(weatherCityWrapper)
        }
    }
}