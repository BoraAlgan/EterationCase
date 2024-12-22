package com.example.eterationcase.di

import android.content.Context
import androidx.room.Room
import com.example.eterationcase.feature.cart.data.ProductDatabase
import com.example.eterationcase.feature.cart.domain.ProductDAO
import com.example.eterationcase.feature.favorites.domain.FavouriteDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideEntityDatabase(@ApplicationContext context: Context): ProductDatabase {
        return Room.databaseBuilder(
            context,
            ProductDatabase::class.java,
            "product_database"
        ).build()
    }

    @Provides
    fun provideProductDAO(productDatabase: ProductDatabase): ProductDAO {
        return productDatabase.productDAO()
    }

    @Provides
    fun provideFavouritesDAO(productDatabase: ProductDatabase): FavouriteDAO {
        return productDatabase.favouritesDAO()
    }
}