package com.example.forecastapp.presentation.app

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.sp
import com.example.forecastapp.domain.app.WeatherCityWrapper
import com.example.forecastapp.utils.Dimensions
import com.example.forecastapp.utils.ViewHelper

@Composable
fun WeatherView(item: WeatherCityWrapper) {
    val localDim = compositionLocalOf { Dimensions() }

    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(localDim.current.spaceMedium)
        ) {
            Row {
                Text(
                    fontSize = 28.sp,
                    text = "${item.locationName}  ${item.temp}°"
                )
            }
            Spacer(modifier = Modifier.height(localDim.current.spaceSmall))
            Row(
                modifier = Modifier.weight(1.5f),
            ) {
                Text(
                    fontStyle = FontStyle.Italic,
                    text = item.weatherType
                )
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {
            Image(
                painter = painterResource(id = ViewHelper.changeBgAccToTemp(item.icon)),
                contentDescription = "icon",
                modifier = Modifier
                    .height(localDim.current.spaceXXLarge)
                    .padding(localDim.current.spaceSmall)
            )
        }
    }
}
