package com.example.forecastapp.presentation.app.add_location

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.forecastapp.presentation.app.WeatherView
import com.example.forecastapp.utils.Dimensions
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCityPage(
    navController: NavHostController,
    uid: String,
    addLocationViewModel: AddLocationViewModel = hiltViewModel()
) {
    val item by addLocationViewModel.weatherCity.collectAsState(null)

    val localDim = compositionLocalOf { Dimensions() }
    val pos = LatLng(49.8383, 24.0232)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(pos, 10f)
    }
    val mapState = rememberMarkerState(position = pos)
    var isBottomSheetVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        GoogleMap(
            modifier = Modifier
                .fillMaxWidth(),
            cameraPositionState = cameraPositionState,
            onMapClick = {
                Log.e("Location latLon", "lat:${it.latitude} lon:${it.longitude}")
                cameraPositionState.position = CameraPosition.fromLatLngZoom(it, 10f)
                mapState.position = it
                addLocationViewModel.getForecastForLocation(
                    uid,
                    it.latitude.toString(),
                    it.longitude.toString()
                )
                isBottomSheetVisible = true
            }
        ) {
            Marker(state = mapState)
        }

        if (isBottomSheetVisible) {
            ModalBottomSheet(
                onDismissRequest = { isBottomSheetVisible = false },
            ) {
                item?.let {
                    Spacer(modifier = Modifier.height(localDim.current.spaceMedium))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(localDim.current.spaceMedium)
                    ) {
                        Row(modifier = Modifier.height(localDim.current.cardSize)) {
                            WeatherView(it)
                        }
                        Button(
                            onClick = {
                                addLocationViewModel.addLocation(it)
                                isBottomSheetVisible = false
                                navController.navigateUp()
                            },

                            modifier = Modifier
                                .padding(
                                    bottom = localDim.current.spaceLarge,
                                    top = localDim.current.spaceMedium
                                )
                                .align(Alignment.CenterHorizontally)
                        ) {
                            Text("Save new location")
                        }
                    }
                }
            }
        }
    }
}