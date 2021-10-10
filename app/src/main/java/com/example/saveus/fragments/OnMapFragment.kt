package com.example.saveus.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.saveus.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

class OnMapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var map: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_on_map, container, false)
        val mapFragment = childFragmentManager.findFragmentById(R.id.show_on_map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        return view
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap ?: return
    }

    companion object {
        @JvmStatic
        fun newInstance() = OnMapFragment()
    }
}