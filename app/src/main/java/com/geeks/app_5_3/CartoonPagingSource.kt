package com.geeks.app_5_3

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.geeks.app_5_3.models.BaseResponse
import com.geeks.app_5_3.models.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

private const val START_INDEX = 1

class CartoonPagingSource(private val api: CartoonApiService) : PagingSource<Int, Character>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        return try {
            val current = params.key ?: START_INDEX
            val previousKey = if (current == START_INDEX) null else current - 1

            val response = suspendCoroutine<List<Character>> { continuation ->
                api.fetchCharacters(current).enqueue(object : Callback<BaseResponse> {
                    override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                        val characters = response.body()?.results ?: emptyList()
                        continuation.resume(characters)
                    }

                    override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                        continuation.resume(emptyList())
                    }
                })
            }

            LoadResult.Page(
                data = response,
                prevKey = previousKey,
                nextKey = current + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}

private fun Any.resume(value: List<Result>) {
}

