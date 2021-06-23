package com.enigmacamp.myapplication.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.enigmacamp.myapplication.BaseTest
import com.enigmacamp.myapplication.data.local.Shopping
import com.enigmacamp.myapplication.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class ShoppingDaoTest : BaseTest() {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    override fun isMockServerEnabled() = false
    override fun isMockDatabaseEnabled() = true


    @Test
    fun insertShoppingItem(): Unit = runBlocking {
        database?.let {
            val shoppingItem = Shopping("name", 1, 1f, "url", id = 1)
            it.shoppingDao().insertShoppingItem(shoppingItem)

            val allShoppingItems = it.shoppingDao().observeAllShoppingItems().getOrAwaitValue()

            assertThat(allShoppingItems).contains(shoppingItem)
        }

    }

    @Test
    fun deleteShoppingItem(): Unit = runBlocking {
        database?.let {
            val shoppingItem = Shopping("name", 1, 1f, "url", id = 1)
            it.shoppingDao().insertShoppingItem(shoppingItem)
            it.shoppingDao().deleteShoppingItem(shoppingItem)

            val allShoppingItems = it.shoppingDao().observeAllShoppingItems().getOrAwaitValue()

            assertThat(allShoppingItems).doesNotContain(shoppingItem)
        }

    }

    @Test
    fun observeTotalPriceSum(): Unit = runBlocking {
        database?.let {
            val shoppingItem1 = Shopping("name", 2, 10f, "url", id = 1)
            val shoppingItem2 = Shopping("name", 4, 5.5f, "url", id = 2)
            val shoppingItem3 = Shopping("name", 0, 100f, "url", id = 3)
            it.shoppingDao().insertShoppingItem(shoppingItem1)
            it.shoppingDao().insertShoppingItem(shoppingItem2)
            it.shoppingDao().insertShoppingItem(shoppingItem3)

            val totalPriceSum = it.shoppingDao().observeTotalPrice().getOrAwaitValue()

            assertThat(totalPriceSum).isEqualTo(2 * 10f + 4 * 5.5f)
        }
    }
}













