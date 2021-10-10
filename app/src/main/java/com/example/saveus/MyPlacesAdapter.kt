package com.example.saveus

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyPlacesAdapter (private val mList: List<SavePlace>) : RecyclerView.Adapter<MyPlacesAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.my_place, parent, false)

                return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

                val itemsViewModel = mList[position]

                holder.timeStartEnd.text = itemsViewModel.timeStart.toString() + " - " + itemsViewModel.timeEnd.toString()
                holder.timeLength.text = itemsViewModel.timeLength
                holder.address.text = itemsViewModel.address
        }

        override fun getItemCount(): Int {
                return mList.size
        }

        class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
                val timeStartEnd: TextView = ItemView.findViewById(R.id.time_start_end)
                val timeLength: TextView = ItemView.findViewById(R.id.time_length)
                val address: TextView = ItemView.findViewById(R.id.address)
        }
}
