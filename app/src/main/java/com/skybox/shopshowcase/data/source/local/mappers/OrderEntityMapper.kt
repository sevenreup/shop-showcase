package com.skybox.shopshowcase.data.source.local.mappers

import com.skybox.shopshowcase.data.entities.OrderWithItems
import com.skybox.shopshowcase.domain.model.Order
import com.skybox.shopshowcase.domain.model.OrderItem

object OrderEntityMapper {
    fun OrderWithItems.toModel(): Order {
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
}