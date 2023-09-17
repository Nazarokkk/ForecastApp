package com.example.forecastapp.presentation.app.dashboard

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.forecastapp.domain.app.WeatherCityWrapper
import com.example.forecastapp.presentation.app.MainActivity
import com.example.forecastapp.presentation.app.MainTopAppBar
import com.example.forecastapp.presentation.app.WeatherView
import com.example.forecastapp.utils.Dimensions

@Composable
fun DashboardPage(
    navController: NavHostController,
    activity: MainActivity,
    uid: String,
    dashboardViewModel: DashboardViewModel = hiltViewModel()
) {
    val items by dashboardViewModel.forecastList.collectAsState(emptyList())
    val isShowProgress by dashboardViewModel.progressState.collectAsState(false)

    dashboardViewModel.getForecastForLocations(uid)

    Box(modifier = Modifier.fillMaxSize()) {
        DashboardView(navController, items, dashboardViewModel, activity, isShowProgress)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DashboardView(
    navController: NavHostController,
    items: List<WeatherCityWrapper>,
    dashboardViewModel: DashboardViewModel,
    activity: MainActivity,
    isShowProgress: Boolean
) {
    val localDim = compositionLocalOf { Dimensions() }

    Scaffold(topBar = {
        MainTopAppBar(navController, "My weather", activity)
    }, content = {
        Box(
            modifier = Modifier
                .background(Color.White)
                .padding(top = localDim.current.spaceLarge)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isShowProgress) {
                    CircularProgressIndicator()
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                            .padding(localDim.current.spaceSmall)
                    ) {

                        items(items = items) { item ->
                            val dismissState = rememberDismissState()
                            Spacer(modifier = Modifier.height(localDim.current.spaceLarge))

                            if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                                dashboardViewModel.removeItem(item)
                            }

                            SwipeToDismiss(state = dismissState,
                                directions = setOf(DismissDirection.EndToStart),
                                background = {
                                    val color by animateColorAsState(
                                        when (dismissState.targetValue) {
                                            DismissValue.Default -> Color.White
                                            else -> Color.Red
                                        }, label = ""
                                    )
                                    val alignment = Alignment.CenterEnd
                                    val icon = Icons.Default.Delete

                                    val scale by animateFloatAsState(
                                        if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f,
                                        label = ""
                                    )

                                    Box(
                                        Modifier
                                            .fillMaxSize()
                                            .background(color)
                                            .padding(horizontal = localDim.current.spaceMedium),
                                        contentAlignment = alignment
                                    ) {
                                        Icon(
                                            icon,
                                            contentDescription = "Delete Icon",
                                            tint = Color.White,
                                            modifier = Modifier.scale(scale)
                                        )
                                    }
                                },
                                dismissContent = {
                                    Spacer(modifier = Modifier.height(localDim.current.spaceMedium))
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(localDim.current.cardSize),
                                        colors = CardDefaults.cardColors(
                                            containerColor = MaterialTheme.colorScheme.background,
                                        ),
                                        elevation = CardDefaults.cardElevation(
                                            defaultElevation = localDim.current.spaceSmall
                                        ),
                                    ) {
                                        WeatherView(item)
                                    }
                                })
                        }
                    }
                }
            }
        }
    })
}

@Preview
@Composable
fun PreviewListItem() {
    val item = WeatherCityWrapper(
        lat = "123",
        lon = "213",
        temp = "49",
        datetime = "123123123123",
        locationId = "2",
        locationName = "Lviv",
        weatherType = "Clouds",
        icon = "10d"
    )
    WeatherView(item = item)
}