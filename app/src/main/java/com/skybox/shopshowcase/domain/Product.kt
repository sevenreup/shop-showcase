package com.skybox.shopshowcase.domain

data class Product(val id: String, val name: String, val description: String, val image: String, val price: Double)


data class ProductDetails(val product: Product, val recommendations: List<Product>)