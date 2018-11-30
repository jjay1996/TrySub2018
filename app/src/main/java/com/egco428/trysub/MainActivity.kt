package com.egco428.trysub


import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.egco428.trysub.Mini_Game.minigame
import android.util.Log
import com.egco428.trysub.Create_Login.choose
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    var LockId :DataSourse? = null //User Now

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //User Test
        var database = FirebaseDatabase.getInstance().getReference("User")
        database.addValueEventListener(object  : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(p0: DataSnapshot?) {

                for (i in p0!!.children){
                    val message = i.getValue(DataSourse::class.java)

                    if ("poryou11" == message!!.username.toString() ){
                            LockId = message
                        break
                    }

                }

            }
        })
        //End User Test

        setTitle("                               Try-Sub")
        startBtn.setOnClickListener {
            val t = Intent(this,minigame::class.java)
            //val t = Intent(this,choose::class.java)
            startActivity(t)
        }

        //Log.d("check",objects.MyQuestion[0][0])

    }



}
