package com.geeks.app_5_3.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.geeks.app_5_3.CartoonPagingSource
import com.geeks.app_5_3.RetrofitService

class CartoonViewModel: ViewModel() {

    private val apiService = RetrofitService.apiService

    val characters = Pager(PagingConfig(pageSize = 20)) {
        CartoonPagingSource(apiService)
    }.flow.cachedIn(viewModelScope)
}