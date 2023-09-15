package com.example.forecastapp.presentation.app

sealed class MainRoutes(val route: String) {
    object Dashboard : MainRoutes("Dashboard")
    object AddLocation : MainRoutes("Add_location")
}