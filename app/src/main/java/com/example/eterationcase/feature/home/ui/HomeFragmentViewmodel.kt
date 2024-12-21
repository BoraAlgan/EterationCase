package com.example.eterationcase.feature.home.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eterationcase.feature.home.data.model.Car
import com.example.eterationcase.feature.home.domain.CarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewmodel @Inject constructor(
    private val carRepository: CarRepository
) : ViewModel() {
    private val _cars = MutableLiveData<List<Car>>()
    val cars: LiveData<List<Car>> get() = _cars

    init {
        fetchCars()
    }

    fun fetchCars() {
        viewModelScope.launch {
            try {
                val response = carRepository.getCars()
                _cars.value = response
            } catch (e: Exception) {
                _cars.value = emptyList()
            }
        }
    }
}