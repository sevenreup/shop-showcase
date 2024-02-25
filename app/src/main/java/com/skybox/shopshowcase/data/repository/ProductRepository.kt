package com.skybox.shopshowcase.data.repository

import com.skybox.shopshowcase.data.source.local.ProductDao
import javax.inject.Inject
import javax.inject.Singleton

class ProductRepository @Inject constructor(private val productDao: ProductDao) {
    suspend fun getPlants() = productDao.getAll()
}