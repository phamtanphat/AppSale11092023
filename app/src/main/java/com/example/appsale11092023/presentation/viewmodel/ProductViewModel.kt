package com.example.appsale11092023.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appsale11092023.common.AppCommon
import com.example.appsale11092023.common.AppInterface
import com.example.appsale11092023.common.AppSharedPreferences
import com.example.appsale11092023.data.api.AppResource
import com.example.appsale11092023.data.api.dto.CartDTO
import com.example.appsale11092023.data.api.dto.ProductDTO
import com.example.appsale11092023.data.model.Cart
import com.example.appsale11092023.data.model.Product
import com.example.appsale11092023.data.repository.CartRepository
import com.example.appsale11092023.data.repository.ProductRepository
import com.example.appsale11092023.helper.CartHelper
import com.example.appsale11092023.helper.ProductHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ProductViewModel: ViewModel() {
    private var productRepository = ProductRepository
    private var cartRepository = CartRepository
    private val loadingLiveData = MutableLiveData<Boolean>()
    private val productLiveData = MutableLiveData<AppResource<List<Product?>>>()
    private val cartLiveData = MutableLiveData<AppResource<Cart?>>()

    fun getLoading(): LiveData<Boolean> = loadingLiveData
    fun getProductLiveData(): LiveData<AppResource<List<Product?>>> = productLiveData
    fun getCartLiveData(): LiveData<AppResource<Cart?>> = cartLiveData

    fun getProductList() {
        loadingLiveData.value = true
        viewModelScope.launch {
            productRepository.getProductList(onListenResponse = object : AppInterface.OnListenResponse<List<ProductDTO>> {
                override fun onFail(message: String) {
                    productLiveData.postValue(AppResource.Error(message))
                    loadingLiveData.value = false
                }

                override fun onSuccess(data: List<ProductDTO>?) {
                    val listProduct = data?.map {
                        ProductHelper.convertToProduct(it)
                    }
                    listProduct?.let {
                        productLiveData.postValue(AppResource.Success(it))
                    }
                    loadingLiveData.value = false
                }
            })
        }
    }

    fun getCart(context: Context) {
        val token = AppSharedPreferences.getString(context, AppCommon.KEY_TOKEN)
        if (token.isEmpty()) return

        loadingLiveData.value = true
        viewModelScope.launch {
            cartRepository.getCart(token, object : AppInterface.OnListenResponse<CartDTO> {
                override fun onSuccess(data: CartDTO?) {
                    cartLiveData.postValue(AppResource.Success(CartHelper.parseCartDTO(data)))
                    loadingLiveData.value = false
                }

                override fun onFail(message: String) {
                    cartLiveData.postValue(AppResource.Error(message))
                    loadingLiveData.value = false
                }
            })
        }
    }
}