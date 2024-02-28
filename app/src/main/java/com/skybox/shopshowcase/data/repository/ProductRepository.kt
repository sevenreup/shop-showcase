package com.skybox.shopshowcase.data.repository

import com.skybox.shopshowcase.data.source.local.ProductDao
import com.skybox.shopshowcase.data.source.local.mappers.ProductEntityMapper.toModel
import com.skybox.shopshowcase.domain.model.Product
import javax.inject.Inject

class ProductRepository @Inject constructor(private val productDao: ProductDao) {
    suspend fun getProduct(productId: Int) = productDao.getProduct(productId)?.toModel()
    suspend fun getProducts() = productDao.getAllProductsWithCategories().map {
        it.toModel()
    }

    suspend fun getRecommendedProducts(
        brands: List<String>,
        priceLower: Double,
        priceUpper: Double
    ): List<Product> {
        val recommendedByBrand =
            productDao.getByBrand(brands)
        val recommendedByPrice = productDao.getRecommendedProductsByPriceRange(
            priceLower,
            priceUpper
        )
        return (recommendedByBrand + recommendedByPrice).distinctBy { it.product.productId }.map {
            it.toModel()
        }
    }

}