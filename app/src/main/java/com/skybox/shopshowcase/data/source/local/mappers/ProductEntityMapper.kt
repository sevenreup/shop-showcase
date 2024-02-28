package com.skybox.shopshowcase.data.source.local.mappers

import com.skybox.shopshowcase.data.entities.ProductWithCategories
import com.skybox.shopshowcase.domain.model.Product
object ProductEntityMapper  {
     fun ProductWithCategories.toModel(): Product {
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