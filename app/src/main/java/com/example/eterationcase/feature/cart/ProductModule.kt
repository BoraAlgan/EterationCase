package com.example.eterationcase.feature.cart

import com.example.eterationcase.feature.cart.data.ProductRepositoryImp
import com.example.eterationcase.feature.cart.domain.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ProductModule {

    @Binds
    fun bindProductRepository(impl: ProductRepositoryImp): ProductRepository

}