package com.example.eterationcase.feature.home.domain

import com.example.eterationcase.feature.home.data.model.Car

interface CarRepository {
    suspend fun getCars(): List<Car>
}