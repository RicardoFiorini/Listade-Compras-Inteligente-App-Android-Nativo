package com.example.listadecomprasinteligente.data.model

import android.os.Parcelable // <--- Faltava este import
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize // <--- Faltava este import

data class MealResponse(
    @SerializedName("meals") val meals: List<MealDto>?
)

data class MealDto(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String,
    val strCategory: String,
    val strInstructions: String,
    // A API é "feia", mapeamos alguns campos manualmente para lista depois
    val strIngredient1: String?, val strMeasure1: String?,
    val strIngredient2: String?, val strMeasure2: String?,
    val strIngredient3: String?, val strMeasure3: String?,
    val strIngredient4: String?, val strMeasure4: String?,
    val strIngredient5: String?, val strMeasure5: String?,
    val strIngredient6: String?, val strMeasure6: String?,
    val strIngredient7: String?, val strMeasure7: String?,
    val strIngredient8: String?, val strMeasure8: String?,
    val strIngredient9: String?, val strMeasure9: String?,
    val strIngredient10: String?, val strMeasure10: String?
) {
    // Função auxiliar de nível Senior: Transformar DTO sujo em Modelo Limpo
    fun toDomainModel(): Recipe {
        val ingredients = mutableListOf<Ingredient>()

        fun addIfValid(ing: String?, measure: String?) {
            if (!ing.isNullOrBlank()) {
                ingredients.add(Ingredient(ing, measure ?: ""))
            }
        }

        addIfValid(strIngredient1, strMeasure1)
        addIfValid(strIngredient2, strMeasure2)
        addIfValid(strIngredient3, strMeasure3)
        addIfValid(strIngredient4, strMeasure4)
        addIfValid(strIngredient5, strMeasure5)
        addIfValid(strIngredient6, strMeasure6)
        addIfValid(strIngredient7, strMeasure7)
        addIfValid(strIngredient8, strMeasure8)
        addIfValid(strIngredient9, strMeasure9)
        addIfValid(strIngredient10, strMeasure10)

        return Recipe(
            id = idMeal,
            name = strMeal,
            imageUrl = strMealThumb,
            category = strCategory,
            instructions = strInstructions,
            ingredients = ingredients
        )
    }
}

// Modelos de Domínio (Usados na UI)
@Parcelize
data class Recipe(
    val id: String,
    val name: String,
    val imageUrl: String,
    val category: String,
    val instructions: String,
    val ingredients: List<Ingredient>
) : Parcelable

@Parcelize
data class Ingredient(
    val name: String,
    val measure: String
) : Parcelable