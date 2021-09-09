package com.example.saveus

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navBar = findViewById<NavigationBarView>(R.id.nav_bar)
        navBar.itemIconTintList = null
        navBar.setOnItemSelectedListener {
            when (it.itemId){
                R.id.main -> makeCurrentFragment(MainFragment())
                R.id.my_places -> makeCurrentFragment(MyPlacesFragment())
                R.id.alerts -> makeCurrentFragment(NotificationsFragment())
            }
            true
        }

        makeCurrentFragment(MainFragment())

    }

    private fun makeCurrentFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }
    }

}