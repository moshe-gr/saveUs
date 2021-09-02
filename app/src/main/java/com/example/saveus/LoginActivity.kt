package com.example.saveus

import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setSupportActionBar(findViewById(R.id.my_toolbar))

        supportActionBar!!.setDisplayShowTitleEnabled(false)

        findViewById<TextView>(R.id.regulations).setOnClickListener {
            val wb = WebView(this)
            wb.loadUrl("file:///android_asset/regulations.html")
            setContentView(wb)
        }
        
        findViewById<TextView>(R.id.terms_of_use).setOnClickListener {
            val wb = WebView(this)
            wb.loadUrl("file:///android_asset/terms_of_use.html")
            setContentView(wb)
        }

        findViewById<Button>(R.id.sign_in).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        findViewById<Button>(R.id.skip_sign_in).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }

}
