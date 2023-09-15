package com.example.forecastapp.presentation.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.forecastapp.presentation.app.add_location.AddCityPage
import com.example.forecastapp.presentation.app.dashboard.DashboardPage

@Composable
fun MainScreen(activity: MainActivity, uid: String) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = MainRoutes.Dashboard.route) {
        composable(MainRoutes.Dashboard.route) {
            DashboardPage(navController = navController,activity, uid)
        }
        composable(MainRoutes.AddLocation.route) {
            AddCityPage(navController = navController, uid)
        }
    }
}