package com.skybox.shopshowcase.data.repository

import com.skybox.shopshowcase.data.entities.OrderEntity
import com.skybox.shopshowcase.data.entities.OrderWithItems
import com.skybox.shopshowcase.data.source.local.OrderDao
import com.skybox.shopshowcase.domain.CartItem
import java.util.Calendar
import javax.inject.Inject

class OrderRepository @Inject constructor(private val orderDao: OrderDao) {

    suspend fun placeOrder(orderItems: List<CartItem>, totalAmount: Double) {
        val order = OrderEntity(
            orderDate = Calendar.getInstance().time,
            totalAmount = totalAmount
        )
        orderDao.createOrder(order, orderItems)
    }

    suspend fun getAllOrdersWithItems(): List<OrderWithItems> {
        return orderDao.getAllOrdersWithItems()
    }
}
