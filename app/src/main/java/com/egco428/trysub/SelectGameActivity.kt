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

    var userId :String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_game)

        setTitle("                   Try-Sub")

        userId = intent.getStringExtra("keyPath")

        //ล็อคปุ่มไม่ให้กดและให้เป็นสีทำ
        level2Btn.isEnabled = false
        level2Btn.setBackgroundResource(R.drawable.choicebtn_gray_lock)

        level3Btn.isEnabled = false
        level3Btn.setBackgroundResource(R.drawable.choicebtn_gray_lock)

        level4Btn.isEnabled = false
        level4Btn.setBackgroundResource(R.drawable.choicebtn_gray_lock)

        level5Btn.isEnabled = false
        level5Btn.setBackgroundResource(R.drawable.choicebtn_gray_lock)

        level6Btn.isEnabled = false
        level6Btn.setBackgroundResource(R.drawable.choicebtn_gray_lock)

        level7Btn.isEnabled = false
        level7Btn.setBackgroundResource(R.drawable.choicebtn_gray_lock)

        level8Btn.isEnabled = false
        level8Btn.setBackgroundResource(R.drawable.choicebtn_gray_lock)

        level9Btn.isEnabled = false
        level9Btn.setBackgroundResource(R.drawable.choicebtn_gray_lock)

        level10Btn.isEnabled = false
        level10Btn.setBackgroundResource(R.drawable.choicebtn_gray_lock)
        //ล็อคปุ่มไม่ให้กดและให้เป็นสีทำ

        getdatafromFB()
        level1Btn.setOnClickListener {
            val intent = Intent(this@SelectGameActivity,QuestionAnswerActivity::class.java)
            intent.putExtra("Level","0")
            intent.putExtra("keyPath",userId)
            startActivity(intent)
        }

        level2Btn.setOnClickListener {
            val intent = Intent(this@SelectGameActivity,QuestionAnswerActivity::class.java)
            intent.putExtra("Level","1")
            intent.putExtra("keyPath",userId)
            startActivity(intent)
        }

        level3Btn.setOnClickListener {
            val intent = Intent(this@SelectGameActivity,QuestionAnswerActivity::class.java)
            intent.putExtra("Level","2")
            intent.putExtra("keyPath",userId)
            startActivity(intent)
        }

        level4Btn.setOnClickListener {
            val intent = Intent(this@SelectGameActivity,QuestionAnswerActivity::class.java)
            intent.putExtra("Level","3")
            intent.putExtra("keyPath",userId)
            startActivity(intent)
        }

        level5Btn.setOnClickListener {
            val intent = Intent(this@SelectGameActivity,QuestionAnswerActivity::class.java)
            intent.putExtra("Level","4")
            intent.putExtra("keyPath",userId)
            startActivity(intent)
        }

        level6Btn.setOnClickListener {
            val intent = Intent(this@SelectGameActivity,QuestionAnswerActivity::class.java)
            intent.putExtra("Level","5")
            intent.putExtra("keyPath",userId)
            startActivity(intent)
        }

        level7Btn.setOnClickListener {
            val intent = Intent(this@SelectGameActivity,QuestionAnswerActivity::class.java)
            intent.putExtra("Level","6")
            intent.putExtra("keyPath",userId)
            startActivity(intent)
        }

        level8Btn.setOnClickListener {
            val intent = Intent(this@SelectGameActivity,QuestionAnswerActivity::class.java)
            intent.putExtra("Level","7")
            intent.putExtra("keyPath",userId)
            startActivity(intent)
        }

        level9Btn.setOnClickListener {
            val intent = Intent(this@SelectGameActivity,QuestionAnswerActivity::class.java)
            intent.putExtra("Level","8")
            intent.putExtra("keyPath",userId)
            startActivity(intent)
        }

        level10Btn.setOnClickListener {
            val intent = Intent(this@SelectGameActivity,QuestionAnswerActivity::class.java)
            intent.putExtra("Level","9")
            intent.putExtra("keyPath",userId)
            startActivity(intent)
        }

    }

    fun getdatafromFB(){

        var database = FirebaseDatabase.getInstance().getReference("User/${userId}")
        database.addValueEventListener(object  : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
            }
            override fun onDataChange(p0: DataSnapshot?) {
                    //Log.d("check value LockId","${p0.child("user_mission").child("unlock_level").value.toString()}")
                    var checkBtn = p0!!.child("user_mission").child("unlock_level").value.toString().toInt()
                    checkBtn(checkBtn)
            }
        })
        //End User Test
    }

    fun checkBtn(unlock_level:Int){

        if(unlock_level>=2){
            level2Btn.isEnabled = true
            level2Btn.setBackgroundResource(R.drawable.learn_button_style)
        }
        if(unlock_level>=3){
            level3Btn.isEnabled = true
            level3Btn.setBackgroundResource(R.drawable.learn_button_style)
        }
        if(unlock_level>=4){
            level4Btn.isEnabled = true
            level4Btn.setBackgroundResource(R.drawable.learn_button_style)
        }
        if(unlock_level>=5){
            level5Btn.isEnabled = true
            level5Btn.setBackgroundResource(R.drawable.learn_button_style)
        }
        if(unlock_level>=6){
            level6Btn.isEnabled = true
            level6Btn.setBackgroundResource(R.drawable.learn_button_style)
        }
        if(unlock_level>=7){
            level7Btn.isEnabled = true
            level7Btn.setBackgroundResource(R.drawable.learn_button_style)
        }
        if(unlock_level>=8){
            level8Btn.isEnabled = true
            level8Btn.setBackgroundResource(R.drawable.learn_button_style)
        }
        if(unlock_level>=9){
            level9Btn.isEnabled = true
            level9Btn.setBackgroundResource(R.drawable.learn_button_style)
        }
        if(unlock_level>=10){
            level10Btn.isEnabled = true
            level10Btn.setBackgroundResource(R.drawable.learn_button_style)
        }
    }

}
