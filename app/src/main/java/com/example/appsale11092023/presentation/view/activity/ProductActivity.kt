package com.example.appsale11092023.presentation.view.activity

import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.appsale11092023.R
import com.example.appsale11092023.data.api.AppResource
import com.example.appsale11092023.presentation.view.adapter.ProductAdapter
import com.example.appsale11092023.presentation.viewmodel.ProductViewModel
import com.example.appsale11092023.util.ToastUtils

class ProductActivity : AppCompatActivity() {

    private lateinit var layoutLoading: LinearLayout
    private lateinit var productRecyclerView: RecyclerView
    private lateinit var productAdapter: ProductAdapter

    private val productViewModel by lazy {
        ViewModelProvider(this)[ProductViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_product)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.product_activity)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // init view
        productRecyclerView = findViewById(R.id.recycler_view_product)
        layoutLoading = findViewById(R.id.layout_loading)
        productAdapter = ProductAdapter(this@ProductActivity)
        productRecyclerView.adapter = productAdapter

        // event
        productViewModel.getProductList()

        // observeData
        productViewModel.getLoading().observe(this, Observer {
            layoutLoading.isVisible = it
        })

        productViewModel.getProductLiveData().observe(this, Observer {
            when(it) {
                is AppResource.Success -> {
                    //show success
                    ToastUtils.showToast(this, "Get product success")
                    it.data?.let { productAdapter.setListProduct(it) }
                    it.data?.forEach {
                        Log.d("ntp", it.toString())
                    }
                }
                is AppResource.Error -> {
                    //show error
                    ToastUtils.showToast(this, it.error)
                }
            }
        })
    }
}