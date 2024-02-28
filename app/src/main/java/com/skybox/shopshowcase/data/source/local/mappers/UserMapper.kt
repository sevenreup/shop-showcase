package com.skybox.shopshowcase.data.source.local.mappers

import com.google.firebase.auth.FirebaseUser
import com.skybox.shopshowcase.domain.model.User

object UserMapper {
    fun FirebaseUser.toModel(): User {
        return User(
            id = this.uid,
            email = this.email,
            username = this.displayName ?: "",
            profileImage = this.photoUrl?.toString()
        )
    }
}