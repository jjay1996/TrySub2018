package com.egco428.trysub

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class QuestionAnswerActivity : AppCompatActivity() {

    var objects:QuestionAnswer = QuestionAnswer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_answer)
    }
}
