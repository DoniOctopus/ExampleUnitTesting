package com.enigmacamp.myapplication.data.repository

import com.enigmacamp.myapplication.data.local.Shopping
import com.enigmacamp.myapplication.data.local.ShoppingDao
import com.enigmacamp.myapplication.data.validation.ShoppingValidation
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class ShoppingRepositoryTest {
    @Mock
    lateinit var shoppingDao: ShoppingDao

    @Mock
    lateinit var shoppingValidation: ShoppingValidation

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @get:Rule
    val thrown  = ExpectedException.none()

    @Test
    fun successInsert_whenShoppingItemValidationOk() {
        runBlocking {
            val shoppingRepository = ShoppingRepository(shoppingDao, shoppingValidation)
            val item = Shopping(id = 1, amount = 10, price = 0f, imageUrl = "", name = "test")
            Mockito.`when`(shoppingValidation.validationShoppingAmount(item.amount))
                .thenReturn(true)
            shoppingRepository.insertShoppingItem(item)
            verify(shoppingDao, Mockito.times(1))!!.insertShoppingItem(item)
        }
    }

    @Test
    fun throwException_whenShoppingItemValidationFailed() {
        runBlocking {
            val shoppingRepository = ShoppingRepository(shoppingDao, shoppingValidation)
            val item = Shopping(id = 1, amount = 10, price = 0f, imageUrl = "", name = "test")
            Mockito.`when`(shoppingValidation.validationShoppingAmount(item.amount))
                .thenReturn(false)
            thrown.expect(java.lang.Exception::class.java)
            thrown.expectMessage("No valid data")
            shoppingRepository.insertShoppingItem(item)
        }
    }
}