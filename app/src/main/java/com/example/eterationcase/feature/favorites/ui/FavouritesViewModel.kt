package com.example.eterationcase.feature.favorites.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eterationcase.feature.cart.data.ProductEntity
import com.example.eterationcase.feature.cart.domain.ProductRepository
import com.example.eterationcase.feature.favorites.data.FavouriteEntity
import com.example.eterationcase.feature.favorites.domain.FavouriteRepository
import com.example.eterationcase.feature.home.domain.CarUiItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val favoriteRepository: FavouriteRepository,
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _favorites = MutableLiveData<List<CarUiItem>>()
    val favorites: LiveData<List<CarUiItem>> get() = _favorites

    fun fetchFavourites() {
        viewModelScope.launch {
            _favorites.value = favoriteRepository.getFavorites()
        }
    }

    fun insertProduct(car: CarUiItem) {
        viewModelScope.launch {
            val isInCart = productRepository.isInCart(car.id)
            if (isInCart) {
                val product = productRepository.getProduct(car.id)
                if (product != null) {
                    productRepository.update(productId = car.id, newQuantity = product.quantity + 1)
                }
            } else {
                val entity = ProductEntity(
                    productId = car.id,
                    name = car.name,
                    price = car.price.toDouble(),
                    quantity = 1
                )
                productRepository.insert(entity)
            }
        }
    }

    fun onFavouriteClick(car: CarUiItem) {
        viewModelScope.launch {
            try {
                val isFavourite = favoriteRepository.isFavorite(car.id)
                if (!isFavourite) {
                    favoriteRepository.addFavorite(
                        FavouriteEntity(
                            productId = car.id,
                            name = car.name,
                            price = car.price.toDouble(),
                            image = car.image
                        )
                    )
                } else {
                    favoriteRepository.removeFavorite(car.id)
                }
                fetchFavourites()
            } catch (e: Exception) {

            }
        }
    }
}