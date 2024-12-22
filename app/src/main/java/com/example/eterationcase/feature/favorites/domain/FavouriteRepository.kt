package com.example.eterationcase.feature.favorites.domain

import com.example.eterationcase.feature.favorites.data.FavouriteEntity
import com.example.eterationcase.feature.home.domain.CarUiItem

interface FavouriteRepository {
    suspend fun addFavorite(favorite: FavouriteEntity)
    suspend fun removeFavorite(productId: String)
    suspend fun getFavorites(): List<CarUiItem>
    suspend fun isFavorite(productId: String): Boolean
}