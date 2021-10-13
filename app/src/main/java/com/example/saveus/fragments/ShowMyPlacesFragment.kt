package com.example.saveus.fragments

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.saveus.*
import java.util.*

class ShowMyPlacesFragment : Fragment(), ShowDate, DateTimeConverter {

    private lateinit var savedPlacesViewModel: SavedPlacesViewModel
    private lateinit var recyclerview: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_show_my_places, container, false)
        recyclerview = view.findViewById(R.id.recyclerview)

        val startDateTextView = view.findViewById<TextView>(R.id.from_date_text)
        val endDateTextView = view.findViewById<TextView>(R.id.to_date_text)
        val startDateButton = view.findViewById<LinearLayout>(R.id.from_date_button)
        val endDateButton = view.findViewById<LinearLayout>(R.id.to_date_button)
        val selectDatesButton = view.findViewById<Button>(R.id.select_dates_button)

        val currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        val currentMonth = Calendar.getInstance().get(Calendar.MONTH)
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)

        val startDatePicker = datePickerButton(view.context, startDateTextView)
        val endDatePicker = datePickerButton(view.context, endDateTextView)

        savedPlacesViewModel = ViewModelProvider(requireActivity()).get(SavedPlacesViewModel::class.java)
        selectDatesButton.setOnClickListener {
            val startCalendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault())
            startCalendar.set(Calendar.YEAR, startDatePicker.datePicker.year)
            startCalendar.set(Calendar.MONTH, startDatePicker.datePicker.month)
            startCalendar.set(Calendar.DAY_OF_MONTH, startDatePicker.datePicker.dayOfMonth)
            val endCalendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault())
            endCalendar.set(Calendar.YEAR, endDatePicker.datePicker.year)
            endCalendar.set(Calendar.MONTH, endDatePicker.datePicker.month)
            endCalendar.set(Calendar.DAY_OF_MONTH, endDatePicker.datePicker.dayOfMonth)
            val dayList: ArrayList<Any> = arrayListOf()
            var endDay = msToDays(addZoneDstOffset(endCalendar.timeInMillis))
            while (endDay >= msToDays(addZoneDstOffset(startCalendar.timeInMillis))){
                dayList.add(endDay--)
            }
            val adapter = MyPlacesAdapter(dayList, this)
            recyclerview.adapter = adapter
            recyclerview.layoutManager = LinearLayoutManager(view.context)
            adapter.notifyDataSetChanged()
        }

        startDateTextView.text = dateToShow(currentDay, currentMonth, currentYear)
        endDateTextView.text = dateToShow(currentDay, currentMonth, currentYear)


        startDateButton.setOnClickListener {
            startDatePicker.show()
        }
        endDateButton.setOnClickListener {
            endDatePicker.show()
        }

        return view
    }

    private fun datePickerButton(context: Context, textView: TextView): DatePickerDialog {
        val myCalendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault())
        val datePickerDialog = DatePickerDialog(
            context,
            { _, year, monthOfYear, dayOfMonth ->
                textView.text = dateToShow(dayOfMonth, monthOfYear, year)
            },
            myCalendar.get(Calendar.YEAR),
            myCalendar.get(Calendar.MONTH),
            myCalendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.maxDate = currentTimeInMs()
        return datePickerDialog
    }

    private fun dateToShow(day: Int, month: Int, year: Int) = "$day." + (month + 1) + ".$year"

    override fun showDate(day: Long, position: Int, dayList: ArrayList<Any>) {
        if(position+1 < dayList.size && dayList[position+1] is SavePlace) {
            while (position+1 < dayList.size && dayList[position+1] is SavePlace){
                dayList.removeAt(position+1)
            }
            val adapter = MyPlacesAdapter(dayList, this)
            recyclerview.adapter = adapter
            recyclerview.layoutManager = LinearLayoutManager(requireContext())
            adapter.notifyDataSetChanged()
        }
        else{
            savedPlacesViewModel.getPlacesByDate(day, addZoneDstOffset(0).toInt())?.observe(viewLifecycleOwner, { savedPlaces ->
                dayList.addAll(position + 1, savedPlaces.sortedBy { savePlace -> savePlace.timeStart })
                val adapter = MyPlacesAdapter(dayList, this)
                recyclerview.adapter = adapter
                recyclerview.layoutManager = LinearLayoutManager(requireContext())
                adapter.notifyDataSetChanged()
                if(savedPlaces.isEmpty()){
                    Toast.makeText(requireContext(), "No events", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ShowMyPlacesFragment()
    }

}