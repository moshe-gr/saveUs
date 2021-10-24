package com.example.saveus.activitys

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.saveus.R

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
            if(!sharedPreferences.getBoolean("used", false)) {
                startActivity(Intent(this, OnBoardingActivity::class.java))
            }
            else{
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }, 2000)
    }
}