package com.example.eterationcase.feature.cart.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    @ColumnInfo(name = "productId") val productId: String,

    @ColumnInfo(name = "name") val name: String,

    @ColumnInfo(name = "price") val price: Double,

    @ColumnInfo(name = "quantity") val quantity: Int,

    )
