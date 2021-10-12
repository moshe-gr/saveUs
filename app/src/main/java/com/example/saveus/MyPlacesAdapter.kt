package com.example.saveus

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MyPlacesAdapter (private var finalList: ArrayList<Any>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun getItemViewType(position: Int): Int {
                if(finalList[position] is Long){
                        return 1
                }
                return 2
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                if(viewType == 1){
                        val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.dates_layout, parent, false)

                        return ViewHolder1(view)
                }
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.my_place, parent, false)

                return ViewHolder2(view)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                var itemsViewModel = finalList[position]
                if(holder is ViewHolder1){
                        itemsViewModel = itemsViewModel as Long
                        holder.dateHeader.text = SimpleDateFormat("dd/MM/yyyy").format(Date(itemsViewModel * 1000 * 60 * 60 * 24))
                }
                else if(holder is ViewHolder2){
                        itemsViewModel = itemsViewModel as SavePlace
                        holder.timeStartEnd.text = Time(itemsViewModel.timeStart!!).toString() + " - " + Time(itemsViewModel.timeEnd!!).toString()
                        holder.timeLength.text = itemsViewModel.timeLength
                        holder.address.text = itemsViewModel.address
                }
        }

        override fun getItemCount(): Int {
                println(finalList.size)
                return finalList.size
        }

        class ViewHolder1(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
                val dateHeader: TextView = ItemView.findViewById(R.id.date_header)
                val dayHeader: TextView = ItemView.findViewById(R.id.day_header)
        }
        class ViewHolder2(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
                val timeStartEnd: TextView = ItemView.findViewById(R.id.time_start_end)
                val timeLength: TextView = ItemView.findViewById(R.id.time_length)
                val address: TextView = ItemView.findViewById(R.id.address)
        }
}
