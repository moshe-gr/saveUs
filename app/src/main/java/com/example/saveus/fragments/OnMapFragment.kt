package com.example.saveus.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.saveus.R
import com.example.saveus.SavedPlacesViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class OnMapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var savedPlacesViewModel: SavedPlacesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_on_map, container, false)
        savedPlacesViewModel = ViewModelProvider(requireActivity()).get(SavedPlacesViewModel::class.java)
        val mapFragment = childFragmentManager.findFragmentById(R.id.show_on_map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        return view
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        savedPlacesViewModel.getPlaces()?.observe(viewLifecycleOwner, { savedPlaces ->
            for (savedPlace in savedPlaces) {
                googleMap?.addMarker(MarkerOptions()
                    .position(LatLng(savedPlace.latitude!!, savedPlace.longitude!!)))
            }
        })
        map = googleMap ?: return
    }

    companion object {
        @JvmStatic
        fun newInstance() = OnMapFragment()
    }
}