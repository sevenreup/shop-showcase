package com.skybox.shopshowcase.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.skybox.shopshowcase.data.entities.CartItemEntity
import com.skybox.shopshowcase.data.entities.CategoryEntity
import com.skybox.shopshowcase.data.entities.ProductCategoryCrossRef
import com.skybox.shopshowcase.data.entities.ProductEntity
import com.skybox.shopshowcase.data.entities.ProductWithCategories

@Dao
interface ProductDao {
    @Query("SELECT * FROM product WHERE productId = :productId")
    suspend fun getProduct(productId: Int): ProductWithCategories?

    @Query("SELECT * FROM product")
    suspend fun getAll(): List<ProductEntity>

    @Query("SELECT * FROM product WHERE productId IN (:productIds)")
    fun loadAllByIds(productIds: IntArray): List<ProductEntity>

    @Query("SELECT * FROM product")
    suspend fun getAllProductsWithCategories(): List<ProductWithCategories>

    @Insert
    fun insertAll(vararg products: ProductEntity)

    @Upsert
    suspend fun upsertAll(products: List<ProductEntity>)

    @Delete
    fun delete(product: ProductEntity)
    @Upsert
    fun insertProductCategoryCrossRef(productCategoryCrossRefs: List<ProductCategoryCrossRef>)

    @Upsert
    fun insertCategories(categories: List<CategoryEntity>)
}