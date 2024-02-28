package com.skybox.shopshowcase.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.skybox.shopshowcase.data.entities.CategoryEntity
import com.skybox.shopshowcase.data.entities.ProductEntity
import com.skybox.shopshowcase.data.entities.ProductAndCategory

@Dao
interface ProductDao {
    @Query("SELECT * FROM product WHERE productId = :productId")
    suspend fun getProduct(productId: Int): ProductAndCategory?

    @Query("SELECT * FROM product")
    suspend fun getAll(): List<ProductEntity>

    @Query("SELECT * FROM product WHERE productId IN (:productIds)")
    fun loadAllByIds(productIds: IntArray): List<ProductEntity>

    @Query("SELECT * FROM product")
    suspend fun getAllProductsWithCategories(): List<ProductAndCategory>

    @Query("SELECT * FROM product WHERE brand IN (:brand) AND productId NOT IN(:filterOut) LIMIT 5")
    suspend fun getProductRecommendations(brand: List<String>, filterOut: List<Int>): List<ProductAndCategory>

    @Query("SELECT * FROM product WHERE price BETWEEN :minPrice AND :maxPrice AND productId NOT IN(:filterOut) AND categoryId IN(:categories) LIMIT 5")
    suspend fun getRecommendedByPriceRangeAndCategory(minPrice: Double, maxPrice: Double, categories: List<Int>, filterOut: List<Int>): List<ProductAndCategory>

    @Query("SELECT * FROM product WHERE productId NOT IN(:filterOut) AND categoryId IN(:categories) LIMIT 5")
    suspend fun getRecommendedByCategory(categories: List<Int>, filterOut: List<Int>): List<ProductAndCategory>

    @Insert
    fun insertAll(vararg products: ProductEntity)

    @Upsert
    suspend fun upsertAll(products: List<ProductEntity>)

    @Delete
    fun delete(product: ProductEntity)

    @Upsert
    fun insertCategories(categories: List<CategoryEntity>)
}