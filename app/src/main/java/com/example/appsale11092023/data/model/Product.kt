package com.example.appsale11092023.data.model

data class Product(
    val id: String?,
    val name: String?,
    val address: String?,
    val price: Int?,
    val image: String?,
    val quantity: Int?,
    val gallery: List<String>?,
    val dateCreated: String?,
    val dateUpdated: String?,
    val v: Int?,
)