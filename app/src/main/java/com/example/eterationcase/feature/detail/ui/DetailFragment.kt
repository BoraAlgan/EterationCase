package com.example.eterationcase.feature.detail.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.eterationcase.R
import com.example.eterationcase.databinding.FragmentDetailBinding
import com.example.eterationcase.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DetailFragment : Fragment() {
    private val activityViewModel: MainViewModel by activityViewModels()
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.car.observe(viewLifecycleOwner) { car ->
            binding.apply {
                carName.text = car?.name
                carPrice.text = "${car?.price} ₺"
                carDescription.text = car!!.description
                carImage.load(car?.image) {
                    placeholder(R.drawable.placeholder)
                    error(R.drawable.placeholder_error)
                }
            }
        }

        binding.addToCartButton.setOnClickListener {
            viewModel.insertProduct()
            lifecycleScope.launch {
                delay(200)
                activityViewModel.updateCartSize()
            }
        }
    }
}

