package com.example.appsale11092023.data.model

import com.google.gson.annotations.SerializedName

data class User(
    var email: String?,
    var name: String?,
    var phone: String?,
    var userGroup: Int?,
    var registerDate: String?,
    var token: String?
)