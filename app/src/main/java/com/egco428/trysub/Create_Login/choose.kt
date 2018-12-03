package com.egco428.trysub.Create_Login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import com.egco428.trysub.R

import kotlinx.android.synthetic.main.activity_choose.*
import kotlinx.android.synthetic.main.activity_main.*

class choose : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose)

        //do animation
        var animate1 = AnimationUtils.loadAnimation(this,R.anim.fromleft)
        var animate2 = AnimationUtils.loadAnimation(this,R.anim.fromright)
        var animate3 = AnimationUtils.loadAnimation(this,R.anim.fromtop)
        //var animate4 = AnimationUtils.loadAnimation(this,R.anim.frombuttom)
        textView!!.animation = animate3
        male_layout!!.animation = animate1
        female_layout!!.animation = animate2
    }

    //go to register page
    fun login(view:View){
        val intent = Intent(this, login::class.java)
        startActivity(intent)
        finish()
    }

    //go to login page
    fun register(view:View){
        val intent = Intent(this, Create::class.java)
        startActivity(intent)
        finish()
    }



}
