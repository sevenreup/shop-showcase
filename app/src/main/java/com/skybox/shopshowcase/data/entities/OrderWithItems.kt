package com.skybox.shopshowcase.data.entities

import androidx.room.Embedded
import androidx.room.Relation

data class OrderWithItems(
    @Embedded val order: OrderEntity,
    @Relation(
        parentColumn = "orderId",
        entityColumn = "orderId"
    )
    val orderItems: List<OrderItemEntity>
)