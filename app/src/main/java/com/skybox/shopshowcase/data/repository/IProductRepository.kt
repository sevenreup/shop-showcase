package com.skybox.shopshowcase.data.repository

import com.skybox.shopshowcase.domain.model.Product

interface IProductRepository {
    suspend fun getProduct(productId: Int): Product?

    suspend fun getProducts(): List<Product>

    suspend fun getRecommendedProducts(
        brands: List<String>,
        priceLower: Double,
        priceUpper: Double
    ): List<Product>
}