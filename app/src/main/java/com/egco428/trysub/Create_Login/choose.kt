package com.egco428.trysub.Create_Login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.egco428.trysub.R

import kotlinx.android.synthetic.main.activity_choose.*

class choose : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose)

        createBtn.setOnClickListener {
            val intent = Intent(this, Create::class.java)
            startActivity(intent)
            finish()
        }
    }
}
