package com.example.appsale11092023.presentation.view

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.appsale11092023.R
import com.example.appsale11092023.data.api.AppResource
import com.example.appsale11092023.presentation.viewmodel.LoginViewModel
import com.example.appsale11092023.util.ToastUtils
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {

    private lateinit var ediTextEmail: TextInputEditText
    private lateinit var ediTextPassword: TextInputEditText
    private lateinit var buttonSignIn: LinearLayout
    private lateinit var tvRegister: TextView
    private lateinit var layoutLoading: LinearLayout
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

        initViews()
        observerData()
        events()
    }

    private fun events() {
        buttonSignIn.setOnClickListener {
            val email = ediTextEmail.text.toString()
            val password = ediTextPassword.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                ToastUtils.showToast(this, "Input invalid!!!")
                return@setOnClickListener
            }

            viewModel.login(email, password)
        }

    }

    private fun observerData() {
        viewModel.getLoading().observe(this) {
            layoutLoading.isVisible = it
        }

        viewModel.getUser().observe(this) {
            when (it) {
                is AppResource.Success -> {
                    ToastUtils.showToast(this, "Login success!!!")
                }

                is AppResource.Error -> ToastUtils.showToast(this, it.error)
            }
        }
    }

    private fun initViews() {
        ediTextEmail = findViewById(R.id.text_edit_email)
        ediTextPassword = findViewById(R.id.text_edit_password)
        buttonSignIn = findViewById(R.id.button_sign_in)
        tvRegister = findViewById(R.id.text_view_register)
        layoutLoading = findViewById(R.id.layout_loading)
    }
}