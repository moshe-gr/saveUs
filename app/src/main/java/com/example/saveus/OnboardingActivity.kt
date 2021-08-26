package com.example.saveus

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class OnboardingActivity : AppCompatActivity(), OnBoarding {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        viewPager.adapter = PageAdapter(supportFragmentManager, this)

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        tabLayout.setupWithViewPager(viewPager)

        val loginIntent = Intent(this, LoginActivity::class.java)

        val moveOn = Handler(Looper.myLooper()!!).postDelayed({
            startActivity(loginIntent)
        }, 3000)

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }
            override fun onPageSelected(position: Int) {
                if(position == 2) {
                    moveOn
                }
                else{
//                  Handler().removeCallbacks(moveOn)
                }
            }

        })

    }

    override fun used(position: Int) {
        val shared_prefs = "sharedPrefs"
        val sharedPreferences = getSharedPreferences(shared_prefs, MODE_PRIVATE)
        var used = "used"
        Toast.makeText(this, "number $position", Toast.LENGTH_SHORT).show()
        sharedPreferences.edit().putBoolean(used, true).apply()
    }
}

