package com.example.saveus

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.saveus.fragments.MainFragment
import com.example.saveus.fragments.MyPlacesFragment
import com.example.saveus.fragments.NotificationsFragment
import com.example.saveus.fragments.ProfileFragment
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {

    var show = true
    lateinit var profileButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        profileButton = findViewById(R.id.profile_button)

        if(savedInstanceState == null){
            makeCurrentFragment(MainFragment.newInstance())
        }

        profileButton.setOnClickListener {
            if(show) {
                makeCurrentFragment(ProfileFragment.newInstance())
                profileButton.setImageResource(R.drawable.close_1)
            }
            else{
                supportFragmentManager.popBackStackImmediate()
                profileButton.setImageResource(R.drawable.account)
            }
            show = !show
        }

        val navBar = findViewById<NavigationBarView>(R.id.nav_bar)
        navBar.itemIconTintList = null
        navBar.setOnItemSelectedListener {
            when (it.itemId){
                R.id.main -> makeCurrentFragment(MainFragment.newInstance())
                R.id.my_places -> makeCurrentFragment(MyPlacesFragment.newInstance())
                R.id.alerts -> makeCurrentFragment(NotificationsFragment())
            }
            true
        }

    }

    private fun makeCurrentFragment(fragment: Fragment){
        if(fragment !is ProfileFragment){
            profileButton.setImageResource(R.drawable.account)
            show = true
        }
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            addToBackStack(fragment.toString())
            commit()
        }
    }

}