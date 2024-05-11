package com.example.appsale11092023.data.api.dto

sealed class AppResource<out T: Any> {
    data class Success<out T: Any>(val data: T?): AppResource<T>()
    data class Error(val error: String): AppResource<Nothing>()
}

