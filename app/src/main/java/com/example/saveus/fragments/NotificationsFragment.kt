package com.example.saveus.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.saveus.*
import java.text.NumberFormat

class NotificationsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notifications, container, false)
        val sharedPreferences =
            context?.getSharedPreferences("sharedPrefs", AppCompatActivity.MODE_PRIVATE)
        context?.let { DataUtil.fetchData(it) }
        val totalDead = sharedPreferences?.getInt(numDead, -1)
        val totalRecover = sharedPreferences?.getInt(numRecover, -1)
        val totalHospital = sharedPreferences?.getInt(numHospital, -1)
        if (totalDead != null && totalDead >= 0) {
            view.findViewById<TextView>(R.id.num_dead).text =
                NumberFormat.getInstance().format(totalDead)
        }
        if (totalRecover != null && totalRecover >= 0) {
            view.findViewById<TextView>(R.id.num_recover).text =
                NumberFormat.getInstance().format(totalRecover)
        }
        if (totalHospital != null && totalHospital >= 0) {
            view.findViewById<TextView>(R.id.num_hospital).text =
                NumberFormat.getInstance().format(totalHospital)
        }
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = NotificationsFragment()
    }
}