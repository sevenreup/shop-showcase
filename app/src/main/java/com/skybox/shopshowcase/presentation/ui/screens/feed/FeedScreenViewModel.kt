package com.skybox.shopshowcase.presentation.ui.screens.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skybox.shopshowcase.data.repository.CartRepository
import com.skybox.shopshowcase.data.repository.ProductRepository
import com.skybox.shopshowcase.domain.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedScreenViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository
) : ViewModel() {
    private val _state = MutableStateFlow(HomeUIState(listOf()))
    val state = _state.asStateFlow()
    init {
        fetch()
    }

    private fun fetch() {
        viewModelScope.launch {
            _state.emit(HomeUIState(listOf(), true))
            val products = productRepository.getProducts()
            _state.emit(HomeUIState(products))
        }

    }

    fun addToCart(product: Product) {
        viewModelScope.launch {
            cartRepository.addToCart(product.productId, 1)
        }
    }
}

data class HomeUIState(
    val products: List<Product>,
    val loading: Boolean = false,
    val error: String? = null
)