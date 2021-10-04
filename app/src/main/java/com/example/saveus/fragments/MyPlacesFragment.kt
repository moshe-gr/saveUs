package com.example.saveus.fragments

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.saveus.R
import java.util.*

class MyPlacesFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_places, container, false)
        val myPlacesButton = view.findViewById<TextView>(R.id.my_places_button)
        val onMapButton = view.findViewById<TextView>(R.id.on_map_button)
        val fromDateText = view.findViewById<TextView>(R.id.from_date_text)
        val toDateText = view.findViewById<TextView>(R.id.to_date_text)
        val fromDateButton = view.findViewById<LinearLayout>(R.id.from_date_button)
        val toDateButton = view.findViewById<LinearLayout>(R.id.to_date_button)
        val day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        val month = Calendar.getInstance().get(Calendar.MONTH)
        val year = Calendar.getInstance().get(Calendar.YEAR)

        myPlacesButton.setOnClickListener {
            it.setBackgroundResource(R.color.turquoise)
            onMapButton.setBackgroundResource(R.color.turquoise_55)
        }
        onMapButton.setOnClickListener {
            it.setBackgroundResource(R.color.turquoise)
            myPlacesButton.setBackgroundResource(R.color.turquoise_55)
        }

        fromDateText.text = dateToShow(day, month, year)
        toDateText.text = dateToShow(day, month, year)

        datePickerButton(view.context, fromDateButton, fromDateText)
        datePickerButton(view.context, toDateButton, toDateText)

        return view
    }

    private fun datePickerButton(context: Context, view: View, textView: TextView) {
        val myCalendar = Calendar.getInstance()
        val datePicker = DatePickerDialog(
                context,
                { _, year, monthOfYear, dayOfMonth ->
                    textView.text = dateToShow(dayOfMonth, monthOfYear, year)  },
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            )

        datePicker.datePicker.maxDate = System.currentTimeMillis()

        view.setOnClickListener {
            datePicker.show()
        }
    }

    private fun dateToShow(day: Int, month: Int, year: Int) = "$day." + (month+1) + ".$year"

    companion object {
        @JvmStatic
        fun newInstance() =
            MyPlacesFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}