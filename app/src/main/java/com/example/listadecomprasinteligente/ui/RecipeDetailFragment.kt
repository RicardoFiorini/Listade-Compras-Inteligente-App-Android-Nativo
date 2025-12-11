package com.example.listadecomprasinteligente.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import coil.load
import com.example.listadecomprasinteligente.ShoppingApp
import com.example.listadecomprasinteligente.data.model.Recipe
import com.example.listadecomprasinteligente.databinding.FragmentRecipeDetailBinding
import com.example.listadecomprasinteligente.ui.viewmodel.MainViewModel
import com.example.listadecomprasinteligente.ui.viewmodel.MainViewModelFactory

class RecipeDetailFragment : Fragment() {

    private var _binding: FragmentRecipeDetailBinding? = null
    private val binding get() = _binding!!

    // Compartilha o mesmo ViewModel da Activity
    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory((requireActivity().application as ShoppingApp).repository)
    }

    private var currentRecipe: Recipe? = null

    companion object {
        private const val ARG_RECIPE = "arg_recipe"

        // Método estático para criar o fragmento com os argumentos corretos
        fun newInstance(recipe: Recipe): RecipeDetailFragment {
            val fragment = RecipeDetailFragment()
            val args = Bundle()
            args.putParcelable(ARG_RECIPE, recipe)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Recupera a receita passada como argumento (suporte a versões novas e antigas do Android)
        currentRecipe = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(ARG_RECIPE, Recipe::class.java)
        } else {
            @Suppress("DEPRECATION")
            arguments?.getParcelable(ARG_RECIPE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configura a Toolbar para ter um botão de voltar (fechar)
        binding.toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }

        currentRecipe?.let { recipe ->
            setupViews(recipe)
        } ?: run {
            Toast.makeText(context, "Error loading recipe details", Toast.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack()
        }
    }

    private fun setupViews(recipe: Recipe) {
        binding.collapsingToolbar.title = recipe.name
        binding.ivDetailImage.load(recipe.imageUrl) { crossfade(true) }
        binding.tvDetailCategory.text = recipe.category
        binding.tvInstructions.text = recipe.instructions

        // Preenche a lista de ingredientes dinamicamente
        binding.ingredientsContainer.removeAllViews()
        recipe.ingredients.forEach { ingredient ->
            val ingTextView = TextView(context).apply {
                text = "• ${ingredient.measure} ${ingredient.name}"
                textSize = 16f
                setPadding(8)
            }
            binding.ingredientsContainer.addView(ingTextView)
        }

        // Ação do Botão Flutuante
        binding.btnAddToShoppingList.setOnClickListener {
            viewModel.addRecipeToShoppingList(recipe)
            Toast.makeText(context, "Ingredients added to your list!", Toast.LENGTH_LONG).show()
            // Opcional: Voltar automaticamente após adicionar
            // parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}