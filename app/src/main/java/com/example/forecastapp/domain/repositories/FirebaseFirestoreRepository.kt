package com.example.forecastapp.domain.repositories

import android.util.Log
import com.example.forecastapp.data.firebase.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
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
                Log.e("FirebaseFirestore", "Saved location $user")
            }
            .addOnFailureListener { e ->
                Log.e("FirebaseFirestore", e.localizedMessage)
            }
    }

    suspend fun removeLocation(cityId: String):Boolean = suspendCoroutine { continuation ->
        val db = FirebaseFirestore.getInstance()

        val collectionName = "users"
        val usersCollection = db.collection(collectionName)

        usersCollection.get()
            .addOnSuccessListener { documents ->
                val documentObject = documents.find { it.data["cityId"].toString() == cityId}
                documentObject?.let {
                    val docRef = db.collection(collectionName).document(it.id)
                    docRef
                        .delete()
                        .addOnSuccessListener {
                            continuation.resume(true)
                            println("Document successfully deleted!")
                        }
                        .addOnFailureListener { e ->
                            continuation.resumeWithException(e)
                            println("Error deleting document: $e")
                        }
                }
            }
            .addOnFailureListener { e ->
                continuation.resumeWithException(e)
            }
    }
}