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
import kotlinx.android.synthetic.main.activity_play.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setTitle("                               Try-Sub")
        startBtn.setOnClickListener {
            //val t = Intent(this,PlayActivity::class.java)
            val intent = Intent(this,choose::class.java)

            startActivity(intent)
        }

        //Log.d("check",objects.MyQuestion[0][0])

    }



}
