package com.example.forecastapp.presentation.app.dashboard

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.forecastapp.domain.app.WeatherCityWrapper
import com.example.forecastapp.presentation.app.MainActivity
import com.example.forecastapp.presentation.app.MainTopAppBar
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
        DashboardView(navController, items, activity, isShowProgress)
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DashboardView(
    navController: NavHostController,
    items: List<WeatherCityWrapper>,
    activity: MainActivity,
    isShowProgress: Boolean
) {
    Scaffold(topBar = {
        MainTopAppBar(navController, "My weather", activity)
    }, content = {
        Box(modifier = Modifier
            .background(Color.White)
            .padding(top = 48.dp)) {
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
                            .padding(10.dp)
                    ) {
                        items(items = items) { item ->
                            ListRow(item) {
                                //onItemClick
                            }
                        }
                    }
                }
            }
        }
    })
}

@Composable
fun ListRow(item: WeatherCityWrapper, onItemClick: () -> Unit) {
    val localDim = compositionLocalOf { Dimensions() }
    Spacer(modifier = Modifier.height(localDim.current.spaceMedium))
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = localDim.current.spaceSmall
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onItemClick.invoke() }
                .padding(localDim.current.spaceMedium)
        ) {
            Row {
                Text(
                    fontSize = 24.sp,
                    text = item.locationName
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "${item.temp}°",
                    textAlign = TextAlign.End
                )
            }

            Spacer(modifier = Modifier.height(localDim.current.spaceSmall))
            Row {
                Row(
                    modifier = Modifier.weight(1.5f),
                ) {
                    Text(
                        fontStyle = FontStyle.Italic,
                        text = item.weatherType
                    )
                }
                Text(
                    modifier = Modifier
                        .weight(1.0f)
                        .fillMaxWidth(),
                    text = item.icon,
                    textAlign = TextAlign.End
                )
            }
        }
    }
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
        icon = "123"
    )
    ListRow(item = item) {}
}