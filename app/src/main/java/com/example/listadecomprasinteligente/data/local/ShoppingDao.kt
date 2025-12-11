package com.example.listadecomprasinteligente.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingDao {
    @Query("SELECT * FROM shopping_list ORDER BY category, name")
    fun getAllItems(): Flow<List<ShoppingItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<ShoppingItemEntity>)

    @Update
    suspend fun updateItem(item: ShoppingItemEntity)

    @Query("DELETE FROM shopping_list")
    suspend fun clearList()

    @Delete
    suspend fun deleteItem(item: ShoppingItemEntity)
}