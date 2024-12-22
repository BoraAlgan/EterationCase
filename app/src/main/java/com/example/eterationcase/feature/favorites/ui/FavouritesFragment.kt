package com.example.eterationcase.feature.favorites.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eterationcase.databinding.FragmentFavoritesBinding
import com.example.eterationcase.feature.home.ui.CarAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouritesFragment : Fragment() {

    private val viewModel: FavouritesViewModel by viewModels()
    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var adapter: CarAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeFavorites()
    }

    private fun setupRecyclerView() {
        adapter = CarAdapter(
            onItemClick = { car ->
                findNavController().navigate(
                    FavouritesFragmentDirections.favoritesToDetail(car.id)
                )
            },
            onAddToCartClick = { car ->
                viewModel.insertProduct(car)
            },
            onFavoriteClick = {
                viewModel.onFavouriteClick(it)
            }
        )
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observeFavorites() {
        viewModel.favorites.observe(viewLifecycleOwner) { favorites ->
            adapter.submitList(favorites)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchFavourites()
    }
}