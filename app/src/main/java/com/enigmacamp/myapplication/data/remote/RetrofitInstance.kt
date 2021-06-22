package com.enigmacamp.myapplication.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
         val BASE_URL = "https://pixabay.com"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    val api: PixabayAPI by lazy {
        retrofit.create(PixabayAPI::class.java)
    }
}