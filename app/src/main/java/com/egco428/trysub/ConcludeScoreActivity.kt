package com.egco428.trysub

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_conclude_score.*

class ConcludeScoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conclude_score)

        var score = intent.getStringExtra("Score").toInt()
        concludeScoreTextView.text = score.toString()
        val nolevel = intent.getStringExtra("nLevel").toInt()

        //คะแนนน้อยกว่า8ห้ามเล่นMiniGameกับกดปุ่่มnextLevelBtn
        if(score<8){

            miniGametextView.isEnabled = false
            mininGameImage.isEnabled = false

            nextLevelBtn.isEnabled = false
        }

        //อยู่ด้าน10ห้ามกดnext
        if(nolevel==9){
            nextLevelBtn.isEnabled = false
        }

        //กดไปเล่น MiniGame
        mininGameImage.setOnClickListener {

        }

        backToSelectLevelBtn.setOnClickListener {
            val intent = Intent(this@ConcludeScoreActivity,SelectGameActivity::class.java)
            startActivity(intent)
        }

        replayBtn.setOnClickListener {
            val intent = Intent(this@ConcludeScoreActivity,QuestionAnswerActivity::class.java)
            intent.putExtra("Level",nolevel.toString())
            startActivity(intent)
        }

        nextLevelBtn.setOnClickListener {
            val intent = Intent(this@ConcludeScoreActivity,QuestionAnswerActivity::class.java)
            intent.putExtra("Level",(nolevel+1).toString())
            startActivity(intent)
        }
    }
}
