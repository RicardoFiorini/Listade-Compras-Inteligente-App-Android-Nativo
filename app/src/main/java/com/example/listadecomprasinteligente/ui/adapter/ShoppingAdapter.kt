package com.example.listadecomprasinteligente.ui.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.listadecomprasinteligente.data.local.ShoppingItemEntity
import com.example.listadecomprasinteligente.databinding.ItemShoppingBinding

class ShoppingAdapter(
    private val onCheckClick: (ShoppingItemEntity) -> Unit,
    private val onDeleteClick: (ShoppingItemEntity) -> Unit
) : ListAdapter<ShoppingItemEntity, ShoppingAdapter.ShoppingViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingViewHolder {
        val binding = ItemShoppingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShoppingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShoppingViewHolder, position: Int) {
        val item = getItem(position)
        // Lógica para mostrar cabeçalho de categoria apenas se mudou
        val prevItem = if (position > 0) getItem(position - 1) else null
        val showHeader = prevItem == null || prevItem.category != item.category

        holder.bind(item, showHeader)
    }

    inner class ShoppingViewHolder(private val binding: ItemShoppingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ShoppingItemEntity, showHeader: Boolean) {
            binding.tvCategoryHeader.visibility = if (showHeader) View.VISIBLE else View.GONE
            binding.tvCategoryHeader.text = item.category

            binding.tvItemName.text = item.name
            binding.tvMeasure.text = item.measure
            binding.cbItem.isChecked = item.isChecked

            // Risco no texto se marcado
            if (item.isChecked) {
                binding.tvItemName.paintFlags = binding.tvItemName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                binding.tvItemName.paintFlags = binding.tvItemName.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }

            binding.root.setOnClickListener { onCheckClick(item) }
            binding.cbItem.setOnClickListener { onCheckClick(item) }
            binding.btnDelete.setOnClickListener { onDeleteClick(item) }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<ShoppingItemEntity>() {
        override fun areItemsTheSame(oldItem: ShoppingItemEntity, newItem: ShoppingItemEntity) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: ShoppingItemEntity, newItem: ShoppingItemEntity) = oldItem == newItem
    }
}