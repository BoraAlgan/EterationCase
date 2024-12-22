package com.example.eterationcase.feature.home.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.eterationcase.R
import com.example.eterationcase.databinding.ItemCarBinding
import com.example.eterationcase.feature.home.domain.CarUiItem

class CarAdapter(
    val onItemClick: (CarUiItem) -> Unit,
    val onAddToCartClick: (CarUiItem) -> Unit,
    val onFavoriteClick: (CarUiItem) -> Unit
) : ListAdapter<CarUiItem, CarAdapter.CarViewHolder>(CarDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val binding = ItemCarBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CarViewHolder(private val binding: ItemCarBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(car: CarUiItem) {
            binding.apply {
                carName.text = car.name
                carPrice.text = "${car.price} ₺"
                carImage.load(car.image) {
                    placeholder(R.drawable.placeholder)
                    error(R.drawable.placeholder_error)
                }

                root.setOnClickListener {
                    onItemClick.invoke(car)
                }

                addToCartButton.setOnClickListener {
                    onAddToCartClick.invoke(car)
                }
                favoriteIcon.setImageResource(
                    if (car.isFavourite) R.drawable.ic_star_1 else R.drawable.ic_star_2
                )

                favoriteIcon.setOnClickListener {
                    onFavoriteClick.invoke(car)
                }
            }
        }
    }

    class CarDiffCallback : DiffUtil.ItemCallback<CarUiItem>() {
        override fun areItemsTheSame(oldItem: CarUiItem, newItem: CarUiItem): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: CarUiItem, newItem: CarUiItem): Boolean {
            return oldItem.id == newItem.id && oldItem.isFavourite == newItem.isFavourite
        }
    }
}