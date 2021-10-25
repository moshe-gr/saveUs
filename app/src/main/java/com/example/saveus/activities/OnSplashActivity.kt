package com.example.saveus.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.saveus.R

@SuppressLint("CustomSplashScreen")
class OnSplashActivity : AppCompatActivity() {
    private val myHandler = Handler(Looper.myLooper()!!)
    private val myRunnable = Runnable {
        run {
            val sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE)
            if(!sharedPreferences.getBoolean("used", false)) {
                startActivity(Intent(this, OnBoardingActivity::class.java))
            }
            else{
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_un_splash)
    }

    override fun onStart() {
        super.onStart()
        myHandler.postDelayed(myRunnable, 2000)
    }

    override fun onDestroy() {
        super.onDestroy()
        myHandler.removeCallbacks(myRunnable)
    }
}