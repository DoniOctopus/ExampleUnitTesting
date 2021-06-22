package com.enigmacamp.myapplication.presentation.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.enigmacamp.myapplication.R
import com.enigmacamp.myapplication.data.remote.RetrofitInstance
import com.enigmacamp.myapplication.data.repository.PixabayRepository


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewModel()
        subscribe()
        viewModel.searchImageByName("Flower")
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val repository = PixabayRepository(RetrofitInstance.getPixabayApi())
                return MainActivityViewModel(repository) as T
            }
        }).get(MainActivityViewModel::class.java)
    }

    private fun subscribe() {
        viewModel.imageLiveData.observe(this, {
            Log.d("MainActivity", "subscribe: ${it}")
        })
    }
}