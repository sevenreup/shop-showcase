package com.skybox.shopshowcase.domain

import com.skybox.shopshowcase.data.entities.CartItemWithProduct

data class Cart(val total: Double = 0.0, val cartItems: List<CartItemWithProduct> = listOf())