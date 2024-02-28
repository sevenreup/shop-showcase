package com.skybox.shopshowcase.data.source.local.mappers

import com.skybox.shopshowcase.data.entities.CartItemWithProduct
import com.skybox.shopshowcase.domain.model.CartItem

object CartEntityMapper {
    fun CartItemWithProduct.toModel(): CartItem {
        return  CartItem(
            productId = this.product.productId,
            cartItemId = this.cartItem.cartItemId,
            productName = this.product.name,
            image = this.product.thumbnail,
            quantity = this.cartItem.quantity,
            price = this.product.price,
            brand = this.product.brand,
        )
    }
}