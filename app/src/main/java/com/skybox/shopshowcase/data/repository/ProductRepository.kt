package com.skybox.shopshowcase.data.repository

import com.skybox.shopshowcase.data.source.local.ProductDao
import com.skybox.shopshowcase.domain.Product
import javax.inject.Inject

class ProductRepository @Inject constructor(private val productDao: ProductDao) {
    suspend fun getProducts() = productDao.getAll().map {
        Product(
            productId = it.productId,
            name = it.name,
            description = it.description,
            images = it.images,
            thumbnail = it.thumbnail,
            price = it.price,
        )
    }
}