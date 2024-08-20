package com.example.appsale11092023.data.repository

import com.example.appsale11092023.common.AppInterface
import com.example.appsale11092023.data.api.ApiService
import com.example.appsale11092023.data.api.RetrofitClient
import com.example.appsale11092023.data.api.dto.AppResponseDTO
import com.example.appsale11092023.data.api.dto.CartDTO
import com.example.appsale11092023.data.enum.StatusCodeType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object CartRepository {
    private val apiService: ApiService by lazy {
        RetrofitClient.getApiService()
    }

    fun getCart(
        token: String,
        onListenResponse: AppInterface.OnListenResponse<CartDTO>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            apiService.getCartService(token).enqueue(object : Callback<AppResponseDTO<CartDTO>> {
                override fun onResponse(
                    call: Call<AppResponseDTO<CartDTO>>,
                    response: Response<AppResponseDTO<CartDTO>>
                ) {
                    // Success
                    if ((response.isSuccessful && response.body() != null) || StatusCodeType.ERROR_CODE_500.code == response.code()) {
                        onListenResponse.onSuccess(response.body()?.data)
                    } else if (response.errorBody() != null && StatusCodeType.hasCodeError(response.code())) {
                        val json = JSONObject(response.errorBody()?.string() ?: "{}")
                        val errorMessage = json.optString("message")
                        onListenResponse.onFail(errorMessage)
                    }
                }

                override fun onFailure(call: Call<AppResponseDTO<CartDTO>>, t: Throwable) {
                    onListenResponse.onFail(t.message.toString())
                }
            })
        }
    }

}