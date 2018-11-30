package com.egco428.trysub

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_conclude_score.*

class ConcludeScoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conclude_score)

        var score = intent.getStringExtra("Score")
        concludeScoreTextView.text = score

        backToSelectLevelBtn.setOnClickListener {
            val intent = Intent(this@ConcludeScoreActivity,SelectGameActivity::class.java)
            startActivity(intent)
        }

        replayBtn.setOnClickListener {

        }

        nextLevelBtn.setOnClickListener {

        }
    }
}
