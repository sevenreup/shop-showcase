package com.skybox.shopshowcase.presentation.ui.screens.product

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skybox.shopshowcase.domain.LoadableState
import com.skybox.shopshowcase.domain.ProductDetails

class ProductViewModel : ViewModel() {
    val productDetails = MutableLiveData<LoadableState<ProductDetails>>()

    fun loadProductInfo(productId: String) {
        productDetails.value = LoadableState.Loading
        // Fetch Product Details
        // success
    }
}