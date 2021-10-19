package com.example.saveus.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.saveus.R
import com.example.saveus.SavedPlacesViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import java.text.SimpleDateFormat
import java.util.*

class OnMapFragment : Fragment(), OnMapReadyCallback, GoogleMap.InfoWindowAdapter,
    GoogleMap.OnInfoWindowClickListener {

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
        val builder = LatLngBounds.Builder()
        savedPlacesViewModel.getPlaces()?.observe(viewLifecycleOwner, { savedPlaces ->
            for (savedPlace in savedPlaces) {
                googleMap?.addMarker(MarkerOptions()
                    .position(LatLng(savedPlace.latitude!!, savedPlace.longitude!!))
                    .title(savedPlace.address)
                    .icon(BitmapDescriptorFactory.fromBitmap(AppCompatResources.getDrawable(requireContext(), R.drawable.my_place_dot)!!.toBitmap()))
                    .anchor(0.5f, 0.5f)
                    .snippet("${SimpleDateFormat("dd/MM/yyyy").format(Date(savedPlace.timeStart!!))} ${SimpleDateFormat("HH:mm").format(Date(savedPlace.timeStart!!))}-${SimpleDateFormat("HH:mm").format(Date(savedPlace.timeEnd!!))} ${savedPlace.timeLength}"))
                builder.include(LatLng(savedPlace.latitude!!, savedPlace.longitude!!))
            }
            googleMap?.setInfoWindowAdapter(this)
            googleMap?.setOnInfoWindowClickListener(this)
            googleMap?.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 5))
        })
        map = googleMap ?: return
    }

    companion object {
        @JvmStatic
        fun newInstance() = OnMapFragment()
    }

    override fun getInfoWindow(p0: Marker): View? {
        val view = layoutInflater.inflate(R.layout.info_window, null)
        view.findViewById<TextView>(R.id.info_window_address).text = p0.title
        view.findViewById<TextView>(R.id.info_window_date).text = p0.snippet.split(" ")[0]
        view.findViewById<TextView>(R.id.info_window_time).text = p0.snippet.split(" ")[1]
        view.findViewById<TextView>(R.id.info_window_length).text = p0.snippet.split(" ")[2]
        return view
    }

    override fun getInfoContents(p0: Marker): View? {
        return null
    }

    override fun onInfoWindowClick(p0: Marker) {
        p0.hideInfoWindow()
    }
}