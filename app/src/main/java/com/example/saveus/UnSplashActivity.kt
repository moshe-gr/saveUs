package com.example.saveus

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class UnSplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_un_splash)

        val onBoardingIntent = Intent(this, OnBoardingActivity::class.java)
        val loginIntent = Intent(this, LoginActivity::class.java)
        val sharedPrefs = "sharedPrefs"
        val sharedPreferences = getSharedPreferences(sharedPrefs, MODE_PRIVATE)
        val used = "used"

        //sharedPreferences.edit().putBoolean(used, false).apply()

        Handler(Looper.myLooper()!!).postDelayed({
            startActivity(loginIntent)
            if(!sharedPreferences.getBoolean(used, false)) startActivity(onBoardingIntent)
        }, 2000)
    }
}