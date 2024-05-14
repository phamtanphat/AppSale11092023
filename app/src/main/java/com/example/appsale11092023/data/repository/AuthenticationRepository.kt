package com.example.appsale11092023.data.repository

import com.example.appsale11092023.common.AppInterface
import com.example.appsale11092023.data.api.ApiService
import com.example.appsale11092023.data.api.RetrofitClient
import com.example.appsale11092023.data.api.dto.AppResponseDTO
import com.example.appsale11092023.data.api.dto.UserDTO
import com.example.appsale11092023.data.enum.StatusCodeType
import com.example.appsale11092023.extension.launchIO
import com.example.appsale11092023.extension.launchOnMain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

object AuthenticationRepository {
    private val apiService: ApiService by lazy {
        RetrofitClient.getApiService()
    }

    fun requestSignIn(
        email: String,
        password: String,
        onListenResponse: AppInterface.OnListenResponse<UserDTO>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val hashMap = HashMap<String, Any>().apply {
                put("email", email)
                put("password", password)
            }

            apiService.signIn(hashMap).enqueue(object : Callback<AppResponseDTO<UserDTO>> {
                override fun onResponse(
                    call: Call<AppResponseDTO<UserDTO>>,
                    response: Response<AppResponseDTO<UserDTO>>
                ) {
                    // Success
                    if (response.isSuccessful && response.body() != null) {
                        onListenResponse.onSuccess(response.body()?.data)
                    } else if (response.errorBody() != null && StatusCodeType.hasCodeError(response.code())) {
                        val json = JSONObject(response.errorBody()?.string() ?: "{}")
                        val errorMessage = json.optString("message")
                        onListenResponse.onFail(errorMessage)
                    }
                }

                override fun onFailure(call: Call<AppResponseDTO<UserDTO>>, t: Throwable) {
                    onListenResponse.onFail(t.message.toString())
                }
            })
        }
    }
}