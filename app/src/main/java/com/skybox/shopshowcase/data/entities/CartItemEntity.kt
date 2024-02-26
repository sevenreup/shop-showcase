package com.skybox.shopshowcase.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_item")
data class CartItemEntity(
    @PrimaryKey(autoGenerate = true) val cartItemId: Long = 0,
    val productId: Int,
    val quantity: Int
)