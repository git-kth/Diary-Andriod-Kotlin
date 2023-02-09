package com.ksuniv.diary

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import java.io.DataInputStream
import java.io.IOException

class ItemActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)
        val textItem: TextView = findViewById(R.id.text_item)
        val itemTitle: TextView = findViewById(R.id.item_title)
        var name = intent.getStringExtra("name").toString()
        var year =  name.subSequence(0, 4).toString().toInt()
        var month = name.subSequence(4, 6).toString().toInt()
        var dayOfMonth = name.subSequence(6, 8).toString().toInt()
        itemTitle.text = "${name}에 쓴 일기"
        try {
            name = "${year}${if(month < 10) "0" + month.toString() else month}${if(dayOfMonth < 10) "0" + dayOfMonth.toString() else dayOfMonth}"

            var input = openFileInput("${name}.dat")
            var dis = DataInputStream(input)
            var valueUTF = dis.readUTF()
            dis.close() //종료

            textItem.text = valueUTF
        }catch(e: IOException){
            textItem.text = "예상치 못한 에러 발생 다시 시도해주세요."
        }
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