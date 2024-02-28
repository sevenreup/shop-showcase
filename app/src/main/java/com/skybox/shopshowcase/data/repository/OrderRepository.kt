package com.skybox.shopshowcase.data.repository

import com.skybox.shopshowcase.data.entities.OrderEntity
import com.skybox.shopshowcase.data.source.local.OrderDao
import com.skybox.shopshowcase.data.source.local.mappers.OrderEntityMapper.toModel
import com.skybox.shopshowcase.domain.model.CartItem
import com.skybox.shopshowcase.domain.model.Order
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Calendar
import javax.inject.Inject

class OrderRepository @Inject constructor(private val orderDao: OrderDao) : IOrderRepository {

    override suspend fun placeOrder(orderItems: List<CartItem>, totalAmount: Double) {
        val order = OrderEntity(
            orderDate = Calendar.getInstance().time,
            totalAmount = totalAmount
        )
        orderDao.createOrder(order, orderItems)
    }

    override fun getAllOrdersWithItems(): Flow<List<Order>> {
        return orderDao.getAllOrdersWithItems().map { list ->
            list.map {
                it.toModel()
            }
        }
    }
}
