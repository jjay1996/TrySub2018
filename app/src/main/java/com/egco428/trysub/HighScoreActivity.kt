package com.egco428.trysub

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.widget.*
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_high_score.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row_high_score.view.*
import java.io.File
import kotlin.coroutines.experimental.*
import java.sql.Array
import kotlin.coroutines.experimental.coroutineContext
import kotlin.coroutines.experimental.suspendCoroutine

class HighScoreActivity : AppCompatActivity() {

    private lateinit var userID:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_high_score)

        setTitle("                    Try-Sub")
        //supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        userID = "User/-LScPrxbemODH7AsIJ86"

        backFromHSBtn.setOnClickListener  {
            finish()
        }

        var list :ArrayList<ArrayList<String>> = arrayListOf()
        var database = FirebaseDatabase.getInstance().getReference("User")
        database.addValueEventListener(object  : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(p0: DataSnapshot?) {
                for (i in p0!!.children){
                    var listIn:ArrayList<String> = arrayListOf()
                    listIn.add(i.child("name").value.toString())
                    listIn.add(i.child("user_mission").child("total_score").value.toString())
                    listIn.add(i.child("picture").value.toString())
                    list.add(listIn)
                }
                Log.d("test1",list.toString())
                var i = 1
                var j: Int
                while (i < list.size) {
                    var key = arrayListOf(list[i][0],list[i][1],list[i][2])
                    j = i - 1

                    while (j >= 0 && list[j][1].toInt() < key[1].toInt()) {
                        list[j+1] = list[j]
                        j = j - 1

                    }
                    list[j+1] = key
                    //Log.d("test",list.toString())
                    i++
                }
                Log.d("test2",list.toString())
                val listView = findViewById<ListView>(R.id.highscoreList)
                listView.adapter = myCustomAdapter(this@HighScoreActivity,list)
            }
        })
    }

    private class myCustomAdapter(context: Context,listOut:ArrayList<ArrayList<String>>): BaseAdapter(){
        private val mContext: Context
        private var list :ArrayList<ArrayList<String>>
        init{
            mContext = context
            list = listOut
            Log.d("test3",list.toString())
        }

        override fun getCount(): Int {
            return list.size
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getItem(position: Int): Any {
            return list[position]
        }

        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
            val rowMain: View
            if (convertView == null) {
                val layoutInflator = LayoutInflater.from(viewGroup!!.context)
                rowMain = layoutInflator.inflate(R.layout.row_high_score, viewGroup, false)
                val viewHolder = ViewHolder(rowMain.testName, rowMain.testHighScore, rowMain.imageAvatar)
                rowMain.tag = viewHolder
            } else {
                rowMain = convertView
            }
            val viewHolder = rowMain.tag as ViewHolder
            viewHolder.nameText.text = list.get(position).get(0)
            viewHolder.totalScore.text = list.get(position).get(1)
            //Log.d("test",list.get(position).toString())

            var storage = FirebaseStorage.getInstance()
            var storageReference = storage!!.getReferenceFromUrl("gs://trysup2018.appspot.com/${list.get(position).get(2)}")
            var localFile = File.createTempFile("images", "jpg");
            storageReference!!.getFile(localFile)
                    .addOnSuccessListener(OnSuccessListener<FileDownloadTask.TaskSnapshot> {
                        val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                        viewHolder.picture.setImageBitmap(bitmap)
                    }).addOnFailureListener(OnFailureListener {

                    })
            return rowMain
        }


        private class ViewHolder(val nameText: TextView ,val totalScore: TextView,val picture:ImageView){

        }
    }

}
