package com.example.listadecomprasinteligente

import android.app.Application
import com.example.listadecomprasinteligente.data.local.AppDatabase
import com.example.listadecomprasinteligente.data.remote.MealApiService
import com.example.listadecomprasinteligente.data.repository.SmartRepository

class ShoppingApp : Application() {
    // Singleton manual para injeção de dependência simples
    val database by lazy { AppDatabase.getDatabase(this) }
    val apiService by lazy { MealApiService.create() }
    val repository by lazy { SmartRepository(apiService, database.shoppingDao()) }
}