package com.skybox.shopshowcase.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.skybox.shopshowcase.data.source.local.mappers.UserMapper.toModel
import com.skybox.shopshowcase.domain.model.User
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthRepository @Inject constructor(private val firebaseAuth: FirebaseAuth) : IAuthRepository {
    override fun isLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

    override fun currentUser(): User {
        return firebaseAuth.currentUser!!.toModel()
    }

    override fun logout() {
        firebaseAuth.signOut()
    }

    override fun listenToAuth(): Flow<Boolean> {
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