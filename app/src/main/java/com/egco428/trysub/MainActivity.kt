package com.egco428.trysub

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var objects:QuestionAnswer = QuestionAnswer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setTitle("                               Try-Sub")

        startBtn.setOnClickListener {
            val intent = Intent(this,PlayActivity::class.java)
            startActivity(intent)
        }

        //Log.d("check",objects.MyQuestion[0][0])

    }



}
