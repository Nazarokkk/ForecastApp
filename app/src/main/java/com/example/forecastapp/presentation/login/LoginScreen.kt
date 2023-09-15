package com.example.forecastapp.presentation.login

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(activity: LoginActivity, auth: FirebaseAuth) {
    val navController = rememberNavController()

    auth.currentUser?.let { activity.startForecastAppActivity(it.uid) }

    NavHost(navController = navController, startDestination = LoginRoutes.Login.route) {
        composable(LoginRoutes.Login.route) {
            LoginPage(navController = navController, auth, activity)
        }
        composable(LoginRoutes.SignUp.route) {
            SignUp(navController = navController, auth, activity)
        }
    }
}