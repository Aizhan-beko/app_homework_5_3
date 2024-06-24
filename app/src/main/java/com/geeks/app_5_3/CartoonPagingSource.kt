package com.geeks.app_5_3

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.geeks.app_5_3.models.BaseResponse
import com.geeks.app_5_3.models.Result
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

private const val START_INDEX = 1

class CartoonPagingSource(private val api: CartoonApiService) : PagingSource<Int, com.geeks.app_5_3.models.Result>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, com.geeks.app_5_3.models.Result> {
        return try {
            val current = params.key ?: START_INDEX
            val previousKey = if (current == START_INDEX) null else current - 1

            val response = suspendCancellableCoroutine<List<com.geeks.app_5_3.models.Result>> { continuation ->
                api.fetchCharacters(current).enqueue(object : Callback<BaseResponse> {
                    override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                        if (response.isSuccessful) {
                            val characters = response.body()?.results ?: emptyList()
                            continuation.resume(characters)
                        } else {
                            continuation.resumeWithException(HttpException(response))
                        }
                    }

                    override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                        continuation.resumeWithException(t)
                    }
                })
            }

            LoadResult.Page(
                data = response,
                prevKey = previousKey,
                nextKey = if (response.isEmpty()) null else current + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, com.geeks.app_5_3.models.Result>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}