package com.egco428.trysub

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.egco428.trysub.Mini_Game.minigame
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_conclude_score.*

class ConcludeScoreActivity : AppCompatActivity() {

    var LockId :DataSourse? = null //User Now
    var score = 0
    var Nowlevel = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conclude_score)

        //get data from FireBase
        test()

        score = intent.getStringExtra("Score").toInt()
        Nowlevel = intent.getStringExtra("nLevel").toInt()
        concludeScoreTextView.text = score.toString()

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
            val intent = Intent(this@ConcludeScoreActivity,minigame::class.java)
            startActivity(intent)
            finish()
        }

        backToSelectLevelBtn.setOnClickListener {
            val intent = Intent(this@ConcludeScoreActivity,SelectGameActivity::class.java)
            startActivity(intent)
            finish()
        }

        replayBtn.setOnClickListener {
            val intent = Intent(this@ConcludeScoreActivity,QuestionAnswerActivity::class.java)
            intent.putExtra("Level",Nowlevel.toString())
            startActivity(intent)
            finish()
        }

        nextLevelBtn.setOnClickListener {
            val intent = Intent(this@ConcludeScoreActivity,QuestionAnswerActivity::class.java)
            intent.putExtra("Level",(Nowlevel+1).toString())
            startActivity(intent)
            finish()
        }
    }

    fun ConcludeScoreAndNowLevel(score:Int,Nowlevel:Int){

        if(LockId!=null) {
            //pushคะแนนขึ้นFireBase
            Log.d("check old value","${LockId!!.score[Nowlevel]}")
            if(score > LockId!!.score[Nowlevel]) {
                var scoreToFB = FirebaseDatabase.getInstance().getReference("User/${LockId!!.id}/user_mission/mission/${LockId!!.mission_id[Nowlevel]}/score")
                scoreToFB.setValue(score.toString())
            }
        }

        //เช็คunlock mission
        if(score>=8){
            if(Nowlevel<=8) {
                Log.d("check FB","get 2")
                val unlock = FirebaseDatabase.getInstance().getReference("User/${LockId!!.id}/user_mission/unlock_level")
                unlock.setValue((Nowlevel + 2).toString())
            }
            if(Nowlevel==9){
                Log.d("check FB","get 2")
                val unlock = FirebaseDatabase.getInstance().getReference("User/${LockId!!.id}/user_mission/unlock_level")
                unlock.setValue((Nowlevel + 1).toString())
            }
        }
    }

    fun test(){

        var database = FirebaseDatabase.getInstance().getReference("User")
        database.addValueEventListener(object  : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
            }
            override fun onDataChange(p0: DataSnapshot?) {
                if (LockId == null){
                    for (i in p0!!.children) {

                        val message = i.getValue(DataSourse::class.java)
                        if ("poryou12" == message!!.username.toString()) {
                            LockId = message
                            var check=1

                            var databasetest = FirebaseDatabase.getInstance().getReference("User/${LockId!!.id}/user_mission/mission")
                            databasetest.addValueEventListener(object : ValueEventListener {
                                override fun onCancelled(p0: DatabaseError?) {}

                                override fun onDataChange(p0: DataSnapshot?) {
                                    if(check <=10){
                                        for (i in p0!!.children) {
                                            val message: DataSourceSupport? = i.getValue(DataSourceSupport::class.java)

                                            if ("poryou12" == LockId!!.username.toString()) {
                                                LockId!!.score[message!!.level.toInt() - 1] = message!!.score.toInt()
                                                LockId!!.mini_score[message!!.level.toInt() - 1] = message!!.mini_score.toInt()
                                                LockId!!.total[message!!.level.toInt() - 1] = message!!.total.toInt()
                                                LockId!!.mission_id[message!!.level.toInt() - 1] = message!!.id
                                                check++
                                            }
                                        }
                                    }
                                    ConcludeScoreAndNowLevel(score,Nowlevel)
                                }
                            })
                            break
                        }
                    }
                }
            }
        })
        //End User Test
    }

}
