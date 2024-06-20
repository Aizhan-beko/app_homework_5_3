package com.geeks.app_5_3.models


import com.google.gson.annotations.SerializedName

data class Info(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: String? = null,
    @SerializedName("pages")
    val pages: Int,
    @SerializedName("prev")
    val prev: String? = null
)