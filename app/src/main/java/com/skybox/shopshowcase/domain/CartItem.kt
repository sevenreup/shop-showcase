package com.skybox.shopshowcase.domain

data class CartItem(
    val cartItemId: Long,
    val productId: Int,
    val productName: String,
    val quantity: Int,
    val price: Double
)