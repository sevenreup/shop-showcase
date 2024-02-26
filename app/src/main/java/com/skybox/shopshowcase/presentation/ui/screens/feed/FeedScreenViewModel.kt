package com.skybox.shopshowcase.presentation.ui.screens.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skybox.shopshowcase.data.entities.ProductEntity
import com.skybox.shopshowcase.data.repository.CartRepository
import com.skybox.shopshowcase.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedScreenViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository
) : ViewModel() {
    val state = MutableStateFlow(HomeUIState(listOf()))

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

    fun addToCart(product: ProductEntity) {
        viewModelScope.launch {
            cartRepository.addToCart(product.productId, 1)
        }
    }
}

data class HomeUIState(
    val products: List<ProductEntity>,
    val loading: Boolean = false,
    val error: String? = null
)