package com.example.eterationcase.feature.home.data.model

import com.example.eterationcase.feature.home.domain.CarUiItem
import kotlinx.serialization.Serializable
import java.time.ZoneId
import java.time.ZonedDateTime

@Serializable
data class Car(
    val id: String,
    val createdAt: String,
    val name: String,
    val image: String,
    val price: String,
    val description: String,
    val model: String,
    val brand: String,
)

fun Car.toDomain() = CarUiItem(
    id = id,
    name = name,
    image = image,
    price = price,
    brand = brand,
    model = model,
    createdAt = ZonedDateTime.parse(createdAt).withZoneSameInstant(ZoneId.systemDefault())
        .toLocalDateTime(),
    description = description
)