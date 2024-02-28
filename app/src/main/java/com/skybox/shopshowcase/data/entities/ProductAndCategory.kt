package com.skybox.shopshowcase.data.entities

import androidx.room.Embedded
import androidx.room.Relation

data class ProductAndCategory(
    @Embedded val product: ProductEntity,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "categoryId"
    )
    val category: CategoryEntity
)