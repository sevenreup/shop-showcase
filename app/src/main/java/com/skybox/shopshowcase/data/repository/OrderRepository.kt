package com.skybox.shopshowcase.data.repository

import com.skybox.shopshowcase.data.entities.OrderEntity
import com.skybox.shopshowcase.data.source.local.OrderDao
import com.skybox.shopshowcase.data.source.local.mappers.OrderEntityMapper.toModel
import com.skybox.shopshowcase.domain.model.CartItem
import kotlinx.coroutines.flow.map
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

    fun getAllOrdersWithItems() = orderDao.getAllOrdersWithItems().map { list ->
        list.map {
            it.toModel()
        }
    }
}
