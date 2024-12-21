package com.example.eterationcase.di

import com.example.eterationcase.feature.home.data.CarRepositoryImp
import com.example.eterationcase.feature.home.data.CarService
import com.example.eterationcase.feature.home.data.model.Car
import com.example.eterationcase.feature.home.domain.CarRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        val builder = OkHttpClient().newBuilder()

        return builder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .baseUrl("API_URL")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun provideCarService(retrofit: Retrofit): CarService {
        return retrofit.create(CarService::class.java)
    }

    @Singleton
    @Provides
    fun provideCarRespository(carService: CarService): CarRepository {
        return CarRepositoryImp(carService)
    }
}