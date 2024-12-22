package com.example.eterationcase.feature.detail.data

import com.example.eterationcase.feature.home.data.model.Car
import retrofit2.http.GET
import retrofit2.http.Path

interface CarDetailService {
    @GET("products/{id}")
    suspend fun getCarDetail(@Path("id") id: String): Car
}