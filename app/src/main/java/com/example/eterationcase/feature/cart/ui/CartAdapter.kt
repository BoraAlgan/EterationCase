package com.example.eterationcase.feature.cart.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.eterationcase.databinding.ItemProductBinding
import com.example.eterationcase.feature.cart.data.ProductEntity

class CartAdapter(
    val onIncreasedQuantity: (productId: String, newQuantity: Int) -> Unit,
    val onDecreaseQuantity: (productId: String, newQuantity: Int) -> Unit
) : ListAdapter<ProductEntity, CartAdapter.CartViewHolder>(ProductDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.CartViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartAdapter.CartViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)
    }

    inner class CartViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductEntity) {

            binding.apply {
                productName.text = product.name
                productPrice.text = "${product.price} ₺"
                productQuantity.text = product.quantity.toString()

                decreaseButton.setOnClickListener {
                    onDecreaseQuantity.invoke(product.productId, product.quantity)
                }

                increaseButton.setOnClickListener {
                    onIncreasedQuantity.invoke(product.productId, product.quantity)
                }

            }
        }
    }

    class ProductDiffCallback : DiffUtil.ItemCallback<ProductEntity>() {
        override fun areItemsTheSame(oldItem: ProductEntity, newItem: ProductEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductEntity, newItem: ProductEntity): Boolean {
            return oldItem == newItem
        }
    }
}