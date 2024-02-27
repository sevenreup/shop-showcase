package com.skybox.shopshowcase.domain

import java.util.Date

data class Order(
    val orderId: Long,
    val orderDate: Date,
    val totalAmount: Double, val items: List<OrderItem>
)

data class OrderItem(
    val productId: Int,
    val productName: String,
    val productImage: String,
    val quantity: Int,
    val price: Double
)