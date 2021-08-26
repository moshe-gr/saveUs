package com.example.saveus

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class PageAdapter(fm:FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
        return 3;
    }

    override fun getItem(position: Int): Fragment {
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
