package com.skybox.shopshowcase.data.repository

import com.skybox.shopshowcase.domain.model.CartItem
import kotlinx.coroutines.flow.Flow

interface ICartRepository {
    suspend fun addToCart(productId: Int, quantity: Int)

    suspend fun updateItem(productId: Int, quantity: Int)

    suspend fun removeFromCart(productId: Int)

    fun getCartItems(): Flow<List<CartItem>>

    suspend fun clearCartItems()
}