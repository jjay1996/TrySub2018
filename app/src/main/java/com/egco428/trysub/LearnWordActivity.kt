package com.egco428.trysub

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import android.widget.LinearLayout
import android.support.v4.view.ViewPager




class LearnWordActivity : AppCompatActivity() {


    private var mSlideViewPager: ViewPager? = null
    private var mDotLayout: LinearLayout? = null

    private var sliderAdapter: SliderAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learn_word)

        var nolevel = intent.getStringExtra("LevelLearn").toInt()

        mSlideViewPager = findViewById(R.id.slideViewPager) as ViewPager
        mDotLayout = findViewById(R.id.dotsLayout) as LinearLayout

        sliderAdapter = SliderAdapter(this,nolevel);

        mSlideViewPager!!.adapter = sliderAdapter
    }

}
