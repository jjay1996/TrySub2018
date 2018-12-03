package com.egco428.trysub

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_select_learn.*

class SelectLearnActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_learn)

        //Do animation
        var animate1 = AnimationUtils.loadAnimation(this,R.anim.fromleft)
        var animate2 = AnimationUtils.loadAnimation(this,R.anim.fromright)
        var animate3 = AnimationUtils.loadAnimation(this,R.anim.fromtop)
        var animate4 = AnimationUtils.loadAnimation(this,R.anim.frombuttom)
        //top
        textView14!!.animation = animate3
        //left
        lv1Btn!!.animation = animate1
        lv3Btn!!.animation = animate1
        lv5Btn!!.animation = animate1
        lv7Btn!!.animation = animate1
        lv9Btn!!.animation = animate1
        //right
        lv2Btn!!.animation = animate2
        lv4Btn!!.animation = animate2
        lv6Btn!!.animation = animate2
        lv8Btn!!.animation = animate2
        lv10Btn!!.animation = animate2
        //End do animation

        //ปุ่มกลับไปหน้าSelect Lesson
        backToPlayBtn1.setOnClickListener {
            finish()
        }

        ////////// go to level that choose for learning /////////////
        //*** send different level to page ***//
        lv1Btn.setOnClickListener {
            val intent = Intent(this@SelectLearnActivity,LearnWordActivity::class.java)
            intent.putExtra("LevelLearn","0")
            startActivity(intent)
        }
        lv2Btn.setOnClickListener {
            val intent = Intent(this@SelectLearnActivity,LearnWordActivity::class.java)
            intent.putExtra("LevelLearn","1")
            startActivity(intent)
        }
        lv3Btn.setOnClickListener {
            val intent = Intent(this@SelectLearnActivity,LearnWordActivity::class.java)
            intent.putExtra("LevelLearn","2")
            startActivity(intent)
        }
        lv4Btn.setOnClickListener {
            val intent = Intent(this@SelectLearnActivity,LearnWordActivity::class.java)
            intent.putExtra("LevelLearn","3")
            startActivity(intent)
        }
        lv5Btn.setOnClickListener {
            val intent = Intent(this@SelectLearnActivity,LearnWordActivity::class.java)
            intent.putExtra("LevelLearn","4")
            startActivity(intent)
        }
        lv6Btn.setOnClickListener {
            val intent = Intent(this@SelectLearnActivity,LearnWordActivity::class.java)
            intent.putExtra("LevelLearn","5")
            startActivity(intent)
        }
        lv7Btn.setOnClickListener {
            val intent = Intent(this@SelectLearnActivity,LearnWordActivity::class.java)
            intent.putExtra("LevelLearn","6")
            startActivity(intent)
        }
        lv8Btn.setOnClickListener {
            val intent = Intent(this@SelectLearnActivity,LearnWordActivity::class.java)
            intent.putExtra("LevelLearn","7")
            startActivity(intent)
        }
        lv9Btn.setOnClickListener {
            val intent = Intent(this@SelectLearnActivity,LearnWordActivity::class.java)
            intent.putExtra("LevelLearn","8")
            startActivity(intent)
        }
        lv10Btn.setOnClickListener {
            val intent = Intent(this@SelectLearnActivity,LearnWordActivity::class.java)
            intent.putExtra("LevelLearn","9")
            startActivity(intent)
        }
    }
}
