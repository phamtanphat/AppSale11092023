package com.example.appsale11092023.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appsale11092023.common.AppInterface
import com.example.appsale11092023.data.api.AppResource
import com.example.appsale11092023.data.api.dto.ProductDTO
import com.example.appsale11092023.data.model.Product
import com.example.appsale11092023.data.repository.ProductRepository
import com.example.appsale11092023.helper.ProductHelper
import kotlinx.coroutines.launch

class ProductViewModel: ViewModel() {
    private var productRepository = ProductRepository
    private val loadingLiveData = MutableLiveData<Boolean>()
    private val productLiveData = MutableLiveData<AppResource<List<Product?>>>()

    fun getLoading(): LiveData<Boolean> = loadingLiveData
    fun getProductLiveData(): LiveData<AppResource<List<Product?>>> = productLiveData

    fun getProductList() {
        loadingLiveData.value = true
        viewModelScope.launch {
            productRepository.getProductList(onListenResponse = object : AppInterface.OnListenResponse<List<ProductDTO>> {
                override fun onFail(message: String) {
                    AppResource.Error(message)
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
}