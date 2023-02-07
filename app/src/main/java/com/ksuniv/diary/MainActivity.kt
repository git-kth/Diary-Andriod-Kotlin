package com.ksuniv.diary

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import com.ksuniv.diary.FormActivity
import com.ksuniv.diary.R
import java.io.BufferedReader
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.FileReader
import java.io.IOException
import java.nio.file.Paths
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dayText : TextView = findViewById(R.id.day_text)
        val calendarView : CalendarView = findViewById(R.id.calenderView)

        val dateFormat : DateFormat = SimpleDateFormat("yyyy년 MM월 dd일")

        val date = Date(calendarView.date)
        dayText.text = dateFormat.format(date)


        val btn: Button = findViewById(R.id.btn)

        val intent = Intent(this, FormActivity::class.java)
        btn.setOnClickListener{
            startActivity(intent)
        }
        calendarView.setOnDateChangeListener { calendarView, year, month, dayOfMonth ->
            var day = "${year}년 ${if(month < 9) "0" + (month + 1).toString() else month + 1}월 ${if(dayOfMonth < 10) "0" + dayOfMonth.toString() else dayOfMonth}일"
            dayText.text = day

//            var output = openFileOutput("${name}.dat", Context.MODE_PRIVATE) //MODE_APPEND : 뒤에 추가, MODE_PRIVATE : 덮어쓰기
//            var dos = DataOutputStream(output)
//
//
//            dos.writeUTF("안녕하세요")
//            dos.flush()
//            dos.close()

            try {
                var name = "${year}${if(month < 9) "0" + (month + 1).toString() else month + 1}${if(dayOfMonth < 10) "0" + dayOfMonth.toString() else dayOfMonth}"
                var input = openFileInput("${name}.dat")
                var dis = DataInputStream(input)
                var valueUTF = dis.readUTF()
                dis.close() //종료
                dayText.text = valueUTF
                btn.visibility = View.INVISIBLE
            }catch(e: IOException){
                btn.visibility = View.VISIBLE
            }

        }




    }
}