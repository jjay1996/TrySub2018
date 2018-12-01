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
import java.lang.Math.abs


class LearnWordActivity : AppCompatActivity() {

    var x1: Float? = 0.toFloat()
    var x2: Float? = 0.toFloat()
    var velocityX1: Float? = 0.toFloat()
    var velocityX2: Float? = 0.toFloat()
    private var flingCount = 0
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var action = event?.action
        if (x1 == 0.toFloat()) {
            x1 = event?.rawX
        } else {
            x2 = event?.rawX
            var distanceX: Float? = x1!!-x2!!
            var timeX: Float? = event?.downTime?.toFloat()

            velocityX2 = velocityX1 //v2 = previous v1
            velocityX1 = distanceX!!/timeX!!

            println("velocity 1: "+velocityX1?.toString())
            println("velocity 2: "+velocityX2?.toString()+"\n")
            var velocityDelta: Float? = velocityX2!!-velocityX1!!
            velocityX2 = 0.toFloat()
            //println("velocity delta: "+abs(velocityDelta!!).toString())
            if (velocityX1!! > 0.toFloat() && velocityX1!! == abs(velocityDelta!!)){ // fling left
                this.flingCount++
                println("fling left!  fling count is: "+this.flingCount)
                return true
            } else if (velocityX1!! < 0.toFloat() && abs(velocityX1!!) == velocityDelta!! && action == MotionEvent.ACTION_MOVE){ // fling right
                this.flingCount++
                println("fling right! fling count is: "+this.flingCount)
                return true
            } else if (distanceX!! == 0.toFloat() && action == MotionEvent.ACTION_UP && velocityX2==velocityDelta) { // tap or press
                println("tap somewhere on the screen")
                return true
            }
            x1 = 0.toFloat()
        }
        println("touch event called itself")
        return super.onTouchEvent(event)
    }
}
