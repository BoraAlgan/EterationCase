package com.example.eterationcase.feature.cart.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.eterationcase.feature.cart.domain.ProductDAO
import com.example.eterationcase.feature.favorites.data.FavouriteEntity
import com.example.eterationcase.feature.favorites.domain.FavouriteDAO

@Database(entities = [ProductEntity::class, FavouriteEntity::class], version = 1)
abstract class ProductDatabase() : RoomDatabase() {
    abstract fun productDAO(): ProductDAO
    abstract fun favouritesDAO(): FavouriteDAO
}