package com.example.forecastapp.presentation.app

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

data class PersonModel(val name: String)

private val personsList = mutableListOf<PersonModel>()

@Composable
fun Dashboard(navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize()) {
        DashboardPage(navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DashboardPage(navController: NavHostController) {
    personsList.add(PersonModel("James"))
    personsList.add(PersonModel("John"))
    personsList.add(PersonModel("Robert"))
    personsList.add(PersonModel("Michael"))
    personsList.add(PersonModel("William"))
    personsList.add(PersonModel("David"))
    personsList.add(PersonModel("Richard"))
    personsList.add(PersonModel("Charles"))
    personsList.add(PersonModel("Joseph"))
    personsList.add(PersonModel("Steven"))
    personsList.add(PersonModel("Kenneth"))
    personsList.add(PersonModel("George"))
    personsList.add(PersonModel("Donald"))

    Scaffold(
        content = {
            Box(modifier = Modifier.background(Color.White)) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                            .padding(10.dp)
                    ) {
                        items(personsList) { model ->
                            ListRow(model = model)
                        }
                    }
                }
            }
        })
}

@Composable
fun ListRow(model: PersonModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        val paddingModifier = Modifier.padding(10.dp)
        Card(modifier = paddingModifier) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = model.name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                )
            }
        }
    }
}