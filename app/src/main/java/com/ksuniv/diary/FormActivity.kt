package com.ksuniv.diary

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.io.DataOutputStream

class FormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        val homeBtn: Button = findViewById(R.id.homeBtn)
        val saveBtn: Button = findViewById(R.id.saveBtn)
        val diaryText: EditText = findViewById(R.id.diaryText)


        var name = intent.getStringExtra("name")
        homeBtn.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }

        saveBtn.setOnClickListener{
            var output = openFileOutput("${name}.dat", Context.MODE_PRIVATE) //MODE_APPEND : 뒤에 추가, MODE_PRIVATE : 덮어쓰기
            var dos = DataOutputStream(output)
            dos.writeUTF(diaryText.text.toString())
            dos.flush()
            dos.close()
            homeBtn.callOnClick()
        }
    }
}