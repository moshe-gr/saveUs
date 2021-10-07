package com.example.saveus.fragments

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.saveus.MyPlacesAdapter
import com.example.saveus.R
import com.example.saveus.SavedPlacesViewModel
import java.util.*


class MyPlacesFragment : Fragment() {

    private lateinit var savedPlacesViewModel: SavedPlacesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_places, container, false)

        val myPlacesButton = view.findViewById<TextView>(R.id.my_places_button)
        val onMapButton = view.findViewById<TextView>(R.id.on_map_button)

        val recyclerview = view.findViewById<RecyclerView>(R.id.recyclerview)

        val startDateTextView = view.findViewById<TextView>(R.id.from_date_text)
        val endDateTextView = view.findViewById<TextView>(R.id.to_date_text)
        val startDateButton = view.findViewById<LinearLayout>(R.id.from_date_button)
        val endDateButton = view.findViewById<LinearLayout>(R.id.to_date_button)

        val currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        val currentMonth = Calendar.getInstance().get(Calendar.MONTH)
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)

        savedPlacesViewModel = ViewModelProvider(requireActivity()).get(SavedPlacesViewModel::class.java)
        savedPlacesViewModel.savedPlaces.observe(viewLifecycleOwner, {savedPlaces->
            val adapter = MyPlacesAdapter(savedPlaces)
            recyclerview.adapter = adapter
            recyclerview.layoutManager = LinearLayoutManager(view.context)
        })

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

        return view
    }

    private fun datePickerButton(context: Context, textView: TextView): DatePickerDialog {
        val myCalendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            context,
            { _, year, monthOfYear, dayOfMonth ->
                textView.text = dateToShow(dayOfMonth, monthOfYear, year)
            },
            myCalendar.get(Calendar.YEAR),
            myCalendar.get(Calendar.MONTH),
            myCalendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
        return datePickerDialog
    }

    private fun dateToShow(day: Int, month: Int, year: Int) = "$day." + (month + 1) + ".$year"

    companion object {
        @JvmStatic
        fun newInstance() = MyPlacesFragment()
    }
}