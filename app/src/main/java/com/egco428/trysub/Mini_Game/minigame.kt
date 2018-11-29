package com.egco428.trysub.Mini_Game

import android.content.Context
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
    private var startShake:Long = 0
    private var isShake = false
    private var stopShake = false

    private var animate:Animation? = null
    private var egg:ImageView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_minigame)

        //do animation on egg
        egg = findViewById(R.id.miniEgg)
        animate = AnimationUtils.loadAnimation(this,R.anim.frombutton)
        egg!!.animation = animate

        //set variable for sensor accelerometer system
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(event!!.sensor.type == Sensor.TYPE_ACCELEROMETER){
            getAccelerometer(event)
        }
    }

    private fun getAccelerometer(event: SensorEvent?){
        val values : FloatArray = event!!.values
        var x = values[0]
        var y = values[1]
        var z = values[2]

        //calculate accelerate for sensor detection
        var accel = ((x*x)+(y*y)+(z*z))/(SensorManager.GRAVITY_EARTH*SensorManager.GRAVITY_EARTH)
        if(accel>=2){
            Log.d("test","thread")
            val thread = object : Thread() {
                override fun run() {
                    try {
                        var isStop:Boolean = false
                        var sleepTime:Long = 100
                        var count:Int = 0
                        while (!Thread.currentThread().isInterrupted && !isStop) {
                            Thread.sleep(sleepTime)
                            runOnUiThread {
                                if(!isStop){
                                    count+=1
                                    var randomScore: Int = Random().nextInt(10 + 1)
                                    miniScore.text = "Score : " + randomScore.toString()
                                    if(count==20) {
                                        sleepTime=300
                                    } else if(count==30) {
                                        sleepTime=500
                                    } else if(count==33){
                                        sleepTime=700
                                    } else if(count==35) {
                                        Log.d("test","10"+Thread.currentThread())
                                        isStop = true
                                    }
                                }
                            }
                        }
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }
            thread.start()
            sensorManager!!.unregisterListener(this)
        }
    }

    private fun shake(){
        /*if(isShake) {
            var stopTime:Long = 0
            var seed = 0
            //var lastTime = System.currentTimeMillis()
            //var actualTime = null
            while(true) {
                Random().setSeed(0)
                seed = ((seed+1)%10)
                //Log.d("test","seed : "+seed)
                var randomScore: Int = Random().nextInt(10 + 1)
                miniScore.text = "Score : " + randomScore.toString()
                SystemClock.sleep(1000);
                Log.d("test",randomScore.toString())
                //Log.d("test",System.currentTimeMillis().toString())
                stopTime = System.currentTimeMillis()
                if(stopTime-startShake >=3000) {
                    //var randomScore: Int = Random().nextInt((10 + 1))
                    //miniScore.text = "Score : " + randomScore.toString()
                    Log.d("test","ok")
                    break
                }
            }
        }*/

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
