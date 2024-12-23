package com.example.eterationcase.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eterationcase.feature.cart.domain.ProductRepository
import com.example.eterationcase.feature.favorites.domain.FavouriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val productRepository: ProductRepository,
) : ViewModel() {

    private val _cartSize = MutableLiveData(0)
    val cartSize = _cartSize

    init {
        updateCartSize()
    }

    fun updateCartSize() {
        viewModelScope.launch {
            val products = productRepository.getAll()
            _cartSize.value = products.size
        }
    }
}