package com.egco428.trysub

import android.content.Intent
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import com.egco428.trysub.Mini_Game.minigame
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_conclude_score.*
import kotlinx.android.synthetic.main.activity_login.*

class ConcludeScoreActivity : AppCompatActivity() {
    var MediaPlayer: MediaPlayer? = null
    var dataSnapshot:DataSnapshot? = null
    var score = 0
    var Nowlevel = 0
    var userId:String? = null
    var playMinigame = "0"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conclude_score)

        userId = intent.getStringExtra("keyPath") //รับมาจากหน้าแรกแต่ยังไม่เลือดค่า
        score = intent.getStringExtra("Score").toInt()
        Nowlevel = intent.getStringExtra("nLevel").toInt()
        if(intent.getStringExtra("playMinigame")!=null){
            playMinigame = intent.getStringExtra("playMinigame")
        }

        //Do animation
        var animate1 = AnimationUtils.loadAnimation(this,R.anim.fromleft)
        var animate2 = AnimationUtils.loadAnimation(this,R.anim.fromright)
        var animate3 = AnimationUtils.loadAnimation(this,R.anim.fromtop)
        var animate4 = AnimationUtils.loadAnimation(this,R.anim.frombuttom)
        //right
        mininGameImage!!.animation = animate2
        nextLevelBtn!!.animation = animate2
        //left
        textView122!!.animation = animate1
        concludeScoreTextView!!.animation = animate1
        miniGametextView!!.animation = animate1
        backToSelectLevelBtn!!.animation = animate1
        //top
        textView111!!.animation = animate3
        //buttom
        replayBtn!!.animation = animate4
        //End do animation

        //get data from FireBase
        var doOnce = false
        var database = FirebaseDatabase.getInstance().getReference("User/${userId}")
        database.addValueEventListener(object  : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
            }
            override fun onDataChange(p0: DataSnapshot?) {
                if(!doOnce) {
                    doOnce = true
                    dataSnapshot = p0
                    if(playMinigame=="0") concludeScoreTextView.text = score.toString()
                    ConcludeScoreAndNowLevel(score, Nowlevel)
                }
            }
        })

        //คะแนนน้อยกว่า8ห้ามเล่นMiniGameกับกดปุ่่มnextLevelBtn
        if(score<8){
            //Log.d("check 123","get ${score}")
            miniGametextView.visibility = View.INVISIBLE
            mininGameImage.visibility = View.INVISIBLE
            //fail sound
            MediaPlayer = android.media.MediaPlayer.create(this,R.raw.fail_sound)
            MediaPlayer!!.start()

            nextLevelBtn.visibility = View.INVISIBLE
        }else if(playMinigame!="0"){
            miniGametextView.visibility = View.INVISIBLE
            mininGameImage.visibility = View.INVISIBLE
            var scoreFromMain = score.toString()+" + ${playMinigame}"
            concludeScoreTextView.text = scoreFromMain
        }else {
            //Log.d("check 123","get ${score}")
            miniGametextView.visibility = View.VISIBLE
            mininGameImage.visibility = View.VISIBLE
            //Wow sounf
            MediaPlayer = android.media.MediaPlayer.create(this,R.raw.wow_sound)
            MediaPlayer!!.start()
            nextLevelBtn.visibility = View.VISIBLE
        }

        //อยู่ด้าน10ห้ามกดnext
        if(Nowlevel==9){
            nextLevelBtn.isEnabled = false
        }

        //กดไปเล่น MiniGame
        mininGameImage.setOnClickListener {
            val intent = Intent(this@ConcludeScoreActivity,minigame::class.java)
            intent.putExtra("keyPath",userId) //รับมาจากหน้าแรกแต่ยังไม่เลือดค่า
            intent.putExtra("Score",score.toString())
            intent.putExtra("nLevel",Nowlevel.toString())
            startActivity(intent)
            finish()
        }

        backToSelectLevelBtn.setOnClickListener {
//            val intent = Intent(this@ConcludeScoreActivity,SelectGameActivity::class.java)
//            startActivity(intent)
            finish()
        }

        replayBtn.setOnClickListener {
            val intent = Intent(this@ConcludeScoreActivity,QuestionAnswerActivity::class.java)
            intent.putExtra("Level",Nowlevel.toString())
            intent.putExtra("keyPath",userId.toString())
            startActivity(intent)
            finish()
        }

        nextLevelBtn.setOnClickListener {
            val intent = Intent(this@ConcludeScoreActivity,QuestionAnswerActivity::class.java)
            intent.putExtra("Level",(Nowlevel+1).toString())
            intent.putExtra("keyPath",userId.toString())
            startActivity(intent)
            finish()
        }
    }

    fun ConcludeScoreAndNowLevel(score:Int,Nowlevel:Int){
        //pushคะแนนขึ้นFireBase
        if(score > dataSnapshot!!.child("user_mission").child("mission").child("level${Nowlevel+1}").child("score").value.toString().toInt()) {
            var scoreToFB = FirebaseDatabase.getInstance().getReference("User/$userId/user_mission/mission/level${Nowlevel+1}/score")
            scoreToFB.setValue(score.toString())

            var total = dataSnapshot!!.child("user_mission").child("mission").child("level${Nowlevel+1}").child("mini_score").value.toString().toInt()
            total+=score
            var scoreToFB2 = FirebaseDatabase.getInstance().getReference("User/$userId/user_mission/mission/level${Nowlevel+1}/total")
            scoreToFB2.setValue(total.toString())

            var total_score = 0
            var up_score = false
            var database = FirebaseDatabase.getInstance().getReference("User/${userId}")
            database.addValueEventListener(object  : ValueEventListener {
                override fun onCancelled(p0: DatabaseError?) {
                }
                override fun onDataChange(p0: DataSnapshot?) {
                    var count = 0
                    for (i in p0!!.child("user_mission").child("mission").children){
                        total_score += i.child("total").value.toString().toInt()
                        count++
                        if(count==10 && !up_score){
                            up_score = true
                            var totalToFB = FirebaseDatabase.getInstance().getReference("User/$userId/user_mission/total_score")
                            totalToFB.setValue(total_score.toString())
                        }
                    }

                }
            })

        }


        //เช็คunlock mission
        if(score>=8){
            if(Nowlevel<=8) {
                //Log.d("check FB","get <8 ${Nowlevel}")
                //Log.d("level : " ,dataSnapshot!!.child("user_mission").child("unlock_level").value.toString())
                if ((Nowlevel+2) > dataSnapshot!!.child("user_mission").child("unlock_level").value.toString().toInt()){
                    val unlock = FirebaseDatabase.getInstance().getReference("User/$userId/user_mission/unlock_level")
                    unlock.setValue((Nowlevel + 2).toString())
                }
            }
            if(Nowlevel==9){
                //Log.d("check FB","get ==9 ${Nowlevel}")
                if ((Nowlevel+1) > dataSnapshot!!.child("user_mission").child("unlock_level").value.toString().toInt()) {
                    //Log.d("level : " ,dataSnapshot!!.child("user_mission").child("unlock_level").value.toString())
                    val unlock = FirebaseDatabase.getInstance().getReference("User/$userId/user_mission/unlock_level")
                    unlock.setValue((Nowlevel + 1).toString())
                }
            }
        }

    }

}
