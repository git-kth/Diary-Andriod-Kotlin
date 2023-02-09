package com.ksuniv.diary

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
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
import java.time.DayOfWeek
import java.time.Year
import java.util.Date


class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dayText : TextView = findViewById(R.id.day_text)
        val diaryContent : TextView = findViewById(R.id.diaryContent)
        val calendarView : CalendarView = findViewById(R.id.calenderView)
        val actionVar = supportActionBar

        actionVar?.title = "일기장"
        val dateFormat : DateFormat = SimpleDateFormat("yyyy년 MM월 dd일")

        val date = Date(calendarView.date)
        dayText.text = dateFormat.format(date)
        var name : String
        name = "${date.year + 1900}${if(date.month < 9) "0" + (date.month + 1).toString() else date.month + 1}${if(date.day + 5 < 10) "0" + (date.day + 5).toString() else date.day + 5}"
        val btn: Button = findViewById(R.id.btn)
        val intent = Intent(this, FormActivity::class.java)

        intent.putExtra("name", name)
        btn.setOnClickListener{
            startActivity(intent)
        }

        calendarView.maxDate = date.time

        fun viewDiary(calendarView: CalendarView, year: Int, month: Int, dayOfMonth: Int) {
            var day = "${year}년 ${if(month < 9) "0" + (month + 1).toString() else month + 1}월 ${if(dayOfMonth < 10) "0" + dayOfMonth.toString() else dayOfMonth}일"
            dayText.text = day

            try {
                name = "${year}${if(month < 9) "0" + (month + 1).toString() else month + 1}${if(dayOfMonth < 10) "0" + dayOfMonth.toString() else dayOfMonth}"
                intent.putExtra("name", name)
                var input = openFileInput("${name}.dat")
                var dis = DataInputStream(input)
                var valueUTF = dis.readUTF()
                dis.close() //종료
                diaryContent.visibility = View.VISIBLE
                diaryContent.text = valueUTF
                btn.visibility = View.INVISIBLE
            }catch(e: IOException){
                diaryContent.visibility = View.INVISIBLE
                diaryContent.text = ""
                btn.visibility = View.VISIBLE
            }

        }

        viewDiary(calendarView, date.year + 1900, date.month, date.day + 5)
        calendarView.setOnDateChangeListener { calendarView, year, month, dayOfMonth ->
//            var output = openFileOutput("${name}.dat", Context.MODE_PRIVATE) //MODE_APPEND : 뒤에 추가, MODE_PRIVATE : 덮어쓰기
//            var dos = DataOutputStream(output)
//
//
//            dos.writeUTF("안녕하세요")
//            dos.flush()
//            dos.close()

            viewDiary(calendarView, year, month, dayOfMonth)
        }

        calendarView.date = date.time - 1
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.diary_list -> {
                val intent = Intent(this, ListActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}