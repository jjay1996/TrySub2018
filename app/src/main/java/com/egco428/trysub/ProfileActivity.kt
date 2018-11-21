package com.egco428.trysub

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        setTitle("                               Try-Sub")
        imageUserView.setImageResource(R.drawable.vatar)

        exitProBtn.setOnClickListener {
            val intentsecondActivity = Intent(this,PlayActivity::class.java)
            startActivity(intentsecondActivity)
        }


    }
}
