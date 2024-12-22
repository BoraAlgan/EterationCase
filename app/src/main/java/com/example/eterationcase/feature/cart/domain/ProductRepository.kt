package com.example.eterationcase.feature.cart.domain

import com.example.eterationcase.feature.cart.data.ProductEntity

interface ProductRepository {
    suspend fun getAll(): List<ProductEntity>
    suspend fun getProduct(productId: String): ProductEntity?
    suspend fun update(productId: String, newQuantity: Int)
    suspend fun insert(product: ProductEntity)
    suspend fun delete(productId: String)
    suspend fun isInCart(productId: String): Boolean
}