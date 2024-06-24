package com.geeks.app_5_3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.geeks.app_5_3.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val adapter by lazy {
        CartoonPagingAdapter()
    }

    private val apiService = RetrofitService.apiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupAdapter()
        fetchData()
    }

    private fun setupAdapter() {
        binding.rvCharacters.adapter = adapter
    }

    private fun fetchData() {
        val pagingFlow: Flow<PagingData<com.geeks.app_5_3.models.Result>> = Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { CartoonPagingSource(apiService) }
        ).flow

        lifecycleScope.launch {
            pagingFlow.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }
}

