package com.skybox.shopshowcase.data.repository

import com.skybox.shopshowcase.data.source.local.ProductDao
import com.skybox.shopshowcase.data.source.local.mappers.ProductEntityMapper.toModel
import com.skybox.shopshowcase.domain.model.Product
import javax.inject.Inject

class ProductRepository @Inject constructor(private val productDao: ProductDao) :
    IProductRepository {
    override suspend fun getProduct(productId: Int): Product? {
        return productDao.getProduct(productId)?.toModel()
    }

    override suspend fun getProducts(): List<Product> {
        return productDao.getAllProductsWithCategories().map {
            it.toModel()
        }
    }

    override suspend fun getRecommendedProducts(
        brands: List<String>,
        categories: List<Int>,
        filterOut: List<Int>,
        priceLower: Double,
        priceUpper: Double
    ): List<Product> {
        val recommendedByBrand =
            productDao.getProductRecommendations(brand = brands, filterOut = filterOut)
        val recommendedByPrice = productDao.getRecommendedByPriceRangeAndCategory(
            minPrice = priceLower,
            maxPrice = priceUpper,
            categories = categories,
            filterOut = filterOut,
        )
        var recommendations =
            (recommendedByBrand + recommendedByPrice).distinctBy { it.product.productId }

        if (recommendations.isEmpty()) {
            recommendations = productDao.getRecommendedByCategory(categories, filterOut)
        }

        return recommendations.map {
            it.toModel()
        }
    }

}