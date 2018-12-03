package com.egco428.trysub

import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.util.TimeUtils
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_question_answer.*
import java.util.*
import java.util.concurrent.TimeUnit

class QuestionAnswerActivity : AppCompatActivity() {
    var MediaPlayer: MediaPlayer? = null
    var objects:QuestionAnswer = QuestionAnswer()
    var score:Int = 0 // คะแนน
    var noQuestion:Int = 0 // ข้อที่ทำอยู่
    var noLevel:Int = 0
    var userId:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_answer)

        noLevel = intent.getStringExtra("Level").toInt()
        userId = intent.getStringExtra("keyPath")

        //Do animation
        var animate1 = AnimationUtils.loadAnimation(this,R.anim.fromleft)
        var animate2 = AnimationUtils.loadAnimation(this,R.anim.fromright)
        var animate3 = AnimationUtils.loadAnimation(this,R.anim.fromtop)
        var animate4 = AnimationUtils.loadAnimation(this,R.anim.frombuttom)
        //right
        answ1btn!!.animation = animate2
        answ2btn!!.animation = animate2
        answ3btn!!.animation = animate2
        //left
        questionWordTextView!!.animation = animate1
        //top
        noQATextView!!.animation = animate3
        //buttom
        nextQuestionBtn!!.animation = animate4
        //End do animation

        MediaPlayer = android.media.MediaPlayer.create(this,R.raw.media_cookie)
        MediaPlayer!!.start()
        MediaPlayer!!.isLooping = true

        noQATextView.text = ("Question " + (noQuestion+1) + "Score :" + score)
        updateQuestion(noQuestion)

        nextQuestionBtn.isEnabled = false

        //ปุ่มคำตอบ1
        answ1btn.setOnClickListener {
            nextQuestionBtn.isEnabled = true
            if(answ1btn.text.toString() == objects.CorrectAnswer[noLevel][noQuestion]){
                // ตอบถูก
                answ1btn.setBackgroundResource(R.drawable.choicebtn_green)
                score++

                // ล็อคปุ่มไม่ให้กด
                answ1btn.isEnabled = false
                answ2btn.isEnabled = false
                answ3btn.isEnabled = false
            }else{
                // ตอบผิด
                //แสดงchoiceผิดสีแดง
                answ1btn.setBackgroundResource(R.drawable.choicebtn_red)

                //แสดงchoiceถูกสีเขียว
                if(answ1btn.text.toString() == objects.CorrectAnswer[noLevel][noQuestion]){
                    answ1btn.setBackgroundResource(R.drawable.choicebtn_green)
                }else if(answ2btn.text.toString() == objects.CorrectAnswer[noLevel][noQuestion]){
                    answ2btn.setBackgroundResource(R.drawable.choicebtn_green)
                }else if(answ3btn.text.toString() == objects.CorrectAnswer[noLevel][noQuestion]){
                    answ3btn.setBackgroundResource(R.drawable.choicebtn_green)
                }

                // ล็อคปุ่มไม่ให้กด
                answ1btn.isEnabled = false
                answ2btn.isEnabled = false
                answ3btn.isEnabled = false
            }
            nextQuestionBtn.setOnClickListener {
                noQuestion++
                if(noQuestion>9){
                    //ทำครบ10ข้อ
                    Toast.makeText(this,"End Your score $score",Toast.LENGTH_SHORT).show()
                    val intentToConclude = Intent(this@QuestionAnswerActivity,ConcludeScoreActivity::class.java)
                    intentToConclude.putExtra("Score", score.toString())
                    intentToConclude.putExtra("nLevel",noLevel.toString())
                    intentToConclude.putExtra("keyPath",userId)
                    startActivity(intentToConclude)
                    finish()
                }else if(noQuestion<=9) {
                    updateQuestion(noQuestion)
                }
            }
            nextQuestionBtn.isEnabled = true
        }
        //ปุ่มคำตอบ2
        answ2btn.setOnClickListener {
            nextQuestionBtn.isEnabled = true
            if(answ2btn.text.toString() == objects.CorrectAnswer[noLevel][noQuestion]){
                //ตอบถูก
                answ2btn.setBackgroundResource(R.drawable.choicebtn_green)
                score++

                // ล็อคปุ่มไม่ให้กด
                answ1btn.isEnabled = false
                answ2btn.isEnabled = false
                answ3btn.isEnabled = false
            }else {
                //ตอบผิด
                //แสดงchoiceผิดสีแดง
                answ2btn.setBackgroundResource(R.drawable.choicebtn_red)

                //แสดงchoiceถูกสีเขียว
                if(answ1btn.text.toString() == objects.CorrectAnswer[noLevel][noQuestion]){
                    answ1btn.setBackgroundResource(R.drawable.choicebtn_green)
                }else if(answ2btn.text.toString() == objects.CorrectAnswer[noLevel][noQuestion]){
                    answ2btn.setBackgroundResource(R.drawable.choicebtn_green)
                }else if(answ3btn.text.toString() == objects.CorrectAnswer[noLevel][noQuestion]){
                    answ3btn.setBackgroundResource(R.drawable.choicebtn_green)
                }

                // ล็อคปุ่มไม่ให้กด
                answ1btn.isEnabled = false
                answ2btn.isEnabled = false
                answ3btn.isEnabled = false
            }
            nextQuestionBtn.setOnClickListener {
                noQuestion++
                if(noQuestion>9){
                    //ทำครบ10ข้อ
                    Toast.makeText(this,"End Your score $score",Toast.LENGTH_SHORT).show()
                    val intentToConclude = Intent(this@QuestionAnswerActivity,ConcludeScoreActivity::class.java)
                    intentToConclude.putExtra("Score", score.toString())
                    intentToConclude.putExtra("nLevel",noLevel.toString())
                    intentToConclude.putExtra("keyPath",userId)
                    startActivity(intentToConclude)
                    finish()
                }else if(noQuestion<=9) {
                    updateQuestion(noQuestion)
                }
            }
        }
        //ปุ่มคำตอบ3
        answ3btn.setOnClickListener {
            nextQuestionBtn.isEnabled = true
            if(answ3btn.text.toString() == objects.CorrectAnswer[noLevel][noQuestion]){
                //ตอบถูก
                answ3btn.setBackgroundResource(R.drawable.choicebtn_green)
                score++

                // ล็อคปุ่มไม่ให้กด
                answ1btn.isEnabled = false
                answ2btn.isEnabled = false
                answ3btn.isEnabled = false
            }else {
                //ตอบผิด
                //แสดงchoiceผิดสีแดง
                answ3btn.setBackgroundResource(R.drawable.choicebtn_red)

                //แสดงchoiceถูกสีเขียว
                if(answ1btn.text.toString() == objects.CorrectAnswer[noLevel][noQuestion]){
                    answ1btn.setBackgroundResource(R.drawable.choicebtn_green)
                }else if(answ2btn.text.toString() == objects.CorrectAnswer[noLevel][noQuestion]){
                    answ2btn.setBackgroundResource(R.drawable.choicebtn_green)
                }else if(answ3btn.text.toString() == objects.CorrectAnswer[noLevel][noQuestion]){
                    answ3btn.setBackgroundResource(R.drawable.choicebtn_green)
                }

                // ล็อคปุ่มไม่ให้กด
                answ1btn.isEnabled = false
                answ2btn.isEnabled = false
                answ3btn.isEnabled = false
            }
            nextQuestionBtn.setOnClickListener {
                noQuestion++
                if(noQuestion>9){
                    //ทำครบ10ข้อ
                    MediaPlayer!!.stop()
                    Toast.makeText(this,"End Your score $score",Toast.LENGTH_SHORT).show()
                    val intentToConclude = Intent(this@QuestionAnswerActivity,ConcludeScoreActivity::class.java)
                    intentToConclude.putExtra("Score", score.toString())
                    intentToConclude.putExtra("nLevel",noLevel.toString())
                    intentToConclude.putExtra("keyPath",userId)
                    startActivity(intentToConclude)
                    finish()
                }else if(noQuestion<=9) {
                    updateQuestion(noQuestion)
                }
            }
        }
    }

    fun updateQuestion(a:Int){

        noQATextView.text = ("Question ${a+1} Score : $score")

        questionTextView.text = objects.MyQuestion[noLevel][a]
        answ1btn.text = objects.MyChoice[noLevel][a][0].toString()
        answ2btn.text = objects.MyChoice[noLevel][a][1].toString()
        answ3btn.text = objects.MyChoice[noLevel][a][2].toString()

        answ1btn.setBackgroundResource(R.drawable.choicebtn_white)
        answ2btn.setBackgroundResource(R.drawable.choicebtn_white)
        answ3btn.setBackgroundResource(R.drawable.choicebtn_white)
        nextQuestionBtn.isEnabled = false

        // เข้ามากดได้3ปุ่ม
        answ1btn.isEnabled = true
        answ2btn.isEnabled = true
        answ3btn.isEnabled = true
    }

    override fun onBackPressed() {
        // Do Here what ever you want do on back press;
    }

    override fun onResume() {
        //stop music
        MediaPlayer!!.stop()
//
//        //playmusic
//        MediaPlayer = android.media.MediaPlayer.create(this,R.raw.media_cookie)
//        MediaPlayer!!.start()
//        MediaPlayer!!.isLooping = true
        super.onResume()
    }

    override fun onPause() {
        //stop music
        MediaPlayer!!.stop()
        super.onPause()
    }

}
