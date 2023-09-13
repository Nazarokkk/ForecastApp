package com.example.forecastapp.presentation.login

sealed class LoginRoutes(val route: String) {
    object SignUp : LoginRoutes("SignUp")
    object Login : LoginRoutes("Login")
    object Dashboard : LoginRoutes("Dashboard")
}