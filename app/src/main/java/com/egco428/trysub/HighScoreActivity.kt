package com.egco428.trysub

import android.content.Context
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.row_high_score.view.*

class HighScoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_high_score)

        setTitle("                    Try-Sub")
        //supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val listView = findViewById<ListView>(R.id.highscoreList)

//        val redColor = Color.parseColor("#FF0000")
//        listView.setBackgroundColor(redColor)

        listView.adapter = myCustomAdapter(this)
        listView.setOnItemClickListener { adapterView, view, position, id ->
            val item = adapterView.getItemAtPosition(position) as String
            Toast.makeText(this, "${item} $position", Toast.LENGTH_SHORT).show()
        }
    }

    private class myCustomAdapter(context: Context): BaseAdapter(){
        private val mContext: Context
        private val names = arrayListOf<String>("Anakin","Yoda","Solo","Sky Walker","Wookie","George Lucas","CTPO","R2D2"
                ,"Anakin","Yoda","Solo","Sky Walker","Wookie","George Lucas","CTPO","R2D2","Anakin","Yoda","Solo","Sky Walker"
                ,"Wookie","George Lucas","CTPO","R2D2","Anakin","Yoda","Solo","Sky Walker","Wookie","George Lucas","CTPO","R2D2")
        init{
            mContext = context
        }

        override fun getCount(): Int {
            return names.size
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getItem(position: Int): Any {
            return names[position]
        }

        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
            val whiteColor = Color.parseColor("#FFFFFF")
            val greyColor = Color.parseColor("#E0E0E0")
            val rowMain: View
            if(convertView == null){
                val layoutInflator = LayoutInflater.from(viewGroup!!.context)
                rowMain = layoutInflator.inflate(R.layout.row_high_score, viewGroup, false)
                val viewHolder = ViewHolder(rowMain.testName)
                rowMain.tag = viewHolder
            } else {
                rowMain = convertView
            }
            val viewHolder = rowMain.tag as ViewHolder
            viewHolder.nameText.text = names.get(position)

            return rowMain
        }

        private class ViewHolder(val nameText: TextView){

        }
    }

}
