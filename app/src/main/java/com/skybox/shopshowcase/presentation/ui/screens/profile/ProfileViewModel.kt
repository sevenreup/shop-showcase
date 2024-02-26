package com.skybox.shopshowcase.presentation.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skybox.shopshowcase.data.repository.AuthRepository
import com.skybox.shopshowcase.domain.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {
    val state = MutableStateFlow(ProfileUIState())

    init {
        viewModelScope.launch {
            fetchData()
        }
    }

    private suspend fun fetchData() {
        val user = authRepository.currentUser()
        state.emit(ProfileUIState(user = user))
    }

    fun logout() {
        authRepository.logout()
    }
}

data class ProfileUIState(val user: User? = null)