package com.example.saveus.activitys

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.saveus.adapters.PageAdapter
import com.example.saveus.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class OnBoardingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        viewPager.adapter = PageAdapter(supportFragmentManager, lifecycle)

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        TabLayoutMediator(tabLayout, viewPager) { _, _ -> }.attach()

        val r = Runnable {
            run{
                getSharedPreferences("sharedPrefs", MODE_PRIVATE)
                    .edit().putBoolean("used", true).apply()
                finish()
            }
        }

        val myHandler = Handler(Looper.myLooper()!!)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }
            override fun onPageSelected(position: Int) {
                if(position == 2) myHandler.postDelayed(r, 3000)
                else myHandler.removeCallbacks(r)
            }

        })

    }
}