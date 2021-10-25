package com.example.saveus.activities

import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.saveus.PersonalInfo
import com.example.saveus.R
import com.example.saveus.fragments.WebViewFragment
import com.google.gson.Gson


class LoginActivity : AppCompatActivity() {
    private lateinit var wb: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val personalInfo = PersonalInfo()
        wb = WebView(this)

        setSupportActionBar(findViewById(R.id.my_toolbar))
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        findViewById<TextView>(R.id.regulations).setOnClickListener {
            val fragment = WebViewFragment.newInstance("https://www.pantai.com.my/privacy-policy")
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.login_fragment_wrapper, fragment)
                addToBackStack(fragment.toString())
                commit()
            }
        }

        findViewById<TextView>(R.id.terms_of_use).setOnClickListener {
            val fragment = WebViewFragment.newInstance("file:///android_asset/terms_of_use.html")
                supportFragmentManager.beginTransaction().apply {
                replace(R.id.login_fragment_wrapper, fragment)
                addToBackStack(fragment.toString())
                commit()
            }
        }

        findViewById<Button>(R.id.sign_in).setOnClickListener {
            personalInfo.fullName = findViewById<EditText>(R.id.login_name).text.toString()
            personalInfo.phoneNumber = findViewById<EditText>(R.id.login_phone_number).text.toString()
            personalInfo.email = findViewById<EditText>(R.id.login_email).text.toString()
            if(personalInfo.fullName.isNullOrEmpty() || personalInfo.phoneNumber.isNullOrEmpty() || personalInfo.email.isNullOrEmpty()){
                Toast.makeText(this, "missing data", Toast.LENGTH_SHORT).show()
            }
            else {
                getSharedPreferences("sharedPrefs", MODE_PRIVATE)
                    .edit().putString("personalInfo", Gson().toJson(personalInfo)).apply()
                startActivity(Intent(this, MainActivity::class.java))
            }
        }

        findViewById<Button>(R.id.skip_sign_in).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }
}
