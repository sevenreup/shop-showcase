package com.skybox.shopshowcase.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.skybox.shopshowcase.domain.User
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthRepository @Inject constructor(private val firebaseAuth: FirebaseAuth) {
    fun isLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

    fun currentUser(): User {
        val currentUser = firebaseAuth.currentUser!!
        return User(
            id = currentUser.uid,
            email = currentUser.email,
            username = currentUser.displayName ?: "",
            profileImage = currentUser.photoUrl?.toString()
        )
    }

    fun logout() {
        firebaseAuth.signOut()
    }

    fun listenToAuth(): Flow<Boolean> {
        return callbackFlow {
            val listener = FirebaseAuth.AuthStateListener { auth ->
                val user = auth.currentUser
                this@callbackFlow.launch {
                    this@callbackFlow.send(user != null)
                }
            }
            firebaseAuth.addAuthStateListener(listener)

            awaitClose {
                firebaseAuth.removeAuthStateListener(listener)
            }
        }

    }
}