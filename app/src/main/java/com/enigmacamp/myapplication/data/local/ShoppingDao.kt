package com.enigmacamp.myapplication.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ShoppingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingItem(shoppingItem: Shopping)

    @Delete
    suspend fun deleteShoppingItem(shoppingItem: Shopping)

    @Query("SELECT * FROM shopping_items")
    fun observeAllShoppingItems(): LiveData<List<Shopping>>

    @Query("SELECT SUM(price * amount) FROM shopping_items")
    fun observeTotalPrice(): LiveData<Float>
}