package com.example.eterationcase.feature.favorites.data

import com.example.eterationcase.feature.favorites.domain.FavouriteDAO
import com.example.eterationcase.feature.favorites.domain.FavouriteRepository
import com.example.eterationcase.feature.home.domain.CarUiItem
import javax.inject.Inject

class FavouriteRepositoryImp @Inject constructor(
    private val favoriteDAO: FavouriteDAO
) : FavouriteRepository {

    override suspend fun addFavorite(favorite: FavouriteEntity) {
        favoriteDAO.insertFavorite(favorite)
    }

    override suspend fun removeFavorite(productId: String) {
        favoriteDAO.deleteFavorite(productId)
    }

    override suspend fun getFavorites(): List<CarUiItem> {
        return favoriteDAO.getAllFavorites().map { it.toDomain() }
    }

    override suspend fun isFavorite(productId: String): Boolean {
        return favoriteDAO.isFavorite(productId)
    }
}
