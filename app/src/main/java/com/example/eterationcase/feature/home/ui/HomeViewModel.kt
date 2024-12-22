package com.example.eterationcase.feature.home.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eterationcase.feature.cart.data.ProductEntity
import com.example.eterationcase.feature.cart.domain.ProductRepository
import com.example.eterationcase.feature.favorites.data.FavouriteEntity
import com.example.eterationcase.feature.favorites.domain.FavouriteRepository
import com.example.eterationcase.feature.home.domain.CarRepository
import com.example.eterationcase.feature.home.domain.CarUiItem
import com.example.eterationcase.feature.home.domain.FilterModel
import com.example.eterationcase.feature.home.domain.SortFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val carRepository: CarRepository,
    private val productRepository: ProductRepository,
    private val favoriteRepository: FavouriteRepository
) : ViewModel() {

    private val _filter = MutableLiveData(FilterModel())
    val filter: LiveData<FilterModel> = _filter
    private val _cars = MutableLiveData<List<CarUiItem>>()
    private val _brands = MutableLiveData<List<String>>()
    val brands: LiveData<List<String>> = _brands
    private val _models = MutableLiveData<List<String>>()
    val models: LiveData<List<String>> = _models
    private val _filteredCars = MutableLiveData<List<CarUiItem>>()
    val filteredCars: LiveData<List<CarUiItem>> get() = _filteredCars

    init {
        fetchCars()
    }

    private fun fetchCars() {
        viewModelScope.launch {
            try {
                val response = carRepository.getCars()
                val favourites = favoriteRepository.getFavorites().map { it.id }
                response.forEach { car ->
                    car.isFavourite = favourites.contains(car.id)
                }
                _cars.value = response
                _brands.value = response.map { it.brand }.distinct()
                _models.value = response.map { it.model }.distinct()
                applyFilters()
            } catch (e: Exception) {
                println(e)
                _cars.value = emptyList()
            }
        }
    }

    fun setQuery(query: String) {
        _filter.value = _filter.value?.copy(query = query)
        applyFilters()
    }

    fun setSortFilter(sortFilter: SortFilter) {
        _filter.value = _filter.value?.copy(sortFilter = sortFilter)
        applyFilters()
    }

    fun setBrands(brands: List<String>) {
        _filter.value = _filter.value?.copy(selectedBrands = brands)
        applyFilters()
    }

    fun setModels(models: List<String>) {
        _filter.value = _filter.value?.copy(selectedModels = models)
        applyFilters()
    }

    private fun applyFilters() {
        val filter = _filter.value
        val cars = _cars.value?.toMutableList() ?: mutableListOf()
        val filteredList = cars.filter {
            if (filter != null && filter.query.isNotBlank()) {
                it.name.contains(filter.query)
            } else true
        }.filter {
            if (filter != null && filter.selectedBrands.isNotEmpty()) {
                filter.selectedBrands.contains(it.brand)
            } else true
        }.filter {
            if (filter != null && filter.selectedModels.isNotEmpty()) {
                filter.selectedModels.contains(it.model)
            } else true
        }
        _filteredCars.value = when (filter?.sortFilter) {
            SortFilter.OldToNew -> {
                filteredList.sortedByDescending { it.createdAt }
            }

            SortFilter.NewToOld -> {
                filteredList.sortedBy { it.createdAt }
            }

            SortFilter.PriceHighToLow -> {
                filteredList.sortedByDescending { it.price.toDouble() }
            }

            SortFilter.PriceLowToHigh -> {
                filteredList.sortedBy { it.price.toDouble() }
            }

            else -> filteredList
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
                updateFavoriteStatus(car, !isFavourite)
            } catch (e: Exception) {

            }
        }
    }

    private fun updateFavoriteStatus(car: CarUiItem, isFavourite: Boolean) {
        viewModelScope.launch {
            val cars = _cars.value?.toMutableList() ?: mutableListOf()
            val carIndex = cars.indexOf(car)
            if (carIndex != -1) {
                cars[carIndex] = cars[carIndex].copy(isFavourite = isFavourite)
            }
            _cars.value = cars
        }
    }
}