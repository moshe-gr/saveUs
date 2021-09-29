package com.example.saveus.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.saveus.R
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng

class MainFragment : Fragment(), OnMapReadyCallback {

    private lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        var start = true
        val startStopText = view.findViewById<TextView>(R.id.start_stop_text)
        val startStopTitle = view.findViewById<TextView>(R.id.start_stop_title)
        val chronometer = view.findViewById<Chronometer>(R.id.chronometer)

        view.findViewById<ImageView>(R.id.my_location).setOnClickListener {
            enableMyLocation()
        }
        view.findViewById<LinearLayout>(R.id.start_stop_circle).setOnClickListener {
            if(start){
                it.setBackgroundResource(R.drawable.circle_2)
                startStopTitle.setText(R.string.circle_2_title)
                startStopText.visibility = View.GONE
                chronometer.visibility = View.VISIBLE
                chronometer.format = "00:%s"
                chronometer.base = SystemClock.elapsedRealtime()
                chronometer.setOnChronometerTickListener { cArg ->
                    val elapsedMillis = SystemClock.elapsedRealtime() - cArg.base
                    if (elapsedMillis > 3600000L) {
                        cArg.format = "0%s"
                    } else {
                        cArg.format = "00:%s"
                    }
                }
                chronometer.start()
            }
            else{
                it.setBackgroundResource(R.drawable.circle_1)
                startStopText.visibility = View.VISIBLE
                startStopTitle.setText(R.string.circle_1_title)
                chronometer.visibility = View.GONE
            }
            start = !start
        }
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        return view
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap ?: return
        enableMyLocation()
    }

    @SuppressLint("MissingPermission")
    private fun enableMyLocation() {
        if (!::map.isInitialized) return
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            LocationServices.getFusedLocationProviderClient(requireContext()).lastLocation.addOnSuccessListener {
                map.moveCamera(CameraUpdateFactory.newLatLng(LatLng(it.latitude, it.longitude)))
            }
            map.setMinZoomPreference(15F)
        } else {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            return
        }
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            enableMyLocation()
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1

        @JvmStatic
        fun newInstance() =
            MainFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

}