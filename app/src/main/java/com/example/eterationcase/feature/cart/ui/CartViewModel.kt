package com.example.eterationcase.feature.cart.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eterationcase.feature.cart.data.ProductEntity
import com.example.eterationcase.feature.cart.domain.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _products = MutableLiveData<List<ProductEntity>>()
    val products: LiveData<List<ProductEntity>> get() = _products

    val totalItems: LiveData<Int> = MediatorLiveData<Int>().apply {
        addSource(_products) { products ->
            value = products.sumOf { it.quantity }
        }
    }

    val totalPrice: LiveData<Double> = MediatorLiveData<Double>().apply {
        addSource(products) { products ->
            value = products.sumOf { it.price * it.quantity }
        }
    }

    init {
        getAllProducts()
    }

    fun getAllProducts() {
        viewModelScope.launch {
            try {
                val response = productRepository.getAll()
                _products.value = response
            } catch (e: Exception) {
                _products.value = emptyList()
            }
        }
    }


    fun deleteProduct(product: String) {
        viewModelScope.launch {
            try {
                productRepository.delete(product)
                getAllProducts()
            } catch (e: Exception) {
            }
        }
    }

    fun decreaseQuantity(productId: String, quantity: Int) {
        viewModelScope.launch {
            try {
                productRepository.update(productId, quantity - 1)
                getAllProducts()
            } catch (e: Exception) {

            }
        }
    }


    fun increaseQuantity(productId: String, quantity: Int) {
        viewModelScope.launch {
            try {
                productRepository.update(productId, quantity + 1)
                getAllProducts()
            } catch (e: Exception) {

            }
        }
    }
}