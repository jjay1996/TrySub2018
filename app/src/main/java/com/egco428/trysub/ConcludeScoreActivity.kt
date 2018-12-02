package com.egco428.trysub

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.egco428.trysub.Mini_Game.minigame
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_conclude_score.*
import kotlinx.android.synthetic.main.activity_login.*

class ConcludeScoreActivity : AppCompatActivity() {
    var dataSnapshot:DataSnapshot? = null
    var score = 0
    var Nowlevel = 0
    var userId:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conclude_score)

        userId = intent.getStringExtra("keyPath") //รับมาจากหน้าแรกแต่ยังไม่เลือดค่า
        score = intent.getStringExtra("Score").toInt()
        Nowlevel = intent.getStringExtra("nLevel").toInt()

        //get data from FireBase
        var database = FirebaseDatabase.getInstance().getReference("User/${userId}")
        database.addValueEventListener(object  : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
            }
            override fun onDataChange(p0: DataSnapshot?) {
                dataSnapshot=p0


                concludeScoreTextView.text = score.toString()
                ConcludeScoreAndNowLevel(score,Nowlevel)

            }
        })



//        score = intent.getStringExtra("Score").toInt()
//        Nowlevel = intent.getStringExtra("nLevel").toInt()
//        concludeScoreTextView.text = score.toString()

//        for (i in dataSnapshot!!.children) {
//            dataSnapshot=i
//            if (userId == i.key.toString()){
//                break
//            }
//        }
//        ConcludeScoreAndNowLevel(score,Nowlevel)

        //คะแนนน้อยกว่า8ห้ามเล่นMiniGameกับกดปุ่่มnextLevelBtn
        if(score<8){
            Log.d("check 123","get ${score}")
            miniGametextView.visibility = View.INVISIBLE
            mininGameImage.visibility = View.INVISIBLE

            nextLevelBtn.visibility = View.INVISIBLE
        }else {
            Log.d("check 123","get ${score}")
            miniGametextView.visibility = View.VISIBLE
            mininGameImage.visibility = View.VISIBLE

            nextLevelBtn.visibility = View.VISIBLE
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
//            val intent = Intent(this@ConcludeScoreActivity,SelectGameActivity::class.java)
//            startActivity(intent)
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
            //pushคะแนนขึ้นFireBase
        Log.d("check","ok")

        Log.d("check",dataSnapshot!!.toString())
            if(score > dataSnapshot!!.child("user_mission").child("mission").child("level${Nowlevel+1}").child("score").value.toString().toInt()) {
                var scoreToFB = FirebaseDatabase.getInstance().getReference("User/$userId/user_mission/mission/level${Nowlevel+1}/score")
                scoreToFB.setValue(score.toString())
            }


        //เช็คunlock mission
        if(score>=8){
            if(Nowlevel<=8) {
                Log.d("check FB","get <8")
                val unlock = FirebaseDatabase.getInstance().getReference("User/$userId/user_mission/unlock_level")
                unlock.setValue((Nowlevel + 2).toString())
            }
            if(Nowlevel==9){
                Log.d("check FB","get ==9")
                val unlock = FirebaseDatabase.getInstance().getReference("User/$userId/user_mission/unlock_level")
                unlock.setValue((Nowlevel + 1).toString())
            }
        }
    }

}
