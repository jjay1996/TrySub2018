package com.egco428.trysub

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.util.TimeUtils
import android.widget.Button
import kotlinx.android.synthetic.main.activity_question_answer.*
import java.util.*
import java.util.concurrent.TimeUnit

class QuestionAnswerActivity : AppCompatActivity() {

    var objects:QuestionAnswer = QuestionAnswer()
    var score:Int = 0
    var QuestionLength = objects.MyQuestion.size
    var noQuestion:Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_answer)

        noQATextView.text = ("Question " + (noQuestion+1) + "Score :" + score)
        updateQuestion(noQuestion)


        answ1btn.setOnClickListener {
            if(answ1btn.text.toString() == objects.CorrectAnswer[0][noQuestion][0]){
                score++
                //Log.d("what 's score ?","$score")
            }else{
                answerText.text = objects.CorrectAnswer[0][noQuestion][0]
            }
            SystemClock.sleep(3000)
            noQuestion++
            updateQuestion(noQuestion)
        }

        answ2btn.setOnClickListener {
            if(answ2btn.text.toString() == objects.CorrectAnswer[0][noQuestion][0]){
                score++
            }else {
                answerText.text = objects.CorrectAnswer[0][noQuestion][0]
            }
            SystemClock.sleep(3000)
            noQuestion++
            updateQuestion(noQuestion)
        }

        answ3btn.setOnClickListener {
            if(answ3btn.text.toString() == objects.CorrectAnswer[0][noQuestion][0]){
                score++
            }else {
                answerText.text = objects.CorrectAnswer[0][noQuestion][0]
            }
            SystemClock.sleep(3000)
            noQuestion++
            updateQuestion(noQuestion)
        }
    }

    fun updateQuestion(a:Int){
        noQATextView.text = ("Question ${a+1} Score : $score")
        questionTextView.text = objects.MyQuestion[0][a][0]
        answ1btn.text = objects.MyChoice[0][a][0].toString()
        answ2btn.text = objects.MyChoice[0][a][1].toString()
        answ3btn.text = objects.MyChoice[0][a][2].toString()

    }
}
