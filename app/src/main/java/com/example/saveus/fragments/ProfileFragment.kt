package com.example.saveus.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.saveus.PersonalInfo
import com.example.saveus.R
import com.google.gson.Gson
import java.text.SimpleDateFormat

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val json = activity?.getSharedPreferences("sharedPrefs",
            AppCompatActivity.MODE_PRIVATE)?.getString("personalInfo", "")
        val personalInfo = Gson().fromJson(json, PersonalInfo::class.java)

        if(personalInfo.firstName != null && personalInfo.surName != null) {
            view.findViewById<TextView>(R.id.profile_name).text =
                "${personalInfo.firstName} ${personalInfo.surName}"
        }
        if(personalInfo.phoneNumber != null) {
            view.findViewById<TextView>(R.id.profile_phone_number).text = personalInfo.phoneNumber
        }
        if(personalInfo.email != null) {
            view.findViewById<TextView>(R.id.profile_email).text = personalInfo.email
        }
        if(personalInfo.birthDate != null) {
            view.findViewById<TextView>(R.id.profile_birthday).text =
                SimpleDateFormat("dd/MM/yyyy").format(personalInfo.birthDate)
        }
        if(personalInfo.allowNotifications != null) {
            view.findViewById<TextView>(R.id.profile_alerts).text =
                if (personalInfo.allowNotifications == true) {
                    "כן"
                } else {
                    "לא"
                }
        }
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}