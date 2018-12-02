package com.egco428.trysub

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        setTitle("                               Try-Sub")
        //imageView.setImageResource(R.drawable.vatar)

        EditBtn.setOnClickListener {
            val intentToEditProfile = Intent(this,editProfileActivity::class.java)
            startActivity(intentToEditProfile)
            finish()
        }

        backFromProfileBtn.setOnClickListener {
            finish()
        }


    }
}
