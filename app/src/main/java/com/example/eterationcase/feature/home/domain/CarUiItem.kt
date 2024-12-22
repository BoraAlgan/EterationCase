package com.example.eterationcase.feature.home.domain

import java.time.LocalDateTime

data class CarUiItem(
    val id: String,
    val name: String,
    val description: String,
    val image: String,
    val price: String,
    val brand: String = "",
    val model: String = "",
    val createdAt: LocalDateTime = LocalDateTime.now(),
    var isFavourite: Boolean = false
)