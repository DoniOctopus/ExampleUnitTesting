package com.enigmacamp.myapplication.data.repository

import com.enigmacamp.myapplication.data.local.Shopping
import com.enigmacamp.myapplication.data.local.ShoppingDao
import com.enigmacamp.myapplication.data.validation.ShoppingValidation

class ShoppingRepository(
    private val shoppingDao: ShoppingDao,
    val validation: ShoppingValidation = ShoppingValidation()
) {
    suspend fun insertShoppingItem(item: Shopping) {
        if (validation.validationShoppingAmount(item.amount)) {
            shoppingDao.insertShoppingItem(item)
        } else {
            throw Exception("No valid data")
        }


    }
}