package com.example.eterationcase.feature.home

import com.example.eterationcase.feature.home.data.CarRepositoryImp
import com.example.eterationcase.feature.home.data.CarService
import com.example.eterationcase.feature.home.domain.CarRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
interface HomeModule {

    @Binds
    fun bindHomeRepository(impl: CarRepositoryImp): CarRepository


    companion object {
        @Provides
        fun provideCarService(retrofit: Retrofit): CarService {
            return retrofit.create(CarService::class.java)
        }

    }
}