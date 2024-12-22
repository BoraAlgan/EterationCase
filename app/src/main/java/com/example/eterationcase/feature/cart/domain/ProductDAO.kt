package com.example.eterationcase.feature.cart.domain

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.eterationcase.feature.cart.data.ProductEntity

@Dao
interface ProductDAO {

    @Insert
    suspend fun insert(product: ProductEntity)

    @Query("DELETE FROM ProductEntity WHERE productId = :productId")
    suspend fun delete(productId: String)

    @Query("UPDATE ProductEntity SET quantity = :newQuantity WHERE productId = :productId")
    suspend fun update(productId: String, newQuantity: Int)

    @Query("SELECT * FROM ProductEntity")
    suspend fun getAll(): List<ProductEntity>

    @Query("SELECT * FROM ProductEntity WHERE productId = :productId")
    suspend fun getProduct(productId: String): ProductEntity?

    @Query("SELECT EXISTS(SELECT 1 FROM ProductEntity WHERE productId = :productId)")
    suspend fun isInCart(productId: String): Boolean
}