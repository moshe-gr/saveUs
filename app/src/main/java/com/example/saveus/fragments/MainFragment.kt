package com.example.saveus.fragments

import android.Manifest
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
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
import androidx.lifecycle.ViewModelProvider
import com.example.saveus.interfaces.DateTimeConverter
import com.example.saveus.R
import com.example.saveus.SavePlace
import com.example.saveus.SavedPlacesViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import java.util.*

import android.view.animation.AlphaAnimation
import android.view.animation.Animation


class MainFragment : Fragment(), OnMapReadyCallback, DateTimeConverter {

    private lateinit var map: GoogleMap
    private lateinit var savedPlacesViewModel: SavedPlacesViewModel
    private lateinit var savePlace: SavePlace
    private lateinit var startAnimation: ObjectAnimator
    private var start: Boolean? = null

    @SuppressLint("MissingPermission")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val myLocationButton = view.findViewById<ImageView>(R.id.my_location)
        val startStopText = view.findViewById<TextView>(R.id.start_stop_text)
        val startStopTitle = view.findViewById<TextView>(R.id.start_stop_title)
        val chronometer = view.findViewById<Chronometer>(R.id.chronometer)
        val startStopButton = view.findViewById<LinearLayout>(R.id.start_stop_circle)

        startAnimation = ObjectAnimator.ofPropertyValuesHolder(
            startStopButton,
            PropertyValuesHolder.ofFloat("scaleX", 1.3f, 1f),
            PropertyValuesHolder.ofFloat("scaleY", 1.3f, 1f)
        )
        startAnimation.duration = 2000
        startAnimation.repeatMode = ValueAnimator.REVERSE
        startAnimation.repeatCount = ValueAnimator.INFINITE

        val stopAnimation: Animation = AlphaAnimation(1f, 0.6f)
        stopAnimation.repeatCount = Animation.INFINITE
        stopAnimation.repeatMode = Animation.REVERSE
        stopAnimation.duration = 1500

        savedPlacesViewModel =
            ViewModelProvider(requireActivity())[SavedPlacesViewModel::class.java]

        myLocationButton.setOnClickListener {
            enableMyLocation()
        }

        startStopButton.setOnClickListener {
            if (start != null) {
                if (start == true) {
                    it.setBackgroundResource(R.drawable.circle_2)
                    startAnimation.end()
                    startStopButton.startAnimation(stopAnimation)
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

                    savePlace = SavePlace()
                    chronometer.start()
                    myLocationButton.visibility = View.GONE
                    savePlace.timeStart = getCurrentTimeInMs()

                    val geocoder = Geocoder(activity, Locale.getDefault())
                    LocationServices.getFusedLocationProviderClient(requireContext()).lastLocation.addOnSuccessListener { currentLocation ->
                        savePlace.latitude = currentLocation.latitude
                        savePlace.longitude = currentLocation.longitude
                        val addresses: List<Address> = geocoder.getFromLocation(
                            currentLocation.latitude,
                            currentLocation.longitude,
                            1
                        )
                        val city = addresses[0].locality
                        val street = addresses[0].thoroughfare
                        val streetNum = addresses[0].subThoroughfare
                        savePlace.address = "$street $streetNum $city"
                    }

                } else {
                    it.setBackgroundResource(R.drawable.circle_1)
                    stopAnimation.cancel()
                    stopAnimation.reset()
                    startAnimation.start()
                    startStopText.visibility = View.VISIBLE
                    startStopTitle.setText(R.string.circle_1_title)
                    chronometer.visibility = View.GONE
                    myLocationButton.visibility = View.VISIBLE
                    savePlace.timeEnd = getCurrentTimeInMs()
                    savePlace.timeLength = chronometer.text.toString()
                    savedPlacesViewModel.addSavedPlace(savePlace)

                }
                start = !start!!
            }
        }
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        return view
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        enableMyLocation()
    }

    @SuppressLint("MissingPermission")
    private fun enableMyLocation() {
        if (!::map.isInitialized) return
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            map.isMyLocationEnabled = true
            map.isMyLocationEnabled = false
            LocationServices.getFusedLocationProviderClient(requireContext()).lastLocation.addOnSuccessListener {
                map.moveCamera(CameraUpdateFactory.newLatLng(LatLng(it.latitude, it.longitude)))
            }
            map.setMinZoomPreference(15F)
            startAnimation.start()
            start = true
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
        fun newInstance() = MainFragment()
    }

}