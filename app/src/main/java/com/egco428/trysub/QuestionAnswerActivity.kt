package com.egco428.trysub

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import kotlinx.android.synthetic.main.activity_question_answer.*
import java.util.*

class QuestionAnswerActivity : AppCompatActivity() {

    var objects:QuestionAnswer = QuestionAnswer()
    var score:Int = 0
    var QuestionLength = objects.MyQuestion.size
    private var noQuestion:Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_answer)

        noQATextView.text = ("Question " + noQuestion)

        var randomQuestion:Int = Random().nextInt((10-1)+1)
        updateQuestion(randomQuestion)


        answ1btn.setOnClickListener {

        }

        answ2btn.setOnClickListener {

        }

        answ3btn.setOnClickListener {

        }
    }

    fun updateQuestion(a:Int){
        //Log.d("check a","${objects.MyQuestion[0][a]}")
        questionTextView.text = objects.MyQuestion[a][0]
        answ1btn.text = objects.MyChoice[a][0][1].toString()
        answ2btn.text = objects.MyChoice[a][0][2].toString()
        answ3btn.text = objects.MyChoice[a][0][2].toString()
    }
}
