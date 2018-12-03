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
import android.view.animation.AnimationUtils
import android.widget.*
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.*
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

    private val database = FirebaseDatabase.getInstance().getReference("User")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_high_score)

        //Do animation
        var animate1 = AnimationUtils.loadAnimation(this,R.anim.fromleft)
        var animate2 = AnimationUtils.loadAnimation(this,R.anim.fromright)
        var animate3 = AnimationUtils.loadAnimation(this,R.anim.fromtop)
        var animate4 = AnimationUtils.loadAnimation(this,R.anim.frombuttom)
        //right
        title4!!.animation = animate2
        //left
        backFromHSBtn!!.animation = animate1
        title2!!.animation = animate1
        title1!!.animation = animate1
        //top
        textView4!!.animation = animate3
        //buttom
        highscoreList!!.animation = animate4
        //End do animation

        //back to home page
        backFromHSBtn.setOnClickListener  {
            finish()
        }

        //set data from firebase
        var list :ArrayList<ArrayList<String>> = arrayListOf()
        database.addListenerForSingleValueEvent(object  : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }

            //get data and sort data by score
            override fun onDataChange(p0: DataSnapshot?) {
                for (i in p0!!.children){
                    var listIn:ArrayList<String> = arrayListOf()
                    listIn.add(i.child("name").value.toString())
                    listIn.add(i.child("user_mission").child("total_score").value.toString())
                    listIn.add(i.child("picture").value.toString())
                    list.add(listIn)
                }
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
                    i++
                }

                val listView = findViewById<ListView>(R.id.highscoreList)
                listView.adapter = myCustomAdapter(this@HighScoreActivity,list)  // create class and set adapter for ListView
            }
        })
    }

    private class myCustomAdapter(context: Context,listOut:ArrayList<ArrayList<String>>): BaseAdapter(){
        private val mContext: Context
        private var list :ArrayList<ArrayList<String>>
        init{
            mContext = context
            list = listOut
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

        //call when item show on screen
        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
            //set class
            val rowMain: View
            if (convertView == null) {
                val layoutInflator = LayoutInflater.from(viewGroup!!.context)
                rowMain = layoutInflator.inflate(R.layout.row_high_score, viewGroup, false)
                val viewHolder = ViewHolder(rowMain.testName, rowMain.testHighScore, rowMain.imageAvatar)
                rowMain.tag = viewHolder
            } else {
                rowMain = convertView
            }

            //set data to class ViewHolder
            val viewHolder = rowMain.tag as ViewHolder
            viewHolder.nameText.text = list.get(position).get(0)
            viewHolder.totalScore.text = list.get(position).get(1)

            //set Image
            if(list.get(position).get(2)!=="null" ) {
                //download Imgae
                var storage = FirebaseStorage.getInstance()
                var storageReference = storage!!.getReferenceFromUrl("gs://trysup2018.appspot.com/${list.get(position).get(2)}")
                var localFile = File.createTempFile("images", "jpg");
                storageReference!!.getFile(localFile)
                        .addOnSuccessListener(OnSuccessListener<FileDownloadTask.TaskSnapshot> {
                            //set Image
                            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                            viewHolder.picture.setImageBitmap(bitmap)
                        }).addOnFailureListener(OnFailureListener {

                        })
            }

            return rowMain
        }


        private class ViewHolder(val nameText: TextView ,val totalScore: TextView,val picture:ImageView){

        }
    }

}
