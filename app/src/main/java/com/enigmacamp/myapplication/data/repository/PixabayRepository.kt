package com.enigmacamp.myapplication.data.repository

import com.enigmacamp.myapplication.data.remote.PixabayAPI
import com.enigmacamp.myapplication.data.remote.responses.ImageResponse

class PixabayRepository(val pixabayAPI: PixabayAPI) {
    suspend fun searchImage(name: String): ImageResponse? {
        val response = pixabayAPI.searchForImage(name)
        if (response.isSuccessful) {
            return response.body()
        } else {
            return null
        }
    }
}