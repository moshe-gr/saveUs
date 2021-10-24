package com.example.saveus.fragments

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.saveus.DateTimeConverter
import com.example.saveus.PersonalInfo
import com.example.saveus.R
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*


class EditProfileFragment : Fragment(), DateTimeConverter {
    private var birthDateCalendar: Calendar? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_profile, container, false)
        val closeButton = view.findViewById<ImageView>(R.id.edit_profile_close)
        val saveButton = view.findViewById<TextView>(R.id.edit_profile_save)
        val nameView = view.findViewById<EditText>(R.id.edit_profile_name)
        val phoneNumberView = view.findViewById<EditText>(R.id.edit_profile_phone_number)
        val emailView = view.findViewById<EditText>(R.id.edit_profile_email)
        val birthdateView = view.findViewById<TextView>(R.id.edit_profile_birthday)
        val notificationsView = view.findViewById<CheckBox>(R.id.edit_profile_notifications)
        val deleteButton = view.findViewById<TextView>(R.id.edit_profile_delete)
        val languageSpinner = view.findViewById<Spinner>(R.id.edit_profile_language_spinner)

        val json = activity?.getSharedPreferences("sharedPrefs",
            AppCompatActivity.MODE_PRIVATE)?.getString("personalInfo", "")
        val personalInfo = Gson().fromJson(json, PersonalInfo::class.java)

        val locals = Locale.getAvailableLocales()
        var languages = arrayOf<String>()

        for(locale in locals){
            if(locale.displayLanguage !in languages) {
                languages = languages.plus(locale.displayLanguage)
            }
        }
        languages = languages.sortedArray()

        val adapter = ArrayAdapter(view.context, R.layout.support_simple_spinner_dropdown_item, languages)
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        languageSpinner.adapter = adapter

        if(personalInfo.fullName != null) {
            nameView.setText(personalInfo.fullName)
        }
        if(personalInfo.phoneNumber != null) {
            phoneNumberView.setText(personalInfo.phoneNumber)
        }
        if(personalInfo.email != null) {
            emailView.setText(personalInfo.email)
        }
        if(personalInfo.birthDate != null) {
            birthdateView.text = SimpleDateFormat("dd/MM/yy").format(personalInfo.birthDate!!)
            birthDateCalendar = getCalendarFromMs(personalInfo.birthDate!!.time)
        }
        if(personalInfo.allowNotifications != null) {
            notificationsView.isChecked = personalInfo.allowNotifications == true
        }
        if(personalInfo.language != null){
            languageSpinner.setSelection(languages.indexOf(personalInfo.language))
        }

        closeButton.setOnClickListener {
            parentFragmentManager.popBackStackImmediate()
        }
        birthdateView.setOnClickListener {
            datePickerButton(view.context, birthdateView).show()
        }
        saveButton.setOnClickListener {
            personalInfo.fullName = nameView.text.toString()
            personalInfo.phoneNumber = phoneNumberView.text.toString()
            personalInfo.email = emailView.text.toString()
            personalInfo.allowNotifications = notificationsView.isChecked
            if (birthDateCalendar != null) {
                personalInfo.birthDate = Date(birthDateCalendar!!.timeInMillis)
            }
            personalInfo.language = languageSpinner.selectedItem as String?
            activity?.getSharedPreferences("sharedPrefs", AppCompatActivity.MODE_PRIVATE)
                ?.edit()?.putString("personalInfo", Gson().toJson(personalInfo))?.apply()
            parentFragmentManager.popBackStackImmediate()
        }
        deleteButton.setOnClickListener {
            nameView.text.clear()
            phoneNumberView.text.clear()
            emailView.text.clear()
            birthdateView.setText(R.string.edit_profile_default)
            notificationsView.isChecked = false
            languageSpinner.setSelection(0)
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

    private fun datePickerButton(context: Context, textView: TextView): DatePickerDialog {
        if (birthDateCalendar == null) {
            birthDateCalendar = getCalendarFromMs(getCurrentTimeInMs())
        }
        val datePickerDialog = DatePickerDialog(
            context,
            { _, year, monthOfYear, dayOfMonth ->
                birthDateCalendar?.set(Calendar.YEAR, year)
                birthDateCalendar?.set(Calendar.MONTH, monthOfYear)
                birthDateCalendar?.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                textView.text = SimpleDateFormat("dd/MM/yy").format(birthDateCalendar!!.timeInMillis)
            },
            birthDateCalendar!!.get(Calendar.YEAR),
            birthDateCalendar!!.get(Calendar.MONTH),
            birthDateCalendar!!.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.maxDate = getCurrentTimeInMs()
        return datePickerDialog
    }

    companion object {
        @JvmStatic
        fun newInstance() = EditProfileFragment()
    }
}