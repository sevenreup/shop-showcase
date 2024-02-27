package com.skybox.shopshowcase.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey(autoGenerate = true) val orderId: Long = 0,
    val orderDate: Date,
    val totalAmount: Double
)
