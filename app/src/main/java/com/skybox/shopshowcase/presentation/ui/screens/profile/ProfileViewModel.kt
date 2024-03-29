package com.skybox.shopshowcase.presentation.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skybox.shopshowcase.data.repository.AuthRepository
import com.skybox.shopshowcase.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val authRepository: AuthRepository) :
    ViewModel() {
    private val _state = MutableStateFlow(ProfileUIState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            fetchData()
        }
    }

    private suspend fun fetchData() {
        val user = authRepository.currentUser()
        _state.emit(ProfileUIState(user = user))
    }

    fun logout() {
        authRepository.logout()
    }
}

data class ProfileUIState(val user: User? = null)