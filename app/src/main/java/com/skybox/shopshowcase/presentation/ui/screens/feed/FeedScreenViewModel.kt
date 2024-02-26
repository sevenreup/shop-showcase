package com.skybox.shopshowcase.presentation.ui.screens.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skybox.shopshowcase.data.entities.ProductEntity
import com.skybox.shopshowcase.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedScreenViewModel @Inject constructor(val productRepository: ProductRepository) : ViewModel() {
    val state = MutableStateFlow<HomeUIState>(HomeUIState(listOf()))

    init {
        fetch()
    }

    private fun fetch() {
        viewModelScope.launch {
            state.emit(HomeUIState(listOf(), true))
            val products = productRepository.getProducts()
            state.emit(HomeUIState(products))
        }

    }

    fun addToCart(product: ProductEntity) {}
}

data class HomeUIState(
    val products: List<ProductEntity>,
    val loading: Boolean = false,
    val error: String? = null
)