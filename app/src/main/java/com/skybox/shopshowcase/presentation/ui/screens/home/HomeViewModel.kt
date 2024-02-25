package com.skybox.shopshowcase.presentation.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skybox.shopshowcase.data.repository.ProductRepository
import com.skybox.shopshowcase.domain.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val productRepository: ProductRepository): ViewModel() {
    init {
        fetch()
    }

    fun fetch() {
        viewModelScope.launch {
            productRepository.getPlants()
        }

    }
    fun addToCart(product: Product) {}
}