package com.skybox.shopshowcase.data.repository

import com.skybox.shopshowcase.data.source.local.ProductDao
import javax.inject.Inject

class ProductRepository @Inject constructor(private val productDao: ProductDao) {
    suspend fun getProducts() = productDao.getAll()
}