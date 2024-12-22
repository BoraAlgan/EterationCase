package com.example.eterationcase.feature.favorites.domain

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.eterationcase.feature.favorites.data.FavouriteEntity

@Dao
interface FavouriteDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavouriteEntity)

    @Query("DELETE FROM FavouriteEntity WHERE productId = :productId")
    suspend fun deleteFavorite(productId: String)

    @Query("SELECT * FROM FavouriteEntity")
    suspend fun getAllFavorites(): List<FavouriteEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM FavouriteEntity WHERE productId = :productId)")
    suspend fun isFavorite(productId: String): Boolean
}