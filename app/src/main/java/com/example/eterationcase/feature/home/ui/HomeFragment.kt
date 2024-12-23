package com.example.eterationcase.feature.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.eterationcase.R
import com.example.eterationcase.databinding.FragmentHomeBinding
import com.example.eterationcase.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val activityViewModel: MainViewModel by activityViewModels()
    private val viewModel: HomeViewModel by hiltNavGraphViewModels(R.id.nav_main)
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: CarAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.selectFilterButton.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.homeToFilter())
        }

        setupLayout()
        setupObservableFields()
    }

    private fun setupLayout() {
        binding.searchEditText.doAfterTextChanged {
            viewModel.setQuery(it.toString())
        }

        adapter = CarAdapter(
            onItemClick = { car ->
                findNavController().navigate(
                    HomeFragmentDirections.homeToDetail(car.id)
                )
            },
            onAddToCartClick = { car ->
                viewModel.insertProduct(car)
                lifecycleScope.launch {
                    delay(200)
                    activityViewModel.updateCartSize()
                }
            },
            onFavoriteClick = {
                viewModel.onFavouriteClick(it)
            }
        )

        binding.recyclerView.adapter = adapter
    }

    private fun setupObservableFields() {
        viewModel.filteredCars.observe(viewLifecycleOwner) { cars ->
            adapter.submitList(cars.toList())
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateFavourites()
    }
}