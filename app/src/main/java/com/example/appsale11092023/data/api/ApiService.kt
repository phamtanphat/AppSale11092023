package com.example.appsale11092023.data.api

import com.example.appsale11092023.data.api.dto.AppResponseDTO
import com.example.appsale11092023.data.api.dto.ProductDTO
import com.example.appsale11092023.data.api.dto.UserDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("user/sign-in")
    fun signIn(@Body body: HashMap<String, Any>): Call<AppResponseDTO<UserDTO>>

    @POST("user/sign-up")
    fun signUp(@Body map: HashMap<String, Any>): Call<AppResponseDTO<UserDTO>>

    @GET("product")
    fun getProductListService(): Call<AppResponseDTO<List<ProductDTO>>>
}