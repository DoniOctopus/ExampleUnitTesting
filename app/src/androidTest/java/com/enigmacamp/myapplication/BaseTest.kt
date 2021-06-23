package com.enigmacamp.myapplication

import androidx.room.Room.inMemoryDatabaseBuilder
import androidx.test.platform.app.InstrumentationRegistry
import com.enigmacamp.myapplication.data.local.ShoppingDatabase
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.InputStreamReader
import java.net.HttpURLConnection

abstract class BaseTest {
    var database: ShoppingDatabase? = null
    var mockServer: MockWebServer? = null

    abstract fun isMockServerEnabled(): Boolean
    abstract fun isMockDatabaseEnabled(): Boolean

    @Before
    open fun setUp() {
        this.configureMock()
    }

    @After
    open fun tearDown() {
        this.stopMock()
    }

    open fun configureMock() {
        if (isMockServerEnabled()) {
            mockServer = MockWebServer()
        }
        if (isMockDatabaseEnabled()) {
            database = inMemoryDatabaseBuilder(
                InstrumentationRegistry.getInstrumentation().targetContext,
                ShoppingDatabase::class.java
            ).build()
        }
    }

    open fun stopMock() {
        if (isMockServerEnabled()) {
            mockServer?.shutdown()
        }
        if (isMockDatabaseEnabled()) {
            database?.close()
        }
    }

    private fun getJson(path: String): String {
        val reader = InputStreamReader(this.javaClass.classLoader?.getResourceAsStream(path))
        return reader.use { r ->
            r.readText()
        }
    }

    fun getResponse(path: String): MockResponse {
        return MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(getJson(path))
    }

    fun getFailedResponse(): MockResponse {
        return MockResponse().setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
    }

    fun getRetrofit(mockWebServer: MockWebServer): Retrofit {
        return Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
}