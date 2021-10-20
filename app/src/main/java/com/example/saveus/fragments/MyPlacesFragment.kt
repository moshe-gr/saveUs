package com.example.saveus.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.saveus.R


class MyPlacesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_places, container, false)

        val myPlacesButton = view.findViewById<TextView>(R.id.my_places_button)
        val onMapButton = view.findViewById<TextView>(R.id.on_map_button)

        setCurrentFragment(ShowMyPlacesFragment.newInstance())

        myPlacesButton.setOnClickListener {
            it.setBackgroundResource(R.color.turquoise)
            onMapButton.setBackgroundResource(R.color.turquoise_55)
            setCurrentFragment(ShowMyPlacesFragment.newInstance())
        }

        onMapButton.setOnClickListener {
            it.setBackgroundResource(R.color.turquoise)
            myPlacesButton.setBackgroundResource(R.color.turquoise_55)
            setCurrentFragment(OnMapFragment.newInstance())
        }

        return view
    }

    private fun setCurrentFragment(fragment: Fragment){
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.my_places_fl_wrapper, fragment)
            commit()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MyPlacesFragment()
    }
}