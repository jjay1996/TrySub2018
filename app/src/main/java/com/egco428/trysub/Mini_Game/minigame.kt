package com.egco428.trysub.Mini_Game

import android.animation.Animator
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Picture
import android.graphics.drawable.Drawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.egco428.trysub.R
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Handler
import android.os.SystemClock
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_minigame.*
import java.sql.Time
import java.util.*
import android.widget.TextView




class minigame : AppCompatActivity(),SensorEventListener {
    private var sensorManager : SensorManager?=null
    private var stopShake = false

    private var animate1:Animation? = null
    private var animate2:Animation? = null
    private var animate3:Animation? = null
    private var animate4:Animation? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_minigame)

        //do animation
        animate1 = AnimationUtils.loadAnimation(this,R.anim.fromleft)
        animate2 = AnimationUtils.loadAnimation(this,R.anim.fromright)
        animate3 = AnimationUtils.loadAnimation(this,R.anim.fromtop)
        animate4 = AnimationUtils.loadAnimation(this,R.anim.frombuttom)
        
        miniDice1!!.animation = animate1
        miniDice2!!.animation = animate2
        miniTitle!!.animation = animate3
        miniShake!!.animation = animate4

        //set variable for sensor accelerometer system
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        object : Thread() {
            override fun run() {
                try {
                    var checkColorText = true
                    while (!stopShake) {
                        Thread.sleep(300)
                        runOnUiThread {
                            if(checkColorText) {
                                miniShake.setTextColor(Color.RED)
                                checkColorText = false
                            } else if(!checkColorText) {
                                miniShake.setTextColor(Color.YELLOW)
                                checkColorText = true
                            }
                        }
                    }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }.start()
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(event!!.sensor.type == Sensor.TYPE_ACCELEROMETER){
            getAccelerometer(event)
        }
    }

    private fun randomDiceValue():Int{
        return Random().nextInt(6)+1
    }

    private fun getAccelerometer(event: SensorEvent?){

        val values : FloatArray = event!!.values
        var x = values[0]
        var y = values[1]
        var z = values[2]

        //calculate accelerate for sensor detection
        var accel = ((x*x)+(y*y)+(z*z))/(SensorManager.GRAVITY_EARTH*SensorManager.GRAVITY_EARTH)
        if(accel>=2){
            Log.d("test","ss")
            var anim1 = AnimationUtils.loadAnimation(this@minigame, R.anim.shakedice)
            var anim2 = AnimationUtils.loadAnimation(this@minigame, R.anim.shakedice)

            val animationListener = object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}

                override fun onAnimationEnd(animation: Animation) {
                    val value = randomDiceValue()
                    var res:Int = 1

                    if(value == 1) res = R.drawable.dice1
                    else if(value == 2) res = R.drawable.dice2
                    else if(value == 3) res = R.drawable.dice3
                    else if(value == 4) res = R.drawable.dice4
                    else if(value == 5) res = R.drawable.dice5
                    else if(value == 6) res = R.drawable.dice6

                    if (animation === anim1) {
                        miniDice1!!.setImageResource(res)
                        Log.d("test",value.toString())
                    } else if (animation === anim2) {
                        miniDice2!!.setImageResource(res)
                        Log.d("test",value.toString())
                    }
                    stopShake = true

                }

                override fun onAnimationRepeat(animation: Animation) {

                }
            }

            anim1.setAnimationListener(animationListener)
            anim2.setAnimationListener(animationListener)

            miniDice1!!.startAnimation(anim1)
            miniDice2!!.startAnimation(anim2)

            //sensorManager!!.unregisterListener(this)
        }
    }


    override fun onPause() {
        super.onPause()
        sensorManager!!.unregisterListener(this)
    }

    override fun onResume() {
        super.onResume()
        sensorManager!!.registerListener(this,sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL)
    }

}
