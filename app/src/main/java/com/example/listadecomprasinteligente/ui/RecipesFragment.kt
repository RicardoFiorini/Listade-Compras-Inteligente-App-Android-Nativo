package com.example.listadecomprasinteligente.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listadecomprasinteligente.ShoppingApp
import com.example.listadecomprasinteligente.databinding.FragmentRecipesBinding
import com.example.listadecomprasinteligente.data.repository.Resource
import com.example.listadecomprasinteligente.ui.adapter.RecipeAdapter
import com.example.listadecomprasinteligente.ui.viewmodel.MainViewModel
import com.example.listadecomprasinteligente.ui.viewmodel.MainViewModelFactory
import com.example.listadecomprasinteligente.R
import com.example.listadecomprasinteligente.data.model.Recipe // <--- ESTE IMPORT ERA O QUE FALTAVA

class RecipesFragment : Fragment() {

    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!

    // Compartilhar ViewModel com a Activity
    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory((requireActivity().application as ShoppingApp).repository)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = RecipeAdapter { recipe ->
            openRecipeDetail(recipe)
        }

        binding.rvRecipes.layoutManager = LinearLayoutManager(context)
        binding.rvRecipes.adapter = adapter

        // Setup Search
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    viewModel.searchRecipes(query)
                }
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean = false
        })

        // Observer
        viewModel.recipes.observe(viewLifecycleOwner) { resource ->
            binding.progressBar.isVisible = resource is Resource.Loading
            binding.tvError.isVisible = resource is Resource.Error

            when (resource) {
                is Resource.Success -> {
                    adapter.submitList(resource.data)
                    if (resource.data.isNullOrEmpty()) binding.tvError.text = "No recipes found."
                }
                is Resource.Error -> {
                    binding.tvError.text = resource.message
                    binding.tvError.isVisible = true
                }
                else -> {}
            }
        }
    }

    private fun openRecipeDetail(recipe: Recipe) {
        val detailFragment = RecipeDetailFragment.newInstance(recipe)

        parentFragmentManager.commit {
            setReorderingAllowed(true)
            // Adiciona animações de transição (opcional, mas fica "sênior")
            setCustomAnimations(
                android.R.anim.fade_in,
                android.R.anim.fade_out,
                android.R.anim.fade_in,
                android.R.anim.fade_out
            )
            // Substitui o container principal da MainActivity (onde estão as abas) pelo detalhe
            replace(R.id.mainContainer, detailFragment)
            // Adiciona à pilha para que o botão "voltar" do Android funcione
            addToBackStack(null)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}