package com.example.saveus.fragments

import android.app.DatePickerDialog
import android.content.Context
import android.location.Address
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.saveus.R
import com.example.saveus.SavedPlaceViewModel
import java.util.*
import android.location.Geocoder




class MyPlacesFragment : Fragment() {

    private val savedPlaceViewModel by lazy {
        ViewModelProviders.of(requireActivity()).get(SavedPlaceViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_places, container, false)

        val myPlacesButton = view.findViewById<TextView>(R.id.my_places_button)
        val onMapButton = view.findViewById<TextView>(R.id.on_map_button)

        val startDateTextView = view.findViewById<TextView>(R.id.from_date_text)
        val endDateTextView = view.findViewById<TextView>(R.id.to_date_text)
        val startDateButton = view.findViewById<LinearLayout>(R.id.from_date_button)
        val endDateButton = view.findViewById<LinearLayout>(R.id.to_date_button)

        val currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        val currentMonth = Calendar.getInstance().get(Calendar.MONTH)
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)

        myPlacesButton.setOnClickListener {
            it.setBackgroundResource(R.color.turquoise)
            onMapButton.setBackgroundResource(R.color.turquoise_55)
        }
        onMapButton.setOnClickListener {
            it.setBackgroundResource(R.color.turquoise)
            myPlacesButton.setBackgroundResource(R.color.turquoise_55)
        }

        startDateTextView.text = dateToShow(currentDay, currentMonth, currentYear)
        endDateTextView.text = dateToShow(currentDay, currentMonth, currentYear)

        val startDatePicker = datePickerButton(view.context, startDateTextView)
        val endDatePicker = datePickerButton(view.context, endDateTextView)

        startDateTextView.doOnTextChanged { text, _, _, _ ->
            startDateTextView.text = text.toString()
        }

        endDateTextView.doOnTextChanged { text, _, _, _ ->
            endDateTextView.text = text.toString()
        }

        startDateButton.setOnClickListener {
            startDatePicker.show()
        }
        endDateButton.setOnClickListener {
            endDatePicker.show()
        }

        view.findViewById<TextView>(R.id.time_start).text = savedPlaceViewModel.timeStart.toString()
        view.findViewById<TextView>(R.id.time_end).text = savedPlaceViewModel.timeEnd.toString()
        view.findViewById<TextView>(R.id.address).text = savedPlaceViewModel.address
        view.findViewById<TextView>(R.id.time_length).text = savedPlaceViewModel.timeLength

        return view
    }

    private fun datePickerButton(context: Context, textView: TextView): DatePickerDialog {
        val myCalendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
                context,
                { _, year, monthOfYear, dayOfMonth ->
                    textView.text = dateToShow(dayOfMonth, monthOfYear, year) },
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            )
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
        return datePickerDialog
    }

    private fun dateToShow(day: Int, month: Int, year: Int) = "$day." + (month+1) + ".$year"

    companion object {
        @JvmStatic
        fun newInstance() = MyPlacesFragment()
    }
}