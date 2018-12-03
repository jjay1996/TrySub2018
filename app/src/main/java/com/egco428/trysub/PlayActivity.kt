package com.egco428.trysub

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat


import android.support.v4.app.ActivityCompat.finishAffinity
import android.support.v4.content.ContextCompat.startActivity
import android.util.Log
import android.view.animation.AnimationUtils
import com.egco428.trysub.Create_Login.choose
import com.egco428.trysub.R.id.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_create.*

import kotlinx.android.synthetic.main.activity_play.*

class PlayActivity : AppCompatActivity() {
    var keyPath:String = ""
    var dataSnapshot:DataSnapshot? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)
        val bundle = intent.extras //รับมาจากหน้าแรกแต่ยังไม่เลือดค่า
        if(bundle!=null) {
            keyPath = bundle.getString("keyPath").toString()
        }
        //Log.d(this.toString(),"keyPath : $keyPath")
        setTitle("                               Try-Sub")

        //do animation
        var animate1 = AnimationUtils.loadAnimation(this,R.anim.fromleft)
        var animate2 = AnimationUtils.loadAnimation(this,R.anim.fromright)
        var animate3 = AnimationUtils.loadAnimation(this,R.anim.fromtop)
        var animate4 = AnimationUtils.loadAnimation(this,R.anim.frombuttom)

        TrysubPlayTextView!!.animation = animate3
        learnBtn!!.animation = animate4
        startGameBtn!!.animation = animate4
        profileBtn!!.animation = animate4
        highScoreBtn!!.animation = animate4
        exitAppBtn!!.animation = animate4
        //End do animation

        var database = FirebaseDatabase.getInstance().getReference("User")
        database.addValueEventListener(object  : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {}
            override fun onDataChange(p0: DataSnapshot?) {
                dataSnapshot = p0
                for (i in dataSnapshot!!.children){
                    dataSnapshot=i
                    //Log.d("check old value","${dataSnapshot!!.child("name").value.toString()}")
                    if (keyPath == i.key.toString()){
                        textView10.text = dataSnapshot!!.child("name").value.toString()
                        break
                    }

                }
            }
        })

        learnBtn.setOnClickListener {
            val intenToLearn = Intent(this,SelectLearnActivity::class.java)
            startActivity(intenToLearn)
        }

        startGameBtn.setOnClickListener {
            val intentToSelectGame = Intent(this,SelectGameActivity::class.java)
            intentToSelectGame.putExtra("keyPath",keyPath)
            startActivity(intentToSelectGame)
        }

        profileBtn.setOnClickListener {
            val intentToProfile = Intent(this,ProfileActivity::class.java)
            intentToProfile.putExtra("keyPath",keyPath)
            startActivity(intentToProfile)
        }

        highScoreBtn.setOnClickListener {
            val intentToHighScore = Intent(this,HighScoreActivity::class.java)
            startActivity(intentToHighScore)
        }

        exitAppBtn.setOnClickListener {
            val intentToHome = Intent(this,MainActivity::class.java)
            startActivity(intentToHome)
            finish()
        }
    }
}
