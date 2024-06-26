package com.example.appsale11092023.data.api.dto

import com.google.gson.annotations.SerializedName

data class AppResponseDTO<T>(
    @SerializedName("data")
    var data: T? = null,
    @SerializedName("message")
    var message: String = ""
)