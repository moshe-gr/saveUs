package com.example.saveus

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.saveus.fragments.OnBoarding1
import com.example.saveus.fragments.OnBoarding2
import com.example.saveus.fragments.OnBoarding3

class PageAdapter(fm:FragmentManager, lc:Lifecycle) : FragmentStateAdapter(fm, lc) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> {
                OnBoarding1.newInstance()
            }
            1 -> {
                OnBoarding2.newInstance()
            }
            2 -> {
                OnBoarding3.newInstance()
            }
            else -> {
                OnBoarding1.newInstance()
            }
        }
    }

}
