package com.example.saveus

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

@SuppressLint("CustomSplashScreen")
class UnSplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_un_splash)
    }

    override fun onStart() {
        super.onStart()
        val sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE)

        Handler(Looper.myLooper()!!).postDelayed({
            startActivity(Intent(this, OnBoardingActivity::class.java))
            if(!sharedPreferences.getBoolean("used", false)) {
                startActivity(Intent(this, OnBoardingActivity::class.java))
            }
        }, 2000)
    }
}