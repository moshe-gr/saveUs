package com.example.saveus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.widget.Toolbar

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setSupportActionBar(findViewById(R.id.my_toolbar))

        supportActionBar!!.setDisplayShowTitleEnabled(false)

        findViewById<Button>(R.id.sign_in).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        findViewById<Button>(R.id.skip_sign_in).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }

}