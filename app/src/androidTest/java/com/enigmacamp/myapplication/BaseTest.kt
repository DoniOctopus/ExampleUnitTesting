package com.enigmacamp.myapplication

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.InputStreamReader
import java.net.HttpURLConnection

abstract class BaseTest {
    var mockServer: MockWebServer? = null
    abstract fun isMockServerEnabled(): Boolean

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
    }

    open fun stopMock() {
        if (isMockServerEnabled()) {
            mockServer?.shutdown()
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

    fun getRetrofit(mockWebServer: MockWebServer): Retrofit {
        return Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
}