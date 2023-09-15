package com.example.forecastapp.presentation.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen(uid: String) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = MainRoutes.Dashboard.route) {
        composable(MainRoutes.Dashboard.route) {
            DashboardPage(navController = navController, uid)
        }
    }
}