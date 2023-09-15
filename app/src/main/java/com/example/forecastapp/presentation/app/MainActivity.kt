package com.example.forecastapp.presentation.app

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.forecastapp.presentation.login.LoginActivity
import com.example.forecastapp.ui.theme.ForecastAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val firebaseUID = intent.getStringExtra("FIREBASE_UID")?:"GL2AwYA0hbbs8TdrLrAVaVx8sA23"

        setContent {
            ForecastAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(this, firebaseUID)
                }
            }
        }
    }

    fun startLogInActivity(){
        val intent = Intent(this@MainActivity, LoginActivity::class.java)
        this@MainActivity.startActivity(intent)
        finish()
    }
}