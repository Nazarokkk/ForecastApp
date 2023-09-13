package com.example.forecastapp

import android.app.Application
import com.google.firebase.FirebaseApp

class ForecastApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}