package com.example.eterationcase.feature.favorites

import com.example.eterationcase.feature.favorites.data.FavouriteRepositoryImp
import com.example.eterationcase.feature.favorites.domain.FavouriteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface FavouritesModule {

    @Binds
    fun bindsFavouriteRepository(repository: FavouriteRepositoryImp): FavouriteRepository
}