package com.example.eterationcase.feature.detail.data

import com.example.eterationcase.feature.detail.domain.CarDetailRepository
import com.example.eterationcase.feature.home.data.model.toDomain
import com.example.eterationcase.feature.home.domain.CarUiItem
import javax.inject.Inject

class CarDetailRepositoryImp @Inject constructor(
    private val carDetailService: CarDetailService
) : CarDetailRepository {
    override suspend fun getCarDetail(id: String): CarUiItem {
        return carDetailService.getCarDetail(id).toDomain()
    }
}