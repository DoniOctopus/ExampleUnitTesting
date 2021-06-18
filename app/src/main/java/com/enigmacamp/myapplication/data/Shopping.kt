package com.enigmacamp.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_items")
data class Shopping(
        var name: String,
        var amount: Int,
        var price: Float,
        var imageUrl: String,
        @PrimaryKey(autoGenerate = true)
        val id: Int? = null
)