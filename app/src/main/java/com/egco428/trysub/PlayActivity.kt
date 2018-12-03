package com.egco428.trysub

import android.content.Intent
import android.media.MediaPlayer
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)
        val bundle = intent.extras //รับมาจากหน้าแรกแต่ยังไม่เลือดค่า
        if(bundle!=null) {
            keyPath = bundle.getString("keyPath").toString()
        }

        //do animation
        var animate1 = AnimationUtils.loadAnimation(this,R.anim.fromleft)
        var animate2 = AnimationUtils.loadAnimation(this,R.anim.fromright)
        //var animate3 = AnimationUtils.loadAnimation(this,R.anim.fromtop)
        //var animate4 = AnimationUtils.loadAnimation(this,R.anim.frombuttom)
        TrysubPlayTextView!!.animation = animate2
        learnBtn!!.animation = animate1
        startGameBtn!!.animation = animate2
        profileBtn!!.animation = animate1
        highScoreBtn!!.animation = animate2
        exitAppBtn!!.animation = animate1
        //End do animation

        //go to learn page
        learnBtn.setOnClickListener {
            val intenToLearn = Intent(this,SelectLearnActivity::class.java)
            startActivity(intenToLearn)
        }

        //go to play game page
        startGameBtn.setOnClickListener {
            val intentToSelectGame = Intent(this,SelectGameActivity::class.java)
            intentToSelectGame.putExtra("keyPath",keyPath)
            startActivity(intentToSelectGame)
        }

        //go to profile page
        profileBtn.setOnClickListener {
            val intentToProfile = Intent(this,ProfileActivity::class.java)
            intentToProfile.putExtra("keyPath",keyPath)
            startActivity(intentToProfile)
        }

        //go to high_score page
        highScoreBtn.setOnClickListener {
            val intentToHighScore = Intent(this,HighScoreActivity::class.java)
            startActivity(intentToHighScore)
        }

        //Logout and go to first page
        exitAppBtn.setOnClickListener {
            val intentToHome = Intent(this,MainActivity::class.java)
            startActivity(intentToHome)
            finish()
        }
    }

    override fun onResume() {
        //clear animation
        learnBtn!!.clearAnimation()
        startGameBtn!!.clearAnimation()
        profileBtn!!.clearAnimation()
        highScoreBtn!!.clearAnimation()
        exitAppBtn!!.clearAnimation()

        //do animation
        var animate1 = AnimationUtils.loadAnimation(this,R.anim.fromleft)
        var animate2 = AnimationUtils.loadAnimation(this,R.anim.fromright)
        //var animate3 = AnimationUtils.loadAnimation(this,R.anim.fromtop)
        //var animate4 = AnimationUtils.loadAnimation(this,R.anim.frombuttom)
        TrysubPlayTextView!!.animation = animate2
        learnBtn!!.animation = animate1
        startGameBtn!!.animation = animate2
        profileBtn!!.animation = animate1
        highScoreBtn!!.animation = animate2
        exitAppBtn!!.animation = animate1
        //End do animation

        super.onResume()
    }
}
