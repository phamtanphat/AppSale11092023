package com.example.appsale11092023.presentation.view

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.airbnb.lottie.LottieAnimationView
import com.example.appsale11092023.R
import com.example.appsale11092023.common.AppCommon
import com.example.appsale11092023.common.AppSharedPreferences

class SplashActivity : AppCompatActivity() {

    private lateinit var lottieView: LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        lottieView = findViewById(R.id.lottie_animation_view)

        lottieView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationCancel(p0: Animator) {
            }

            override fun onAnimationStart(p0: Animator) {
            }

            override fun onAnimationEnd(p0: Animator) {
                val token = AppSharedPreferences.getString(this@SplashActivity, AppCommon.KEY_TOKEN)
                val intent = Intent(
                    this@SplashActivity,
                    if (token.isBlank()) {
                        // navigate to sign in screen
                        LoginActivity::class.java
                    } else {
                        // navigate to product screen
                        ProductActivity::class.java
                    }
                    )
                startActivity(intent)
                finish()
            }

            override fun onAnimationRepeat(p0: Animator) {
            }
        })
    }
}