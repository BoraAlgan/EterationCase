package com.example.eterationcase.feature.home.data

import com.example.eterationcase.feature.home.data.model.toDomain
import com.example.eterationcase.feature.home.domain.CarRepository
import com.example.eterationcase.feature.home.domain.CarUiItem
import javax.inject.Inject

class CarRepositoryImp @Inject constructor(
    private val carService: CarService
) : CarRepository {
    override suspend fun getCars(): List<CarUiItem> {
        return carService.getCars().map { it.toDomain() }
    }
}