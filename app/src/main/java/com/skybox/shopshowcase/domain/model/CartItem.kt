package com.skybox.shopshowcase.domain.model

import com.bumptech.glide.signature.ObjectKey

data class CartItem(
    val cartItemId: Long,
    val productId: Int,
    val image: String,
    val productName: String,
    val brand: String,
    val quantity: Int,
    val price: Double,
    val category: Pair<Int, String>
){
    fun signature() = ObjectKey(productId)
}