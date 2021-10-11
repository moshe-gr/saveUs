package com.example.saveus

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.saveus.fragments.MainFragment
import com.example.saveus.fragments.MyPlacesFragment
import com.example.saveus.fragments.NotificationsFragment
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {

//    private lateinit var savedPlacesViewModel: SavedPlacesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val db = Room.databaseBuilder(
//            applicationContext,
//            AppDatabase::class.java, "savedPlaces"
//        ).build()
//
//        val placeDao = db.placeDao()
//        savedPlacesViewModel = ViewModelProvider(this).get(SavedPlacesViewModel::class.java)
//        savedPlacesViewModel.savedPlaces = MutableLiveData(placeDao.getAll().toCollection(ArrayList()))

        if(savedInstanceState == null){
            makeCurrentFragment(MainFragment.newInstance())
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
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }
    }

}