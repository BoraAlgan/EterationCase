package com.example.eterationcase.feature.home.data

import com.example.eterationcase.feature.home.data.model.Car
import com.example.eterationcase.feature.home.domain.CarRepository
import javax.inject.Inject

class CarRepositoryImp @Inject constructor(
    private val carService: CarService
) : CarRepository {
    override suspend fun getCars(): List<Car> {
        return carService.getCars()
    }
}