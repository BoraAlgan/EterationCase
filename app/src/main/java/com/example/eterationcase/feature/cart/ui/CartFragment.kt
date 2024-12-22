package com.example.eterationcase.feature.cart.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eterationcase.databinding.FragmentCartBinding
import com.example.eterationcase.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CartFragment : Fragment() {
    private val activityViewModel: MainViewModel by activityViewModels()
    private val viewModel: CartViewModel by viewModels()
    private lateinit var binding: FragmentCartBinding
    private lateinit var adapter: CartAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        setupObservableFields()

        binding.completeButton.setOnClickListener {
            Toast.makeText(requireContext(), "Purchase Completed", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllProducts()
    }

    private fun setupRecyclerView() {
        adapter = CartAdapter(
            onIncreasedQuantity = { productId, quantity ->
                viewModel.increaseQuantity(productId, quantity)
            },
            onDecreaseQuantity = { productId, quantity ->
                if (quantity > 1) {
                    viewModel.decreaseQuantity(productId, quantity)
                } else {
                    viewModel.deleteProduct(productId)
                }
                lifecycleScope.launch {
                    delay(200)
                    activityViewModel.updateCartSize()
                }
            })

        binding.cartRecyclerView.adapter = adapter
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupObservableFields() {
        viewModel.products.observe(viewLifecycleOwner) { products ->
            adapter.submitList(products)
        }

        viewModel.totalPrice.observe(viewLifecycleOwner) { total ->
            binding.totalTextView.text = "Total: ${"%.2f".format(total)}₺"
        }
    }
}
