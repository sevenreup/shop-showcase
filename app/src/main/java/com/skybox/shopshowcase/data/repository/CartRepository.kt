package com.skybox.shopshowcase.data.repository

import com.skybox.shopshowcase.data.entities.CartItemEntity
import com.skybox.shopshowcase.data.entities.CartItemWithProduct
import com.skybox.shopshowcase.data.source.local.CartDao
import com.skybox.shopshowcase.domain.CartItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartRepository @Inject constructor(private val cartDao: CartDao) {

    suspend fun addToCart(productId: Int, quantity: Int) {
        val existingCartItem = cartDao.getCartItem(productId)

        if (existingCartItem != null) {
            val updatedQuantity = existingCartItem.cartItem.quantity + quantity
            cartDao.insertCartItem(existingCartItem.cartItem.copy(quantity = updatedQuantity))
        } else {
            val newCartItem = CartItemEntity(productId = productId, quantity = quantity)
            cartDao.insertCartItem(newCartItem)
        }
    }

    suspend fun removeFromCart(productId: Int) {
        val existingCartItem = cartDao.getCartItem(productId)

        if (existingCartItem != null) {
            cartDao.deleteCartItem(existingCartItem.cartItem)
        }
    }

    fun getCartItems(): Flow<List<CartItem>> = cartDao.getAllCartItemsWithProduct().map {list ->
        list.map { item ->
            CartItem(
                productId = item.product.productId,
                cartItemId = item.cartItem.cartItemId,
                productName = item.product.name,
                quantity = item.cartItem.quantity
            )
        }
    }
}
