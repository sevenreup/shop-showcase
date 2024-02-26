package com.skybox.shopshowcase.presentation.ui.screens.home

import androidx.lifecycle.ViewModel
import com.skybox.shopshowcase.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    fun isLoggedIn(): Boolean = authRepository.isLoggedIn()
}