package com.example.forecastapp.presentation.login

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.forecastapp.presentation.app.Dashboard
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(activity: LoginActivity, auth: FirebaseAuth) {
    val navController = rememberNavController()

    auth.currentUser?.let { activity.startForecastAppActivity() }

    NavHost(navController = navController, startDestination = LoginRoutes.Login.route) {
        composable(LoginRoutes.Login.route) {
            LoginPage(navController = navController, auth, activity)
        }
        composable(LoginRoutes.SignUp.route) {
            SignUp(navController = navController, auth, activity)
        }
        composable(LoginRoutes.Dashboard.route) {
            Dashboard(navController = navController)
        }
    }
}