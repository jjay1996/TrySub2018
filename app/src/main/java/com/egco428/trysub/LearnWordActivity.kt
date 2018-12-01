package com.egco428.trysub

import android.gesture.GestureLibraries
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import android.os.Handler
import java.util.*
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.widget.Toast;



class LearnWordActivity : AppCompatActivity() {

    private lateinit var mRandom:Random
    private lateinit var mHandler: Handler
    private lateinit var mRunnable:Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learn_word)

    }
}
