package com.skybox.shopshowcase.domain.model

import com.bumptech.glide.signature.ObjectKey

data class Product(
    val productId: Int,
    val name: String,
    val description: String,
    val images: List<String>,
    val thumbnail: String,
    val price: Double,
    val brand: String,
    val rating: Double,
    val categories: List<String>
){
    fun signature() = ObjectKey(productId)
}


data class ProductDetails(val product: Product, val recommendations: List<Product>)