package com.example.eterationcase.feature.cart.data

import com.example.eterationcase.feature.cart.domain.ProductDAO
import com.example.eterationcase.feature.cart.domain.ProductRepository
import javax.inject.Inject

class ProductRepositoryImp @Inject constructor(
    private val productDAO: ProductDAO
) : ProductRepository {

    override suspend fun getAll(): List<ProductEntity> {
        return productDAO.getAll()
    }

    override suspend fun getProduct(productId: String): ProductEntity? {
        return productDAO.getProduct(productId)
    }

    override suspend fun update(productId: String, newQuantity: Int) {
        return productDAO.update(productId, newQuantity)
    }

    override suspend fun insert(product: ProductEntity) {
        return productDAO.insert(product)
    }

    override suspend fun delete(productId: String) {
        return productDAO.delete(productId)
    }

    override suspend fun isInCart(productId: String): Boolean {
        return productDAO.isInCart(productId)
    }
}