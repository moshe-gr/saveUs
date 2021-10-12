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

class MyPlacesAdapter (private var mList: List<SavePlace>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        private val dateList = ArrayList<Long>()
        private var dateListPosition = 0

        override fun getItemViewType(position: Int): Int {
                if(dateListPosition < dateList.size && mList[position].timeStart!! / 1000 / 60 / 60 / 24 >= dateList[dateListPosition]){
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

                if(holder is ViewHolder1){
                        holder.dateHeader.text = SimpleDateFormat("dd/MM/yyyy").format(Date(dateList[dateListPosition] * 1000 * 60 * 60 * 24))
                        dateListPosition++
                }
                else if(holder is ViewHolder2){
                        val itemsViewModel = mList[position-dateListPosition]
                        holder.timeStartEnd.text = Time(itemsViewModel.timeStart!!).toString() + " - " + Time(itemsViewModel.timeEnd!!).toString()
                        holder.timeLength.text = itemsViewModel.timeLength
                        holder.address.text = itemsViewModel.address
                }
        }

        override fun getItemCount(): Int {
                //mList = mList.sortedBy { savePlace -> savePlace.timeStart }
                println(mList.size)
                println(dateList.size)
                for(i in mList){
                        if (i.timeStart!! / 1000 / 60 / 60 / 24 !in dateList){
                                dateList.add(i.timeStart!! / 1000 / 60 / 60 / 24)
                        }
                }
                return mList.size + dateList.size
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
