package com.ksuniv.diary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import java.io.DataInputStream
import java.io.File
import java.io.IOException

class ListActivity : AppCompatActivity() {
    lateinit var listView: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        listView = findViewById(R.id.list_view)
        File("/data/data/com.ksuniv.diary/files").walk().forEach {
            if(it.toString() != "/data/data/com.ksuniv.diary/files"){
//                Log.d("ListActivity", it.toString() + "파일 존재")
//                var arr = it.toString().split("/")
//                try {
//                    var input = openFileInput(arr[arr.size - 1])
//                    var dis = DataInputStream(input)
//                    var valueUTF = dis.readUTF()
//                    createView(valueUTF)
//                    dis.close() //종료
//                }catch(e: IOException){
//                    Toast.makeText(this, "문제 발생", Toast.LENGTH_LONG).show()
//                }
                var arr = it.toString().split("/")
                var date = arr[arr.size - 1].split(".")[0]
                createView(date)
            }
        }

    }

    private fun createView(text: String) {
        val itemBtn = Button(applicationContext)
        itemBtn.text = "" + text.subSequence(0, 4) + "년 " +
                text.subSequence(4, 6) + "월 " +
                text.subSequence(6, 8) + "일"
        itemBtn.textSize = 30f
        itemBtn.setOnClickListener{
            val intent = Intent(this, ItemActivity::class.java)
            intent.putExtra("name", text)
            startActivity(intent)
        }
        listView.addView(itemBtn)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.home_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.home_menu -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}