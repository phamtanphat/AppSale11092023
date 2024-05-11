package com.example.appsale11092023.common

object AppInterface {
    interface OnListenResponse<T> {
        fun onSuccess(data: T)
        fun onFail(message: String)
    }
}