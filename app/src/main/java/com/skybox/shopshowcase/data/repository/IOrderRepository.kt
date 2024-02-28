package com.skybox.shopshowcase.data.repository

import com.skybox.shopshowcase.domain.model.CartItem
import com.skybox.shopshowcase.domain.model.Order
import kotlinx.coroutines.flow.Flow

interface IOrderRepository {
    suspend fun placeOrder(orderItems: List<CartItem>, totalAmount: Double)

    fun getAllOrdersWithItems(): Flow<List<Order>>
}