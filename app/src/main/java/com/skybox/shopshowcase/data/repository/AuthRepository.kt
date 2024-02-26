package com.skybox.shopshowcase.data.repository

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class AuthRepository @Inject constructor(private val firebaseAuth: FirebaseAuth){
    fun isLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }
}