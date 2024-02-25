package com.skybox.shopshowcase.domain

sealed class LoadableState<out T> {
    data object Loading : LoadableState<Nothing>()
    data class Success<out T>(val data: T) : LoadableState<T>()
    data class Error(val exception: Exception) : LoadableState<Nothing>()
}