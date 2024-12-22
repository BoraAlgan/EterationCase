package com.example.eterationcase.feature.detail.domain

import com.example.eterationcase.feature.home.domain.CarUiItem

interface CarDetailRepository {
    suspend fun getCarDetail(id: String): CarUiItem
}