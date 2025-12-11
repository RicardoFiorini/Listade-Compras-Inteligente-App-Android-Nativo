
package com.example.listadecomprasinteligente.ui.adapter



import android.view.LayoutInflater

import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil

import androidx.recyclerview.widget.ListAdapter

import androidx.recyclerview.widget.RecyclerView

import coil.load

import com.example.listadecomprasinteligente.databinding.ItemRecipeBinding

import com.example.listadecomprasinteligente.data.model.Recipe



// Mudança aqui: renomeamos para onRecipeClick

class RecipeAdapter(private val onRecipeClick: (Recipe) -> Unit) :

    ListAdapter<Recipe, RecipeAdapter.RecipeViewHolder>(DiffCallback()) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {

        val binding = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return RecipeViewHolder(binding)

    }



    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {

        holder.bind(getItem(position))

    }



    inner class RecipeViewHolder(private val binding: ItemRecipeBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: Recipe) {

            binding.tvRecipeName.text = recipe.name

            binding.tvCategory.text = recipe.category

            binding.ivRecipe.load(recipe.imageUrl) {

                crossfade(true)

            }

// O clique agora é no item inteiro (root)

            binding.root.setOnClickListener { onRecipeClick(recipe) }

// O botão foi removido do XML

        }

    }



    class DiffCallback : DiffUtil.ItemCallback<Recipe>() {

        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe) = oldItem == newItem

    }

}