package com.skybox.shopshowcase.data.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Junction
import androidx.room.Relation

@Entity(tableName = "product_category_cross_ref", primaryKeys = ["productId", "categoryId"])
data class ProductCategoryCrossRef(
    val productId: Int,
    val categoryId: Int
)

data class ProductWithCategories(
    @Embedded val product: ProductEntity,
    @Relation(
        entity = CategoryEntity::class,
        parentColumn = "productId",
        entityColumn = "categoryId",
        associateBy = Junction(ProductCategoryCrossRef::class)
    )
    val categories: List<CategoryEntity>
)