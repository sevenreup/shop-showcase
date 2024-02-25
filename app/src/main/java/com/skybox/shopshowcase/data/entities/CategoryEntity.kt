package com.skybox.shopshowcase.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class CategoryEntity(
    @PrimaryKey val categoryId: Int,
    val categoryName: String
)