package com.example.forecastapp.presentation.app.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.forecastapp.domain.app.WeatherCityWrapper
import com.example.forecastapp.domain.repositories.FirebaseFirestoreRepository
import com.example.forecastapp.domain.repositories.ForecastRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val forecastRepository: ForecastRepository
) : ViewModel() {
    private lateinit var firebaseFirestoreRepository: FirebaseFirestoreRepository

    private val _forecastList = MutableStateFlow<List<WeatherCityWrapper>>(emptyList())
    val forecastList: StateFlow<List<WeatherCityWrapper>> get() = _forecastList

    private val _progressState = MutableStateFlow(false)
    val progressState: StateFlow<Boolean> get() = _progressState

    private lateinit var uid : String

    fun getForecastForLocations(uid: String) {
        _progressState.value = true
        this.uid = uid
        firebaseFirestoreRepository = FirebaseFirestoreRepository(uid)

        //Get location ids from firebase
        val locationList = viewModelScope.async { firebaseFirestoreRepository.getUserLocations() }

        //Get forecast for locations from server
        viewModelScope.launch {
            val locations = locationList.await()
            _forecastList.value = forecastRepository.getForecastListByCityId(locations)
            _progressState.value = false
        }
    }

    fun removeItem(item: WeatherCityWrapper) {
        _progressState.value = true

        viewModelScope.launch {
            val isDeleted = firebaseFirestoreRepository.removeLocation(item.locationId)
            _progressState.value = isDeleted
            getForecastForLocations(uid)
        }
    }
}
