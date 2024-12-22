package com.example.eterationcase.feature.detail.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eterationcase.feature.cart.data.ProductEntity
import com.example.eterationcase.feature.cart.domain.ProductRepository
import com.example.eterationcase.feature.detail.domain.CarDetailRepository
import com.example.eterationcase.feature.favorites.domain.FavouriteRepository
import com.example.eterationcase.feature.home.domain.CarUiItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val carDetailRepository: CarDetailRepository,
    private val productRepository: ProductRepository,
    private val favouriteRepository: FavouriteRepository
) : ViewModel() {

    private val carId = savedStateHandle.get<String>("productID")

    init {
        fetchCarDetails()
    }

    private val _car = MutableLiveData<CarUiItem?>()
    val car: LiveData<CarUiItem?> get() = _car

    private fun fetchCarDetails() {
        carId?.let { id ->
            viewModelScope.launch {
                try {
                    val carDetails = carDetailRepository.getCarDetail(id)
                    val isFavourite = favouriteRepository.isFavorite(id)
                    carDetails.isFavourite = isFavourite
                    _car.postValue(carDetails)
                } catch (e: Exception) {
                    _car.value = null
                }
            }
        }
    }

    fun insertProduct() {
        viewModelScope.launch {
            val car = car.value
            if (car != null) {
                val isInCart = productRepository.isInCart(car.id)
                if (isInCart) {
                    val product = productRepository.getProduct(car.id)
                    if (product != null) {
                        productRepository.update(
                            productId = car.id,
                            newQuantity = product.quantity + 1
                        )
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
    }
}


