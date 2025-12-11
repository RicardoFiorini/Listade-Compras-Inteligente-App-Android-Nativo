package com.example.listadecomprasinteligente.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_list")
data class ShoppingItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val measure: String,
    val category: String, // A "Inteligência": Laticínios, Hortifruti, etc.
    val isChecked: Boolean = false,
    val originalRecipeName: String
)