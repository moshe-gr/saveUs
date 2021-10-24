package com.example.saveus

import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson


class LoginActivity : AppCompatActivity() {
    private lateinit var wb: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        wb = WebView(this)

        setSupportActionBar(findViewById(R.id.my_toolbar))
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        val personalInfo = PersonalInfo()

        findViewById<TextView>(R.id.regulations).setOnClickListener {
            wb.loadUrl("https://www.pantai.com.my/privacy-policy")
            setContentView(wb)
        }

        findViewById<TextView>(R.id.terms_of_use).setOnClickListener {
            wb.loadUrl("file:///android_asset/terms_of_use.html")
            setContentView(wb)
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


    override fun onBackPressed() {
        if (wb.isInLayout) {
            startActivity(Intent(this, LoginActivity::class.java))
        } else {
            super.onBackPressed()
        }
    }
}
