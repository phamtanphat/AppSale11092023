package com.example.appsale11092023.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appsale11092023.common.AppInterface
import com.example.appsale11092023.data.api.AppResource
import com.example.appsale11092023.data.api.dto.UserDTO
import com.example.appsale11092023.data.model.User
import com.example.appsale11092023.data.repository.AuthenticationRepository
import com.example.appsale11092023.helper.UserHelper
import kotlinx.coroutines.launch

class RegisterViewModel: ViewModel() {
    private val loadingLiveData = MutableLiveData<Boolean>()
    private val userLiveData = MutableLiveData<AppResource<User>>()

    fun getLoading(): LiveData<Boolean> = loadingLiveData
    fun getUser(): LiveData<AppResource<User>> = userLiveData

    private var repository = AuthenticationRepository

    fun signUp(
        email: String,
        password: String,
        name: String,
        phone: String,
        address: String
    ) {
        loadingLiveData.value = true
        viewModelScope.launch {
            repository.requestSignUp(
                email = email,
                password = password,
                name = name,
                phone = phone,
                address = address,
                onListenResponse = object : AppInterface.OnListenResponse<UserDTO> {
                    override fun onSuccess(data: UserDTO?) {
                        val user = UserHelper.convertToUser(data)
                        userLiveData.postValue(AppResource.Success(user))
                        loadingLiveData.postValue(false)
                    }

                    override fun onFail(message: String) {
                        userLiveData.postValue(AppResource.Error(message))
                        loadingLiveData.postValue(false)
                    }
                })
        }
    }
}