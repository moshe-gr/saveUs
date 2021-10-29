package com.example.saveus.fragments

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.saveus.PersonalInfo
import com.example.saveus.R
import com.example.saveus.activities.OnBoardingActivity
import com.google.gson.Gson
import java.text.SimpleDateFormat

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val editProfileButton = view.findViewById<ImageView>(R.id.edit_profile_button)
        val termsOfUseButton = view.findViewById<LinearLayout>(R.id.profile_terms_of_use)
        val howItWorksButton = view.findViewById<LinearLayout>(R.id.how_it_works)
        val shareButton = view.findViewById<LinearLayout>(R.id.share_with_friends)
        val problemReportButton = view.findViewById<LinearLayout>(R.id.problem_report)

        val json = activity?.getSharedPreferences("sharedPrefs",
            AppCompatActivity.MODE_PRIVATE)?.getString("personalInfo", "")
        val personalInfo = Gson().fromJson(json, PersonalInfo::class.java)

        if(personalInfo.fullName != null) {
            view.findViewById<TextView>(R.id.profile_name).text = personalInfo.fullName
        }
        if(personalInfo.phoneNumber != null) {
            view.findViewById<TextView>(R.id.profile_phone_number).text = personalInfo.phoneNumber
        }
        if(personalInfo.email != null) {
            view.findViewById<TextView>(R.id.profile_email).text = personalInfo.email
        }
        if(personalInfo.birthDate != null) {
            view.findViewById<TextView>(R.id.profile_birthday).text =
                SimpleDateFormat("dd/MM/yyyy").format(personalInfo.birthDate!!)
        }
        if(personalInfo.allowNotifications != null) {
            view.findViewById<TextView>(R.id.profile_alerts).text =
                if (personalInfo.allowNotifications == true) {
                    "כן"
                } else {
                    "לא"
                }
        }
        if(personalInfo.language != null){
            view.findViewById<TextView>(R.id.profile_language).text = personalInfo.language
        }
        editProfileButton.setOnClickListener {
            val fragment = EditProfileFragment.newInstance()
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.fl_wrapper, fragment)
                addToBackStack(fragment.toString())
                commit()
            }
        }
        howItWorksButton.setOnClickListener {
            startActivity(Intent(view.context, OnBoardingActivity::class.java))
        }
        termsOfUseButton.setOnClickListener {
            val fragment = WebViewFragment.newInstance("file:///android_asset/terms_of_use.html")
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.fl_wrapper, fragment)
                addToBackStack(fragment.toString())
                commit()
            }
        }
        shareButton.setOnClickListener {
            val pm: PackageManager? = activity?.packageManager
            val appInfo: ApplicationInfo? = pm?.getApplicationInfo(activity?.packageName!!, PackageManager.GET_META_DATA)
            val sendBt = Intent(Intent.ACTION_SEND)
            sendBt.type = "*/*"
            sendBt.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + appInfo?.publicSourceDir))
            activity?.startActivity(Intent.createChooser(sendBt, "Share it using"))
        }
        return view
    }

    override fun onStart() {
        super.onStart()
        activity?.findViewById<ImageView>(R.id.profile_button)
            ?.setImageResource(R.drawable.close_1)
    }

    override fun onStop() {
        super.onStop()
        activity?.findViewById<ImageView>(R.id.profile_button)
            ?.setImageResource(R.drawable.account)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}