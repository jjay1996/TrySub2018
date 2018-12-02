package com.egco428.trysub.Mini_Game

import android.animation.Animator
import android.content.Context
import android.content.Intent
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
import com.egco428.trysub.ConcludeScoreActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_conclude_score.*


class minigame : AppCompatActivity(),SensorEventListener {
    private var sensorManager : SensorManager?=null
    private var stopShake = false
    private var score = 0
    private var Nowlevel = 0
    private var userId:String? = null
    private var dataSnapshot:DataSnapshot? = null


    private var animate1:Animation? = null
    private var animate2:Animation? = null
    private var animate3:Animation? = null
    private var animate4:Animation? = null

    private var totalScore = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_minigame)

        userId = intent.getStringExtra("keyPath") //รับมาจากหน้าแรกแต่ยังไม่เลือดค่า
        score = intent.getStringExtra("Score").toInt()
        Nowlevel = intent.getStringExtra("nLevel").toInt()

        backToConcludeBtn.setOnClickListener {
            val intent = Intent(this@minigame,ConcludeScoreActivity::class.java)
            if(totalScore != 0) intent.putExtra("playMinigame","1")
            intent.putExtra("keyPath",userId) //รับมาจากหน้าแรกแต่ยังไม่เลือดค่า
            intent.putExtra("Score",score.toString())
            intent.putExtra("nLevel",Nowlevel.toString())
            startActivity(intent)
            finish()
        }

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

        var doOnce = false
        var database = FirebaseDatabase.getInstance().getReference("User/${userId}")
        database.addValueEventListener(object  : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
            }
            override fun onDataChange(p0: DataSnapshot?) {
                if(!doOnce) {
                    doOnce = true
                    dataSnapshot = p0
                }
            }
        })

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
                    totalScore += value

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
                    miniShake.text = "Your Score = ${totalScore}"
                    saveToFB()
                }

                override fun onAnimationRepeat(animation: Animation) {

                }
            }

            anim1.setAnimationListener(animationListener)
            anim2.setAnimationListener(animationListener)

            miniDice1!!.startAnimation(anim1)
            miniDice2!!.startAnimation(anim2)
            sensorManager!!.unregisterListener(this)
        }
    }

    fun  saveToFB(){
        if(totalScore > dataSnapshot!!.child("user_mission").child("mission").child("level${Nowlevel+1}").child("mini_score").value.toString().toInt()) {
            var scoreToFB = FirebaseDatabase.getInstance().getReference("User/$userId/user_mission/mission/level${Nowlevel+1}/mini_score")
            scoreToFB.setValue(totalScore.toString())

            var total = score
            total+=totalScore
            var scoreToFB2 = FirebaseDatabase.getInstance().getReference("User/$userId/user_mission/mission/level${Nowlevel+1}/total")
            scoreToFB2.setValue(total.toString())

            var total_score = 0
            var up_score = false
            var database = FirebaseDatabase.getInstance().getReference("User/${userId}")
            database.addValueEventListener(object  : ValueEventListener {
                override fun onCancelled(p0: DatabaseError?) {
                }
                override fun onDataChange(p0: DataSnapshot?) {
                    var count = 0
                    for (i in p0!!.child("user_mission").child("mission").children){
                        total_score += i.child("total").value.toString().toInt()
                        count++
                        if(count==10 && !up_score){
                            up_score = true
                            var totalToFB = FirebaseDatabase.getInstance().getReference("User/$userId/user_mission/total_score")
                            totalToFB.setValue(total_score.toString())
                        }
                    }

                }
            })

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
