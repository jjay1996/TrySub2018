package com.egco428.trysub

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_select_learn.*

class SelectLearnActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_learn)

        //ปุ่มกลับไปหน้าSelect Lesson
        backToPlayBtn1.setOnClickListener {
            finish()
        }

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
