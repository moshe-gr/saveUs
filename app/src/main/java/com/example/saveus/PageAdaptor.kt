package com.example.saveus

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter

class PageAdapter(fm:FragmentManager, lc:Lifecycle) : FragmentStateAdapter(fm, lc) {

    override fun getItemCount(): Int {
        return 3;
    }

    override fun createFragment(position: Int): Fragment {
        when(position) {
            0 -> {
                return OnBoarding1()
            }
            1 -> {
                return OnBoarding2()
            }
            2 -> {
                return  OnBoarding3()
            }
            else -> {
                return OnBoarding1()
            }
        }
    }

//    override fun getPageTitle(position: Int): CharSequence? {
//        when(position) {
//            0 -> {
//                return "Tab 1"
//            }
//            1 -> {
//                return "Tab 2"
//            }
//            2 -> {
//                return "Tab 3"
//            }
//        }
//        return super.getPageTitle(position)
//    }

}
