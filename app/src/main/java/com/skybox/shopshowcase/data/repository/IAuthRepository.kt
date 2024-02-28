package com.skybox.shopshowcase.data.repository

import com.skybox.shopshowcase.domain.model.User
import kotlinx.coroutines.flow.Flow

interface IAuthRepository {
    fun isLoggedIn(): Boolean

    fun currentUser(): User

    fun logout()

    fun listenToAuth(): Flow<Boolean>
}