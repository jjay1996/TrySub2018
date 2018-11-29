package com.egco428.trysub

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.egco428.trysub.Mini_Game.minigame
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setTitle("                               Try-Sub")

        startBtn.setOnClickListener {
            val intent = Intent(this,minigame::class.java)
            startActivity(intent)
        }
    }


}
