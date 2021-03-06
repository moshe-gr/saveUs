package com.example.saveus.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.saveus.R
import com.example.saveus.SavePlace
import com.example.saveus.fragments.EditSavedPlaceFragment
import com.example.saveus.interfaces.DateTimeConverter
import com.example.saveus.interfaces.ReplaceMyFragment
import com.example.saveus.interfaces.ShowDateListener
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*

class MyPlacesAdapter (private val finalList: ArrayList<Any>, private val showDateListener: ShowDateListener, private val replaceMyFragment: ReplaceMyFragment) : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    DateTimeConverter {

        override fun getItemViewType(position: Int): Int {
                if(finalList[position] is Long){
                        return 1
                }
                return 2
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                if(viewType == 1){
                        val view = LayoutInflater.from(parent.context).inflate(R.layout.dates_layout, parent, false)
                        return DateViewHolder(view)
                }
                val view = LayoutInflater.from(parent.context).inflate(R.layout.my_place, parent, false)
                return SavedPlaceViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                var itemsViewModel = finalList[position]
                if(holder is DateViewHolder){
                        itemsViewModel = itemsViewModel as Long
                        holder.showDay.setOnClickListener {
                                showDateListener.showDate(itemsViewModel as Long, finalList)
                        }
                        holder.dateHeader.text = SimpleDateFormat("dd/MM/yyyy").format(Date(daysToMs(itemsViewModel)))
                        if(msToDays(addZoneDstOffset(getCurrentTimeInMs())) == itemsViewModel){
                                holder.dayHeader.text = "????????"
                        }
                        else if(msToDays(addZoneDstOffset(getCurrentTimeInMs())) == itemsViewModel + 1){
                                holder.dayHeader.text = "??????????"
                        }
                        else{
                                holder.dayHeader.text = when(((itemsViewModel % 7)).toInt()){
                                        0 -> "?????? ??"
                                        1 -> "?????? ??"
                                        2 -> "??????"
                                        3 -> "?????? ??"
                                        4 -> "?????? ??"
                                        5 -> "?????? ??"
                                        6 -> "?????? ??"
                                        else -> ""
                                }
                        }
                }
                else if(holder is SavedPlaceViewHolder){
                        itemsViewModel = itemsViewModel as SavePlace
                        holder.timeStartEnd.text = Time(itemsViewModel.timeStart!!).toString() + " - " + Time(itemsViewModel.timeEnd!!).toString()
                        holder.timeLength.text = itemsViewModel.timeLength
                        holder.address.text = itemsViewModel.address
                        holder.myPlace.setOnClickListener {
                                replaceMyFragment.replaceFragment(EditSavedPlaceFragment.newInstance(itemsViewModel))
                        }
                }
        }

        override fun getItemCount(): Int {
                return finalList.size
        }

        class DateViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
                val showDay: RelativeLayout = ItemView.findViewById(R.id.show_day)
                val dateHeader: TextView = ItemView.findViewById(R.id.date_header)
                val dayHeader: TextView = ItemView.findViewById(R.id.day_header)
        }
        class SavedPlaceViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
                val timeStartEnd: TextView = ItemView.findViewById(R.id.time_start_end)
                val timeLength: TextView = ItemView.findViewById(R.id.time_length)
                val address: TextView = ItemView.findViewById(R.id.address)
                val myPlace: LinearLayout = ItemView.findViewById(R.id.my_place)
        }
}
