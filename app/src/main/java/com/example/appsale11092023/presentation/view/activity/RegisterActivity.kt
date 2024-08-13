package com.example.appsale11092023.presentation.view.activity

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isGone
import androidx.lifecycle.ViewModelProvider
import com.example.appsale11092023.R
import com.example.appsale11092023.data.api.AppResource
import com.example.appsale11092023.presentation.viewmodel.RegisterViewModel
import com.example.appsale11092023.util.SpannedUtils
import com.example.appsale11092023.util.ToastUtils
import com.google.android.material.textfield.TextInputEditText

class RegisterActivity : AppCompatActivity() {

    private lateinit var ediTextEmail: TextInputEditText
    private lateinit var ediTextPassword: TextInputEditText
    private lateinit var ediTextName: TextInputEditText
    private lateinit var ediTextPhone: TextInputEditText
    private lateinit var ediTextAddress: TextInputEditText
    private lateinit var buttonSignUp: LinearLayout
    private lateinit var tvPopToSignInActivity: TextView
    private lateinit var layoutLoading: LinearLayout

    private val viewModel: RegisterViewModel by lazy {
        ViewModelProvider(this)[RegisterViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        initView()
        observerData()
        event()
    }
    private fun event() {
        buttonSignUp.setOnClickListener {
            val email = ediTextEmail.text.toString()
            val password = ediTextPassword.text.toString()
            val address = ediTextAddress.text.toString()
            val phone = ediTextPhone.text.toString()
            val name = ediTextName.text.toString()

            if (email.isEmpty() || password.isEmpty() || address.isEmpty() || phone.isEmpty() || name.isEmpty()) {
                ToastUtils.showToast(this@RegisterActivity, "Input invalid")
                return@setOnClickListener
            }

            viewModel.signUp(
                email = email,
                password = password,
                name = name,
                phone = phone,
                address = address
            )
        }

        displayTextViewSignIn()
    }

    private fun observerData() {
        viewModel.getLoading().observe(this) {
            layoutLoading.isGone = !it
        }

        viewModel.getUser().observe(this) {
            when (it) {
                is AppResource.Success -> {
                    ToastUtils.showToast(this, "Register success")
                    finish()
                }
                is AppResource.Error -> ToastUtils.showToast(this, it.error)
            }
        }
    }

    private fun initView() {
        ediTextEmail = findViewById(R.id.text_edit_email)
        ediTextPassword = findViewById(R.id.text_edit_password)
        ediTextName = findViewById(R.id.text_edit_name)
        ediTextPhone = findViewById(R.id.text_edit_phone)
        ediTextAddress = findViewById(R.id.text_edit_address)
        tvPopToSignInActivity = findViewById(R.id.text_view_pop_to_login_activity)
        buttonSignUp = findViewById(R.id.button_sign_up)
        layoutLoading = findViewById(R.id.layout_loading)
    }

    private fun displayTextViewSignIn() {
        SpannableStringBuilder().apply {
            append("Don't have an account?")
            append(
                SpannedUtils.setClickColorLink(
                    text = "Signin",
                    context = this@RegisterActivity,
                    onListenClick = { finish() }
                ))
            tvPopToSignInActivity.text = this
            tvPopToSignInActivity.highlightColor = Color.TRANSPARENT
            tvPopToSignInActivity.movementMethod = LinkMovementMethod.getInstance()
        }
    }
}