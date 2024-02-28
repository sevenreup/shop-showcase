package com.skybox.shopshowcase.di

import com.skybox.shopshowcase.data.repository.AuthRepository
import com.skybox.shopshowcase.data.repository.CartRepository
import com.skybox.shopshowcase.data.repository.IAuthRepository
import com.skybox.shopshowcase.data.repository.ICartRepository
import com.skybox.shopshowcase.data.repository.IOrderRepository
import com.skybox.shopshowcase.data.repository.IProductRepository
import com.skybox.shopshowcase.data.repository.OrderRepository
import com.skybox.shopshowcase.data.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    abstract fun  buildOrderRepository(repository: OrderRepository): IOrderRepository

    @Binds
    abstract fun  buildProductRepository(repository: ProductRepository): IProductRepository

    @Binds
    abstract fun  buildAuthRepository(repository: AuthRepository): IAuthRepository

    @Binds
    abstract fun  buildCartRepository(repository: CartRepository): ICartRepository
}