package com.example.saveus.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.saveus.R
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.saveus.DateTimeConverter
import com.example.saveus.SavePlace
import com.example.saveus.SavedPlacesViewModel
import com.google.gson.Gson
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*

private const val SAVED_PLACE = "savedPlace"

class EditSavedPlaceFragment : Fragment(), DateTimeConverter {

    private lateinit var savedPlacesViewModel: SavedPlacesViewModel
    private lateinit var startCalendar: Calendar
    private lateinit var endCalendar: Calendar
    private var savedPlace: SavePlace? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            savedPlace = Gson().fromJson(it.getString(SAVED_PLACE), SavePlace::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_saved_place, container, false)
        val addressView = view.findViewById<TextView>(R.id.edit_place_address)
        val dateView = view.findViewById<TextView>(R.id.edit_place_date)
        val startView = view.findViewById<TextView>(R.id.edit_place_time_start)
        val endView = view.findViewById<TextView>(R.id.edit_place_time_end)
        val lengthView = view.findViewById<TextView>(R.id.edit_place_length)
        val saveButton = view.findViewById<TextView>(R.id.edit_place_save)
        val closeButton = view.findViewById<ImageView>(R.id.edit_place_close)

        startCalendar = getCalendarFromMs(savedPlace?.timeStart!!)
        endCalendar = getCalendarFromMs(savedPlace?.timeEnd!!)

        val datePicker = datePickerButton(view.context, dateView)
        val startTimePicker = timePickerButton(view.context, startCalendar, startView, lengthView)
        val endTimePicker = timePickerButton(view.context, endCalendar, endView, lengthView)

        savedPlacesViewModel = ViewModelProvider(requireActivity()).get(SavedPlacesViewModel::class.java)

        closeButton.setOnClickListener {
            parentFragmentManager.popBackStackImmediate()
        }
        addressView.text = savedPlace?.address
        dateView.text = SimpleDateFormat("dd/MM/yyyy")
            .format(Date(savedPlace?.timeStart!!))
        startView.text = Time(savedPlace?.timeStart!!).toString()
        endView.text = Time(savedPlace?.timeEnd!!).toString()
        lengthView.text = savedPlace?.timeLength
        view.findViewById<TextView>(R.id.edit_place_delete).setOnClickListener {
            savedPlace?.let { it1 -> savedPlacesViewModel.deleteSavedPlace(it1)
            Toast.makeText(view.context, "deleted", Toast.LENGTH_SHORT).show()
                parentFragmentManager.popBackStackImmediate() }
        }
        dateView.setOnClickListener {
            datePicker.show()
        }
        startView.setOnClickListener {
            startTimePicker.show()
        }
        endView.setOnClickListener {
            endTimePicker.show()
        }
        saveButton.setOnClickListener {
            savedPlace!!.timeStart = startCalendar.timeInMillis
            savedPlace!!.timeEnd = endCalendar.timeInMillis
            savedPlace!!.timeLength = lengthView.text.toString()
            savedPlacesViewModel.updateSavedPlace(savedPlace!!)
            Toast.makeText(view.context, "saved", Toast.LENGTH_SHORT).show()
            parentFragmentManager.popBackStackImmediate()
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar?.show()
    }

    override fun dateToShow(day: Int, month: Int, year: Int) = "$day/" + (month + 1) + "/$year"

    private fun datePickerButton(context: Context, textView: TextView): DatePickerDialog {
        val datePickerDialog = DatePickerDialog(
            context,
            { _, year, monthOfYear, dayOfMonth ->
                textView.text = dateToShow(dayOfMonth, monthOfYear, year)
                startCalendar.set(Calendar.YEAR, year)
                startCalendar.set(Calendar.MONTH, monthOfYear)
                startCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            },
            startCalendar.get(Calendar.YEAR),
            startCalendar.get(Calendar.MONTH),
            startCalendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.maxDate = getCurrentTimeInMs()
        return datePickerDialog
    }

    private fun timePickerButton(context: Context, calendar: Calendar, timeView: TextView, lengthView: TextView): TimePickerDialog {
        return TimePickerDialog(
            context,
            R.style.TimePickerTheme,
            { _, hour, minute ->
                timeView.text = Time(hourMinuteSecToMs(hour, minute, 0)).toString()
                calendar.set(Calendar.HOUR_OF_DAY, hour)
                calendar.set(Calendar.MINUTE, minute)
                calendar.set(Calendar.SECOND, 0)
                lengthView.text = Time(endCalendar.timeInMillis - startCalendar.timeInMillis).toString()
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        )
    }

    companion object {
        @JvmStatic
        fun newInstance(savedPlace: SavePlace) =
            EditSavedPlaceFragment().apply {
                arguments = Bundle().apply {
                    putString(SAVED_PLACE, Gson().toJson(savedPlace))
                }
            }
    }
}