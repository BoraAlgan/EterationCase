package com.example.eterationcase.feature.home.data

import com.example.eterationcase.feature.home.data.model.Car
import retrofit2.http.GET

interface CarService {
    @GET("products")
    suspend fun getCars(): List<Car>
}