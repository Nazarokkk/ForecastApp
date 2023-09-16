package com.example.forecastapp.utils

import com.example.forecastapp.R

object ViewHelper {
     fun changeBgAccToTemp(iconCode: String?) = when (iconCode) {
            "01d", "02d", "03d" -> R.drawable.sunny_day
            "04d", "09d", "10d", "11d" -> R.drawable.raining
            "13d", "50d" -> R.drawable.snowfalling
         else -> R.drawable.sunny_day
     }
}