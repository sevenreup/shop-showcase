package com.skybox.shopshowcase.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class ProductEntity(
    @PrimaryKey val productId: Int,
    val name: String,
    val description: String,
    val image: String,
    val price: Double
)