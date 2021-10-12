package com.example.saveus

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MyPlacesAdapter (private var finalList: ArrayList<Any>, private var showDate: ShowDate) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
                                showDate.showDate(itemsViewModel as Long, position, finalList)
                        }
                        holder.dateHeader.text = SimpleDateFormat("dd/MM/yyyy").format(Date(itemsViewModel * 1000 * 60 * 60 * 24))
                        if(System.currentTimeMillis() / 1000 / 60 / 60 /24 == itemsViewModel){
                                holder.dayHeader.text = "היום"
                        }
                        else if(System.currentTimeMillis() / 1000 / 60 / 60 /24 == itemsViewModel + 1){
                                holder.dayHeader.text = "אתמול"
                        }
                }
                else if(holder is SavedPlaceViewHolder){
                        itemsViewModel = itemsViewModel as SavePlace
                        holder.timeStartEnd.text = Time(itemsViewModel.timeStart!!).toString() + " - " + Time(itemsViewModel.timeEnd!!).toString()
                        holder.timeLength.text = itemsViewModel.timeLength
                        holder.address.text = itemsViewModel.address
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
        }
}
