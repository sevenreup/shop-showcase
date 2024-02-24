package com.skybox.shopshowcase.util

import com.skybox.shopshowcase.domain.Product

fun getSampleProducts(): List<Product> {
    return listOf(
        Product("1", "Laptop", "Powerful laptop with high performance", "laptop_image.jpg", 999.99),
        Product("2", "Smartphone", "Latest smartphone with advanced features", "phone_image.jpg", 599.99),
        Product("3", "Headphones", "Premium noise-canceling headphones", "headphones_image.jpg", 149.99),
        Product("4", "Coffee Maker", "Automatic coffee maker with multiple brewing options", "coffee_maker_image.jpg", 79.99),
        Product("5", "Fitness Tracker", "Track your fitness and health with this smart tracker", "fitness_tracker_image.jpg", 49.99)
    )
}