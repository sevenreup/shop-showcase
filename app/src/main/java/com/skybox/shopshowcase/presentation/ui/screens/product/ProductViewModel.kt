package com.skybox.shopshowcase.presentation.ui.screens.product

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skybox.shopshowcase.data.repository.CartRepository
import com.skybox.shopshowcase.data.repository.ProductRepository
import com.skybox.shopshowcase.domain.LoadableState
import com.skybox.shopshowcase.domain.ProductDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val productRepository: ProductRepository, private val cartRepository: CartRepository) : ViewModel() {
    val productDetails = MutableStateFlow<LoadableState<ProductDetails>>(LoadableState.Loading)

    fun loadProductInfo(productId: String) {
        viewModelScope.launch {
            productDetails.value = LoadableState.Loading
            val product = productRepository.getProduct(productId.toInt()) ?: return@launch
            productDetails.emit(LoadableState.Success(ProductDetails(product, listOf())))
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
}

