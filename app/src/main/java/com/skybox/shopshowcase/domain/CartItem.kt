package com.skybox.shopshowcase.domain

import com.bumptech.glide.signature.ObjectKey

data class CartItem(
    val cartItemId: Long,
    val productId: Int,
    val image: String,
    val productName: String,
    val brand: String,
    val quantity: Int,
    val price: Double
){
    fun signature() = ObjectKey(productId)
}