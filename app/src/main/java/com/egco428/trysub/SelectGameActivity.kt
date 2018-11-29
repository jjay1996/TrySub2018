package com.egco428.trysub

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_select_game.*

class SelectGameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_game)

        setTitle("                   Try-Sub")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        level1Btn.setOnClickListener{
            val intent = Intent(this@SelectGameActivity,QuestionAnswerActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.select_game_menu,menu)
        return true
    }

    //คล้ายๆonClick
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item!!.getItemId()

        if(id == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)

    }
}
