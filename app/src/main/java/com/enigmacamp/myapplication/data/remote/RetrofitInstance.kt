package com.enigmacamp.myapplication.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val BASE_URL = "https://pixabay.com"

    private val retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    fun getPixabayApi(): PixabayAPI {
        return retrofit.create(PixabayAPI::class.java)
    }
}