package com.enigmacamp.myapplication.data.validation

class ShoppingValidation {
    fun validationShoppingAmount(amount: Int): Boolean {
        return amount > 0
    }
}