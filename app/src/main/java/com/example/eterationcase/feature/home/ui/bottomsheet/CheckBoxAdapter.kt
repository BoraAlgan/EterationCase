package com.example.eterationcase.feature.home.ui.bottomsheet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.eterationcase.databinding.ItemCheckboxBinding

class CheckBoxAdapter(
    private val onItemClick: (String, Boolean) -> Unit
) : RecyclerView.Adapter<CheckBoxAdapter.CheckBoxViewHolder>() {
    private var selectedItems: List<String> = emptyList()
    private var items: List<String> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckBoxViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCheckboxBinding.inflate(inflater, parent, false)
        return CheckBoxViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: CheckBoxViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    fun setData(items: List<String>, selectedItems: List<String>) {
        this.items = items
        this.selectedItems = selectedItems
        notifyDataSetChanged()
    }

    inner class CheckBoxViewHolder(private val binding: ItemCheckboxBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(text: String) {
            binding.checkbox.isChecked = selectedItems.contains(text)
            binding.textview.text = text
            binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
                onItemClick.invoke(text, isChecked)
            }
        }
    }
}