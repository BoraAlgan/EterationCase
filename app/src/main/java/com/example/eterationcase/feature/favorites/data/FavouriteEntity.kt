package com.example.eterationcase.feature.favorites.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.eterationcase.feature.home.domain.CarUiItem

@Entity
data class FavouriteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    @ColumnInfo(name = "productId") val productId: String,

    @ColumnInfo(name = "name") val name: String,

    @ColumnInfo(name = "price") val price: Double,

    @ColumnInfo(name = "image") val image: String
)

fun FavouriteEntity.toDomain() = CarUiItem(
    id = productId,
    name = name,
    description = "",
    price = price.toString(),
    image = image,
    isFavourite = true
)