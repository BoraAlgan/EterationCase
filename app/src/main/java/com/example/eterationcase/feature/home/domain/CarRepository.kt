package com.example.eterationcase.feature.home.domain

interface CarRepository {
    suspend fun getCars(): List<CarUiItem>
}