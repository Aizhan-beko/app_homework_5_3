package com.geeks.app_5_3.models


import com.google.gson.annotations.SerializedName

data class BaseResponse(
    @SerializedName("info")
    val info: Info,
    @SerializedName("results")
    val results: List<Result>
)