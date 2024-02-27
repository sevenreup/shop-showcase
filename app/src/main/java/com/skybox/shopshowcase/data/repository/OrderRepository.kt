package com.skybox.shopshowcase.data.repository

import com.skybox.shopshowcase.data.entities.OrderEntity
import com.skybox.shopshowcase.data.entities.OrderWithItems
import com.skybox.shopshowcase.data.source.local.OrderDao
import com.skybox.shopshowcase.domain.CartItem
import com.skybox.shopshowcase.domain.Order
import com.skybox.shopshowcase.domain.OrderItem
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
            it.toOrder()
        }
    }
}

fun OrderWithItems.toOrder(): Order {
    return Order(
        orderId = order.orderId,
        orderDate = order.orderDate,
        totalAmount = order.totalAmount,
        items = orderItems.map {
            OrderItem(
                productId = it.product.productId,
                productName = it.product.name,
                productImage = it.product.thumbnail,
                quantity = it.orderItem.quantity,
                price = it.orderItem.price
            )
        }
    )
}
