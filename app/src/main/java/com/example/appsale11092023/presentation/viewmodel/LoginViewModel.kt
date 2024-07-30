package com.example.appsale11092023.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appsale11092023.Prefs
import com.example.appsale11092023.common.AppInterface
import com.example.appsale11092023.data.api.AppResource
import com.example.appsale11092023.data.api.dto.UserDTO
import com.example.appsale11092023.data.model.User
import com.example.appsale11092023.data.repository.AuthenticationRepository
import com.example.appsale11092023.helper.UserHelper
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val loadingLiveData = MutableLiveData<Boolean>()
    private val userLiveData = MutableLiveData<AppResource<User>>()
    private lateinit var prefs: Prefs
    fun getLoading(): LiveData<Boolean> = loadingLiveData
    fun getUser(): LiveData<AppResource<User>> = userLiveData

    private var repository = AuthenticationRepository

    fun login(email: String, password: String, context: Context) {
        loadingLiveData.value = true
        viewModelScope.launch {
            repository.requestSignIn(
                email = email,
                password = password,
                onListenResponse = object : AppInterface.OnListenResponse<UserDTO> {
                    override fun onSuccess(data: UserDTO?) {
                        val user = UserHelper.convertToUser(data)
                        userLiveData.postValue(AppResource.Success(user))
                        loadingLiveData.postValue(false)
                        prefs = Prefs(context)
                        // Save token
                        if (user != null) {
                            prefs.token = user.token
                        }
                    }

                    override fun onFail(message: String) {
                        userLiveData.postValue(AppResource.Error(message))
                        loadingLiveData.postValue(false)
                    }
                })
        }
    }
}