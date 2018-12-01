package com.egco428.trysub

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_conclude_score.*

class ConcludeScoreActivity : AppCompatActivity() {

    var LockId :DataSourse? = null //User Now

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conclude_score)

        var score = intent.getStringExtra("Score").toInt()
        concludeScoreTextView.text = score.toString()
        val Nowlevel = intent.getStringExtra("nLevel").toInt()

        //Upload to Firebase
        var database = FirebaseDatabase.getInstance().getReference("User")
        database.addValueEventListener(object  : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(p0: DataSnapshot?) {
                if (LockId == null) {
                    for (i in p0!!.children) {
                        val message = i.getValue(DataSourse::class.java)

                        if ("poryou11" == message!!.username.toString()) {
                            LockId = message
                            ConcludeScoreAndNowLevel(score, Nowlevel)
                            break
                        }
                    }
                }
            }
        })
        //End User Test

        //คะแนนน้อยกว่า8ห้ามเล่นMiniGameกับกดปุ่่มnextLevelBtn
        if(score<8){

            miniGametextView.isEnabled = false
            mininGameImage.isEnabled = false

            nextLevelBtn.isEnabled = false
        }

        //อยู่ด้าน10ห้ามกดnext
        if(Nowlevel==9){
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
            intent.putExtra("Level",Nowlevel.toString())
            startActivity(intent)
        }

        nextLevelBtn.setOnClickListener {
            val intent = Intent(this@ConcludeScoreActivity,QuestionAnswerActivity::class.java)
            intent.putExtra("Level",(Nowlevel+1).toString())
            startActivity(intent)
        }
    }

    fun ConcludeScoreAndNowLevel(score:Int,Nowlevel:Int){
        if(LockId!=null) {
            var level = FirebaseDatabase.getInstance().getReference("User/${LockId!!.id}/user_mission/mission/level${Nowlevel + 1}/score")
            level.setValue(score.toString());
        }

        if(score>=8){
            if(Nowlevel<=8) {
                val unlock = FirebaseDatabase.getInstance().getReference("User/${LockId!!.id}/user_mission/unlock_level")
                unlock.setValue((Nowlevel + 2).toString())
            }
            if(Nowlevel==9){
                val unlock = FirebaseDatabase.getInstance().getReference("User/${LockId!!.id}/user_mission/unlock_level")
                unlock.setValue((Nowlevel + 1).toString())
            }
        }

    }
}
