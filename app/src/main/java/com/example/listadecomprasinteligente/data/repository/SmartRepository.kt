package com.example.listadecomprasinteligente.data.repository

import com.example.listadecomprasinteligente.data.local.ShoppingDao
import com.example.listadecomprasinteligente.data.local.ShoppingItemEntity
import com.example.listadecomprasinteligente.data.model.Recipe
import com.example.listadecomprasinteligente.data.remote.MealApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

// Classe utilitária para estados de UI
sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T> : Resource<T>()
}

class SmartRepository(
    private val api: MealApiService,
    private val dao: ShoppingDao
) {

    // Busca receitas na API
    fun searchRecipes(query: String): Flow<Resource<List<Recipe>>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.searchMeals(query)
            val domainRecipes = response.meals?.map { it.toDomainModel() } ?: emptyList()
            emit(Resource.Success(domainRecipes))
        } catch (e: IOException) {
            emit(Resource.Error("Erro de conexão. Verifique sua internet."))
        } catch (e: Exception) {
            emit(Resource.Error("Erro desconhecido: ${e.message}"))
        }
    }

    // Pega lista de compras do banco
    val shoppingList: Flow<List<ShoppingItemEntity>> = dao.getAllItems()

    // Lógica Inteligente: Adicionar Receita à Lista com Categorias
    suspend fun addRecipeToShoppingList(recipe: Recipe) {
        val shoppingItems = recipe.ingredients.map { ingredient ->
            ShoppingItemEntity(
                name = ingredient.name,
                measure = ingredient.measure,
                category = categorizeIngredient(ingredient.name), // Inteligência aqui
                originalRecipeName = recipe.name
            )
        }
        dao.insertItems(shoppingItems)
    }

    suspend fun toggleItemCheck(item: ShoppingItemEntity) {
        dao.updateItem(item.copy(isChecked = !item.isChecked))
    }

    suspend fun deleteItem(item: ShoppingItemEntity) {
        dao.deleteItem(item)
    }

    // Heurística simples para categorizar (Poderia ser uma IA real ou mapeamento maior)
    private fun categorizeIngredient(name: String): String {
        val lowerName = name.lowercase()
        return when {
            lowerName.contains("milk") || lowerName.contains("cheese") || lowerName.contains("butter") || lowerName.contains("cream") -> "Laticínios"
            lowerName.contains("chicken") || lowerName.contains("beef") || lowerName.contains("pork") || lowerName.contains("fish") -> "Carnes"
            lowerName.contains("onion") || lowerName.contains("garlic") || lowerName.contains("tomato") || lowerName.contains("carrot") || lowerName.contains("lime") -> "Hortifruti"
            lowerName.contains("sugar") || lowerName.contains("salt") || lowerName.contains("flour") || lowerName.contains("oil") -> "Despensa"
            else -> "Outros"
        }
    }
}