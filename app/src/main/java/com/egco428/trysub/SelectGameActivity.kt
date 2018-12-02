package com.egco428.trysub

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_select_game.*

class SelectGameActivity : AppCompatActivity() {

    var LockId :DataSourse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_game)

        setTitle("                   Try-Sub")

        level2Btn.isEnabled = false
        level3Btn.isEnabled = false
        level4Btn.isEnabled = false
        level5Btn.isEnabled = false
        level6Btn.isEnabled = false
        level7Btn.isEnabled = false
        level8Btn.isEnabled = false
        level9Btn.isEnabled = false
        level10Btn.isEnabled = false

        test()
        level1Btn.setOnClickListener {
            val intent = Intent(this@SelectGameActivity,QuestionAnswerActivity::class.java)
            intent.putExtra("Level","0")
            startActivity(intent)
        }

        level2Btn.setOnClickListener {
            val intent = Intent(this@SelectGameActivity,QuestionAnswerActivity::class.java)
            intent.putExtra("Level","1")
            startActivity(intent)
        }

        level3Btn.setOnClickListener {
            val intent = Intent(this@SelectGameActivity,QuestionAnswerActivity::class.java)
            intent.putExtra("Level","2")
            startActivity(intent)
        }

        level4Btn.setOnClickListener {
            val intent = Intent(this@SelectGameActivity,QuestionAnswerActivity::class.java)
            intent.putExtra("Level","3")
            startActivity(intent)
        }

        level5Btn.setOnClickListener {
            val intent = Intent(this@SelectGameActivity,QuestionAnswerActivity::class.java)
            intent.putExtra("Level","4")
            startActivity(intent)
        }

        level6Btn.setOnClickListener {
            val intent = Intent(this@SelectGameActivity,QuestionAnswerActivity::class.java)
            intent.putExtra("Level","5")
            startActivity(intent)
        }

        level7Btn.setOnClickListener {
            val intent = Intent(this@SelectGameActivity,QuestionAnswerActivity::class.java)
            intent.putExtra("Level","6")
            startActivity(intent)
        }

        level8Btn.setOnClickListener {
            val intent = Intent(this@SelectGameActivity,QuestionAnswerActivity::class.java)
            intent.putExtra("Level","7")
            startActivity(intent)
        }

        level9Btn.setOnClickListener {
            val intent = Intent(this@SelectGameActivity,QuestionAnswerActivity::class.java)
            intent.putExtra("Level","8")
            startActivity(intent)
        }

        level10Btn.setOnClickListener {
            val intent = Intent(this@SelectGameActivity,QuestionAnswerActivity::class.java)
            intent.putExtra("Level","9")
            startActivity(intent)
        }

    }

    fun test(){

        var database = FirebaseDatabase.getInstance().getReference("User/mankey/")
        database.addValueEventListener(object  : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
            }
            override fun onDataChange(p0: DataSnapshot?) {
                if (LockId == null){
                    for (i in p0!!.children) {
                        Log.d("check value LockId","${i.child("user_mission").child("unlock_level").value.toString()}")
                        var checkBtn = i.child("user_mission").child("unlock_level").value.toString().toInt()
                        checkBtn(checkBtn)
                    }
                }
            }
        })
        //End User Test
    }

    fun checkBtn(unlock_level:Int){

        if(unlock_level>=2){
            level2Btn.isEnabled = true
        }
        if(unlock_level>=3){
            level3Btn.isEnabled = true
        }
        if(unlock_level>=4){
            level4Btn.isEnabled = true
        }
        if(unlock_level>=5){
            level5Btn.isEnabled = true
        }
        if(unlock_level>=6){
            level6Btn.isEnabled = true
        }
        if(unlock_level>=7){
            level7Btn.isEnabled = true
        }
        if(unlock_level>=8){
            level8Btn.isEnabled = true
        }
        if(unlock_level>=9){
            level9Btn.isEnabled = true
        }
        if(unlock_level>=10){
            level10Btn.isEnabled = true
        }
    }

}
