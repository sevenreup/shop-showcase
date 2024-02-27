package com.skybox.shopshowcase.data.entities

import androidx.room.Embedded
import androidx.room.Relation

data class OrderWithItems(
    @Embedded val order: OrderEntity,
    @Relation(
        entity = OrderItemEntity::class,
        parentColumn = "orderId",
        entityColumn = "orderId"
    )
    val orderItems: List<OrderItemWithProduct>
)

data class OrderItemWithProduct(
    @Embedded val orderItem: OrderItemEntity,
    @Relation(
        parentColumn = "productId",
        entityColumn = "productId"
    )
    val product: ProductEntity
)