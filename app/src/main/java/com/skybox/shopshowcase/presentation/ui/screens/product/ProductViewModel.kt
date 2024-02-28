package com.skybox.shopshowcase.presentation.ui.screens.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skybox.shopshowcase.data.repository.CartRepository
import com.skybox.shopshowcase.data.repository.ProductRepository
import com.skybox.shopshowcase.domain.model.LoadableState
import com.skybox.shopshowcase.domain.model.Product
import com.skybox.shopshowcase.domain.model.ProductDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository
) : ViewModel() {
    val productDetails = MutableStateFlow<LoadableState<ProductDetails>>(LoadableState.Loading)
    val relatedProducts = MutableStateFlow<LoadableState<List<Product>>>(LoadableState.Loading)

    fun loadProductInfo(productId: String) {
        viewModelScope.launch {
            productDetails.emit(LoadableState.Loading)
            val product = productRepository.getProduct(productId.toInt()) ?: return@launch
            productDetails.emit(LoadableState.Success(ProductDetails(product, listOf())))
            getRecommendations(product)
        }
    }

    fun addToCart() {
        viewModelScope.launch {
            val current = productDetails.value
            if (current is LoadableState.Success) {
                cartRepository.addToCart(current.data.product.productId, 1)
            }
        }
    }

    private suspend fun getRecommendations(product: Product) {
        relatedProducts.emit(LoadableState.Loading)
        val products = productRepository.getRecommendedProducts(
            listOf(product.brand),
            product.price - 50,
            product.price + 50
        )
        relatedProducts.emit(LoadableState.Success(products))
    }
}

