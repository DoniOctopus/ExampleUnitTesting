package com.enigmacamp.myapplication.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Shopping::class],
    version = 1
)
abstract class ShoppingDatabase : RoomDatabase() {

    abstract fun shoppingDao(): ShoppingDao
}