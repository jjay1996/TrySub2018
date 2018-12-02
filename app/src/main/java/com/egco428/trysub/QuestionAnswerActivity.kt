package com.egco428.trysub

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.util.TimeUtils
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_question_answer.*
import java.util.*
import java.util.concurrent.TimeUnit

class QuestionAnswerActivity : AppCompatActivity() {

    var objects:QuestionAnswer = QuestionAnswer()
    var score:Int = 0 // คะแนน
    var noQuestion:Int = 0 // ข้อที่ทำอยู่
    var noLevel:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_answer)

        noLevel = intent.getStringExtra("Level").toInt()

        noQATextView.text = ("Question " + (noQuestion+1) + "Score :" + score)
        updateQuestion(noQuestion)

        //ปุ่มคำตอบ1
        answ1btn.setOnClickListener {
            if(answ1btn.text.toString() == objects.CorrectAnswer[noLevel][noQuestion]){
                // ตอบถูก
                answ1btn.setBackgroundColor(Color.GREEN)
                score++

                // ล็อคปุ่มไม่ให้กด
                answ1btn.isEnabled = false
                answ2btn.isEnabled = false
                answ3btn.isEnabled = false
            }else{
                // ตอบผิด
                //แสดงchoiceผิดสีแดง
                answ1btn.setBackgroundColor(Color.RED)

                //แสดงchoiceถูกสีเขียว
                if(answ1btn.text.toString() == objects.CorrectAnswer[noLevel][noQuestion]){
                    answ1btn.setBackgroundColor(Color.GREEN)
                }else if(answ2btn.text.toString() == objects.CorrectAnswer[noLevel][noQuestion]){
                    answ2btn.setBackgroundColor(Color.GREEN)
                }else if(answ3btn.text.toString() == objects.CorrectAnswer[noLevel][noQuestion]){
                    answ3btn.setBackgroundColor(Color.GREEN)
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
                    val intentToSelectLevel = Intent(this@QuestionAnswerActivity,ConcludeScoreActivity::class.java)
                    intentToSelectLevel.putExtra("Score", score.toString())
                    intentToSelectLevel.putExtra("nLevel",noLevel.toString())
                    startActivity(intentToSelectLevel)
                    finish()
                }else if(noQuestion<=9) {
                    updateQuestion(noQuestion)
                }
            }
        }
        //ปุ่มคำตอบ2
        answ2btn.setOnClickListener {
            if(answ2btn.text.toString() == objects.CorrectAnswer[noLevel][noQuestion]){
                //ตอบถูก
                answ2btn.setBackgroundColor(Color.GREEN)
                score++

                // ล็อคปุ่มไม่ให้กด
                answ1btn.isEnabled = false
                answ2btn.isEnabled = false
                answ3btn.isEnabled = false
            }else {
                //ตอบผิด
                //แสดงchoiceผิดสีแดง
                answ2btn.setBackgroundColor(Color.RED)

                //แสดงchoiceถูกสีเขียว
                if(answ1btn.text.toString() == objects.CorrectAnswer[noLevel][noQuestion]){
                    answ1btn.setBackgroundColor(Color.GREEN)
                }else if(answ2btn.text.toString() == objects.CorrectAnswer[noLevel][noQuestion]){
                    answ2btn.setBackgroundColor(Color.GREEN)
                }else if(answ3btn.text.toString() == objects.CorrectAnswer[noLevel][noQuestion]){
                    answ3btn.setBackgroundColor(Color.GREEN)
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
                    val intentToSelectLevel = Intent(this@QuestionAnswerActivity,ConcludeScoreActivity::class.java)
                    intentToSelectLevel.putExtra("Score", score.toString())
                    intentToSelectLevel.putExtra("nLevel",noLevel.toString())
                    startActivity(intentToSelectLevel)
                    finish()
                }else if(noQuestion<=9) {
                    updateQuestion(noQuestion)
                }
            }
        }
        //ปุ่มคำตอบ3
        answ3btn.setOnClickListener {
            if(answ3btn.text.toString() == objects.CorrectAnswer[noLevel][noQuestion]){
                //ตอบถูก
                answ3btn.setBackgroundColor(Color.GREEN)
                score++

                // ล็อคปุ่มไม่ให้กด
                answ1btn.isEnabled = false
                answ2btn.isEnabled = false
                answ3btn.isEnabled = false
            }else {
                //ตอบผิด
                //แสดงchoiceผิดสีแดง
                answ3btn.setBackgroundColor(Color.RED)

                //แสดงchoiceถูกสีเขียว
                if(answ1btn.text.toString() == objects.CorrectAnswer[noLevel][noQuestion]){
                    answ1btn.setBackgroundColor(Color.GREEN)
                }else if(answ2btn.text.toString() == objects.CorrectAnswer[noLevel][noQuestion]){
                    answ2btn.setBackgroundColor(Color.GREEN)
                }else if(answ3btn.text.toString() == objects.CorrectAnswer[noLevel][noQuestion]){
                    answ3btn.setBackgroundColor(Color.GREEN)
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
                    val intentToSelectLevel = Intent(this@QuestionAnswerActivity,ConcludeScoreActivity::class.java)
                    intentToSelectLevel.putExtra("Score", score.toString())
                    intentToSelectLevel.putExtra("nLevel",noLevel.toString())
                    startActivity(intentToSelectLevel)
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

        answ1btn.setBackgroundColor(Color.LTGRAY)
        answ2btn.setBackgroundColor(Color.LTGRAY)
        answ3btn.setBackgroundColor(Color.LTGRAY)

        // ล็อคปุ่มไม่ให้กด
        answ1btn.isEnabled = true
        answ2btn.isEnabled = true
        answ3btn.isEnabled = true
    }
}
