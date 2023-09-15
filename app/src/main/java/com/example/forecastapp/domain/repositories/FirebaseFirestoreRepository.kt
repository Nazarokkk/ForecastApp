package com.example.forecastapp.domain.repositories

import android.util.Log
import com.example.forecastapp.data.firebase.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FirebaseFirestoreRepository(
    val uid: String
) {
    suspend fun getUserLocations():List<String> = suspendCoroutine { continuation ->
        val db = FirebaseFirestore.getInstance()
        val usersCollection = db.collection("users")

        val cityIdList: MutableList<String> = mutableListOf()

        usersCollection.get()
            .addOnSuccessListener { documents ->
                documents.filter { it.data["uid"].toString() == uid }.forEach {
                    cityIdList.add(it.data["cityId"].toString())
                }
                continuation.resume(cityIdList)
            }
            .addOnFailureListener { e ->
                continuation.resumeWithException(e)
            }
    }

    fun saveNewLocation(cityId: String) {
        val db = FirebaseFirestore.getInstance()
        val usersCollection = db.collection("users")
        val user = FirebaseUser(uid, cityId)

        usersCollection
            .add(user)
            .addOnSuccessListener { documentReference ->
                // Document added successfully
                val userId = documentReference.id
                Log.e("FirebaseFirestore", "Saved location $user")
            }
            .addOnFailureListener { e ->
                Log.e("FirebaseFirestore", e.localizedMessage)
            }
    }
}