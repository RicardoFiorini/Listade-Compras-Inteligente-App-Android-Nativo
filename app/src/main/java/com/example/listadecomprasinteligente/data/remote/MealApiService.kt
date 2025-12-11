package com.example.listadecomprasinteligente.data.remote

import com.example.listadecomprasinteligente.data.model.MealResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApiService {
    // Busca por nome
    @GET("search.php")
    suspend fun searchMeals(@Query("s") query: String): MealResponse

    companion object {
        private const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

        fun create(): MealApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MealApiService::class.java)
        }
    }
}