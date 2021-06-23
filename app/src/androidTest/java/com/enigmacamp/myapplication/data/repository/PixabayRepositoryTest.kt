package com.enigmacamp.myapplication.data.repository

import com.enigmacamp.myapplication.BaseTest
import com.enigmacamp.myapplication.data.remote.PixabayAPI
import com.google.common.truth.Truth
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking

import org.junit.Assert.*
import org.junit.Test

class PixabayRepositoryTest : BaseTest() {
    override fun isMockServerEnabled() = true
    override fun isMockDatabaseEnabled() = false

    @Test
    fun getJsonData_whenSuccessHitPixabayAPI() {
        runBlocking {
            mockServer?.let {
                val pixabayAPI = getRetrofit(it).create(PixabayAPI::class.java)
                it.enqueue(getResponse("imageList_whenSuccess.json"))

                val pixabayRepository = PixabayRepository(pixabayAPI)
                val response = pixabayRepository.searchImage("flower")
                Truth.assertThat(response!!.hits.size).isEqualTo(2)
            }
        }
    }

    @Test
    fun returnNull_whenFailedPixabayAPI() {
        runBlocking {
            mockServer?.let {
                val pixabayAPI = getRetrofit(it).create(PixabayAPI::class.java)
                it.enqueue(getFailedResponse())
                val pixabayRepository = PixabayRepository(pixabayAPI)
                val response = pixabayRepository.searchImage("flower")
                Truth.assertThat(response).isNull()
            }
        }
    }

}