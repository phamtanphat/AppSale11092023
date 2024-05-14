package com.example.appsale11092023.presentation.view

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.appsale11092023.R
import com.example.appsale11092023.data.api.AppResource
import com.example.appsale11092023.presentation.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel.getLoading().observe(this) {

        }

        viewModel.getUser().observe(this) {
            when (it) {
                is AppResource.Success -> {
                    findViewById<TextView>(R.id.text_view_name).text = it.data.toString()
                }

                is AppResource.Error -> {
                    findViewById<TextView>(R.id.text_view_name).text = it.error
                }
            }
        }

        viewModel.login("demo1@gmail.com", "1256789")
    }
}