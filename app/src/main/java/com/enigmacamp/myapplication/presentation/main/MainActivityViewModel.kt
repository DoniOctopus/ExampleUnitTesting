package com.enigmacamp.myapplication.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enigmacamp.myapplication.data.remote.responses.ImageResponse
import com.enigmacamp.myapplication.data.repository.PixabayRepository
import kotlinx.coroutines.launch

class MainActivityViewModel(private val pixabayRepository: PixabayRepository) : ViewModel() {
    private var _imageLiveData = MutableLiveData<ImageResponse>()
    val imageLiveData: LiveData<ImageResponse>
        get() = _imageLiveData

    fun searchImageByName(name: String) {
        viewModelScope.launch {
            val response = pixabayRepository.searchImage(name)
            response?.let {
                _imageLiveData.postValue(it)
            }

        }
    }
}