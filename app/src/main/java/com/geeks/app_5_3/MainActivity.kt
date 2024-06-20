package com.geeks.app_5_3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.geeks.app_5_3.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.Flow

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val adapter by lazy {
        CartoonPagingAdapter()
    }

    private val apiService = CartoonApiService.create()

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
        val pagingFlow: Flow<PagingData<Character>> = Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { CartoonPagingSource(apiService) }
        ).flow

        val pagingLiveData: LiveData<PagingData<Character>> = pagingFlow.asLiveData()
        pagingLiveData.observe(this) { pagingData ->
            adapter.submitData(lifecycle, pagingData)
        }
    }
}


