package com.egco428.trysub

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import android.support.v4.app.ActivityCompat.finishAffinity
import android.support.v4.content.ContextCompat.startActivity
import com.egco428.trysub.R.id.*


import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import kotlinx.android.synthetic.main.activity_play.*

class PlayActivity : AppCompatActivity() {
    var LockId :DataSourse? = null //User Now
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

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
                        textView10.text = "Name  :  ${LockId!!.name}"
                        break
                    }

                }

            }
        })
        //End User Test


        setTitle("                               Try-Sub")

        startGameBtn.setOnClickListener {
            val intentToSelectGame = Intent(this,SelectGameActivity::class.java)
            startActivity(intentToSelectGame)
        }

        profileBtn.setOnClickListener {
            val intentToProfile = Intent(this,ProfileActivity::class.java)
            startActivity(intentToProfile)
        }

        highScoreBtn.setOnClickListener {
            val intentToHighScore = Intent(this,HighScoreActivity::class.java)
            startActivity(intentToHighScore)
        }

        exitAppBtn.setOnClickListener {
            finishAffinity()
        }
    }
}
