package com.skybox.shopshowcase.data.source.local.mappers

import com.skybox.shopshowcase.data.entities.ProductAndCategory
import com.skybox.shopshowcase.domain.model.Product
object ProductEntityMapper  {
     fun ProductAndCategory.toModel(): Product {
        return Product(
            productId = product.productId,
            name = product.name,
            description = product.description,
            images = product.images,
            thumbnail = product.thumbnail,
            price = product.price,
            category = Pair(category.categoryId, category.categoryName),
            rating = product.rating,
            brand = product.brand
        )
    }

}