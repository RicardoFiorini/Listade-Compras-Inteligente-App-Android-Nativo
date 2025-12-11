package com.example.listadecomprasinteligente.ui.viewmodel

import androidx.lifecycle.*
import com.example.listadecomprasinteligente.data.local.ShoppingItemEntity
import com.example.listadecomprasinteligente.data.model.Recipe
import com.example.listadecomprasinteligente.data.repository.Resource
import com.example.listadecomprasinteligente.data.repository.SmartRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: SmartRepository) : ViewModel() {

    // Receitas
    private val _recipes = MutableLiveData<Resource<List<Recipe>>>()
    val recipes: LiveData<Resource<List<Recipe>>> = _recipes

    // Lista de Compras (Flow convertido para LiveData para facilitar na View)
    val shoppingList: LiveData<List<ShoppingItemEntity>> = repository.shoppingList.asLiveData()

    init {
        // Carrega algo inicial
        searchRecipes("chicken")
    }

    fun searchRecipes(query: String) {
        viewModelScope.launch {
            repository.searchRecipes(query).collect {
                _recipes.value = it
            }
        }
    }

    fun addRecipeToShoppingList(recipe: Recipe) {
        viewModelScope.launch {
            repository.addRecipeToShoppingList(recipe)
        }
    }

    fun toggleItem(item: ShoppingItemEntity) {
        viewModelScope.launch {
            repository.toggleItemCheck(item)
        }
    }

    fun deleteItem(item: ShoppingItemEntity) {
        viewModelScope.launch {
            repository.deleteItem(item)
        }
    }
}

// Factory para injetar o Repository
class MainViewModelFactory(private val repository: SmartRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}