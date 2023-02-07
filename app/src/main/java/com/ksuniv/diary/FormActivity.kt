package com.ksuniv.diary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class FormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        val homeBtn: Button = findViewById(R.id.homeBtn)
        val saveBtn: Button = findViewById(R.id.saveBtn)

        homeBtn.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}