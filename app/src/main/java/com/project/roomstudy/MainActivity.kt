package com.project.roomstudy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerview : RecyclerView

    var dataList : MutableList<MainData> = mutableListOf()
    lateinit var database: RoomDB
    lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val editText: EditText = findViewById(R.id.edit_text)
        val btAdd: Button = findViewById(R.id.bt_add)
        val btReset: Button = findViewById(R.id.bt_reset)
        recyclerview = findViewById(R.id.recycler_view)

        database = RoomDB.getInstance(this)!!
        dataList = database.mainDao().getAll()

        recyclerview.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(this@MainActivity, dataList)
        recyclerview.adapter = adapter

        btAdd.setOnClickListener {
            val sText = editText.text.toString().trim { it <= ' ' }
            if (sText != "") {
                val data = MainData(null, sText)
                database.mainDao().insert(data)
                editText.setText("")
                dataList.clear()
                dataList.addAll(database.mainDao().getAll())
                adapter.notifyDataSetChanged()
            }
        }

        btReset.setOnClickListener {
            database.mainDao().reset(dataList)
            dataList.clear()
            dataList.addAll(database.mainDao().getAll())
            adapter.notifyDataSetChanged()
        }
    }
}