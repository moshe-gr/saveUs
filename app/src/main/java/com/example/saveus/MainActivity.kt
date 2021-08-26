package com.example.saveus

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val unplashIntent = Intent(this, UnplashActivity::class.java)
        val onboardingIntent = Intent(this, OnboardingActivity::class.java)
        val loginIntent = Intent(this, LoginActivity::class.java)
        val shared_prefs = "sharedPrefs"
        val sharedPreferences = getSharedPreferences(shared_prefs, MODE_PRIVATE)
        var used = "used"

        startActivity(unplashIntent)

        sharedPreferences.edit().putBoolean(used, false).apply()

        Handler(Looper.myLooper()!!).postDelayed({
            if(!sharedPreferences.getBoolean(used, false)) startActivity(onboardingIntent)
            else startActivity(loginIntent)
        }, 2000)
    }
}