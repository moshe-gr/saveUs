package com.example.saveus.activities

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.saveus.R
import com.example.saveus.fragments.MainFragment
import com.example.saveus.fragments.MyPlacesFragment
import com.example.saveus.fragments.NotificationsFragment
import com.example.saveus.fragments.ProfileFragment
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.my_toolbar))
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        val profileButton = findViewById<ImageView>(R.id.profile_button)
        val profileFragment = ProfileFragment.newInstance()

        if (savedInstanceState == null) {
            makeCurrentFragment(MainFragment.newInstance())
        }

        profileButton.setOnClickListener {
            if (profileFragment.isResumed) {
                supportFragmentManager.popBackStackImmediate()
            } else {
                makeCurrentFragment(profileFragment)
            }
        }

        val navBar = findViewById<NavigationBarView>(R.id.nav_bar)
        navBar.itemIconTintList = null
        navBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.main -> makeCurrentFragment(MainFragment.newInstance())
                R.id.my_places -> makeCurrentFragment(MyPlacesFragment.newInstance())
                R.id.alerts -> makeCurrentFragment(NotificationsFragment())
            }
            true
        }

    }

    private fun makeCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            addToBackStack(fragment.toString())
            commit()
        }
    }

}