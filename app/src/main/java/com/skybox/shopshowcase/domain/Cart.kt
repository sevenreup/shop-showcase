package com.skybox.shopshowcase.domain

data class Cart(val total: Double = 0.0, val cartItems: List<CartItem> = listOf())