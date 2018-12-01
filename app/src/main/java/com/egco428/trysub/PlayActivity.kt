package com.egco428.trysub

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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
        Log.d(this.toString(),"keyPath : $keyPath")
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
