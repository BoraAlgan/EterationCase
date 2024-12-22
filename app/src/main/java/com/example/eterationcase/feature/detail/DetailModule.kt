package com.example.eterationcase.feature.detail

import com.example.eterationcase.feature.detail.data.CarDetailRepositoryImp
import com.example.eterationcase.feature.detail.data.CarDetailService
import com.example.eterationcase.feature.detail.domain.CarDetailRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
interface DetailModule {

    @Binds
    fun bindCarDetailRepository(impl: CarDetailRepositoryImp): CarDetailRepository


    companion object {
        @Provides
        fun provideCarDetailService(retrofit: Retrofit): CarDetailService {
            return retrofit.create(CarDetailService::class.java)
        }
    }
}