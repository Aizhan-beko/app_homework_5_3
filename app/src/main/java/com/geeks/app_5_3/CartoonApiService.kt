package com.geeks.app_5_3

import com.geeks.app_5_3.models.BaseResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
interface CartoonApiService {
    @GET("character")
    fun fetchCharacters(@Query("page") currentPage: Int): Call<BaseResponse>

    @POST("character")
    fun createCharacter(@Body character: Character): Call<Character>
}