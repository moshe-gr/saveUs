package com.example.saveus

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.webkit.WebViewAssetLoader
import androidx.webkit.WebViewClientCompat


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val wb = WebView(this)

        setSupportActionBar(findViewById(R.id.my_toolbar))

        supportActionBar!!.setDisplayShowTitleEnabled(false)

        findViewById<TextView>(R.id.regulations).setOnClickListener {
            wb.loadUrl("file:///android_asset/regulations.html")
            setContentView(wb)
        }
        
        findViewById<TextView>(R.id.terms_of_use).setOnClickListener {
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
