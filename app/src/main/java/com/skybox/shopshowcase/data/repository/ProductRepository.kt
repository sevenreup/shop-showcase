package com.skybox.shopshowcase.data.repository

import com.skybox.shopshowcase.data.entities.ProductEntity
import com.skybox.shopshowcase.data.entities.ProductWithCategories
import com.skybox.shopshowcase.data.source.local.ProductDao
import com.skybox.shopshowcase.domain.Product
import javax.inject.Inject

class ProductRepository @Inject constructor(private val productDao: ProductDao) {
    suspend fun getProduct(productId: Int) = productDao.getProduct(productId)?.toProduct()
    suspend fun getProducts() = productDao.getAllProductsWithCategories().map {
        it.toProduct()
    }

    companion object {
        fun ProductWithCategories.toProduct(): Product {
            return Product(
                productId = product.productId,
                name = product.name,
                description = product.description,
                images = product.images,
                thumbnail = product.thumbnail,
                price = product.price,
                categories = categories.map { it.categoryName },
                rating = product.rating,
                brand = product.brand
            )
        }
    }
}