package com.project.roomstudy

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MainAdapter(
    val context: Context,
    val dataList: MutableList<MainData>
) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private lateinit var database: RoomDB

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val textView: TextView
        val btEdit: ImageView
        val btDelete: ImageView

        init {
            textView = view.findViewById(R.id.text_view)
            btEdit = view.findViewById(R.id.bt_edit)
            btDelete = view.findViewById(R.id.bt_delete)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_row_main, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = dataList[position]
        database = RoomDB.getInstance(context)!!
        holder.textView.text = data.text

        holder.btDelete.setOnClickListener {
            database.mainDao().delete(data)
            dataList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, dataList.size)
        }

        holder.btEdit.setOnClickListener {

            //dialog window
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.dialog_update)
            val width = WindowManager.LayoutParams.MATCH_PARENT
            val height = WindowManager.LayoutParams.WRAP_CONTENT
            dialog.window!!.setLayout(width, height)
            dialog.show()

            val editText = dialog.findViewById<EditText>(R.id.dialog_edit_text)
            val bt_update = dialog.findViewById<Button>(R.id.bt_update)
            editText.setText(data.text)
            //

            bt_update.setOnClickListener {
                dialog.dismiss()
                val eText = editText.text.toString().trim { it <= ' ' }
                database.mainDao().update(data.id, eText)
                dataList.clear()
                dataList.addAll(database.mainDao().getAll())
                notifyDataSetChanged()
            }
        }
    }
}