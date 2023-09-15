package com.example.forecastapp.presentation.app

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.forecastapp.data.network.WeatherDataResponse
import com.example.forecastapp.domain.app.WeatherCityWrapper
import com.example.forecastapp.domain.repositories.FirebaseFirestoreRepository
import com.example.forecastapp.domain.repositories.ForecastRepository
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val forecastRepository: ForecastRepository
) : ViewModel() {
    private lateinit var firebaseFirestoreRepository: FirebaseFirestoreRepository

    private val _forecastList = MutableStateFlow<List<WeatherCityWrapper>>(emptyList())
    val forecastList: StateFlow<List<WeatherCityWrapper>> get() = _forecastList

    private val _progressState = MutableStateFlow(false)
    val progressState: StateFlow<Boolean> get() = _progressState

    fun getForecastForLocations(uid: String) {
        _progressState.value = true
        firebaseFirestoreRepository = FirebaseFirestoreRepository(uid)
        val forecastCityWrapperList: MutableList<WeatherCityWrapper> = mutableListOf()

        //Get location ids from firebase
        val locationList = viewModelScope.async { firebaseFirestoreRepository.getUserLocations() }

        //Get forecast for locations
        viewModelScope.launch {
            val locations = locationList.await()
            _forecastList.value = forecastRepository.getForecastFromDB(locations)

            locations.forEach {
                forecastRepository.getForecast(it)?.let { forecastCityWrapperList += it }
            }
            _progressState.value = false
            _forecastList.value = forecastCityWrapperList
        }
    }
}
