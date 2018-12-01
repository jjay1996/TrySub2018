package com.egco428.trysub

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_high_score.*

class HighScoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTitle("                    Try-Sub")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }


}
